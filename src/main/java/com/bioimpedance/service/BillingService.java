package com.bioimpedance.service;

import com.bioimpedance.config.StripeProperties;
import com.bioimpedance.constants.Plan;
import com.bioimpedance.constants.PlanFeature;
import com.bioimpedance.dto.request.CheckoutRequestDTO;
import com.bioimpedance.dto.response.*;
import com.bioimpedance.entity.BillingSubscription;
import com.bioimpedance.entity.User;
import com.bioimpedance.repository.BillingSubscriptionRepository;
import com.bioimpedance.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Price;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class BillingService {

    private static final String STATUS_FREE = "free";
    private static final String STATUS_ACTIVE = "active";
    private static final List<String> GRANT_ACCESS_STATUSES = List.of("active", "trialing", "past_due");

    private final BillingSubscriptionRepository billingSubscriptionRepository;
    private final UserRepository userRepository;
    private final StripeProperties stripeProperties;
    private final ObjectMapper objectMapper;
    private final CurrentUserService currentUserService;

    @Value("${billing.default-plan:BASIC}")
    private String defaultPlanValue;

    private record StripePriceInfo(long unitAmount, String currency) {}

    private final Cache<Plan, StripePriceInfo> priceCache = Caffeine.newBuilder()
        .expireAfterWrite(6, TimeUnit.HOURS)
        .maximumSize(10)
        .build();

    // ==================== MÉTODOS PÚBLICOS ====================

    public List<BillingPlanDTO> getPlans() {
        return Arrays.stream(Plan.values())
            .sorted((left, right) -> Integer.compare(left.getSortOrder(), right.getSortOrder()))
            .map(this::toPlanDTO)
            .toList();
    }

    public SubscriptionResponseDTO getCurrentSubscription() {
        User currentUser = currentUserService.getCurrentUser();
        Optional<BillingSubscription> subscription = findCurrentPaidSubscription(currentUser.getId());
        Plan plan = subscription.map(BillingSubscription::getPlan)
            .orElseGet(() -> currentUser.getPlan() != null ? currentUser.getPlan() : defaultPlan());
        String customerId = subscription
            .map(BillingSubscription::getStripeCustomerId)
            .orElse(currentUser.getStripeCustomerId());

        return SubscriptionResponseDTO.builder()
            .plan(plan.getSlug())
            .planName(plan.getLabel())
            .status(subscription.map(BillingSubscription::getStatus).orElse(STATUS_FREE))
            .currentPeriodEnd(subscription.map(BillingSubscription::getCurrentPeriodEnd).orElse(null))
            .cancelAtPeriodEnd(subscription.map(BillingSubscription::isCancelAtPeriodEnd).orElse(false))
            .billingPortalReady(hasText(customerId) && stripeConfigured())
            .features(toFeatureDTOs(plan))
            .build();
    }

    public boolean hasFeature(PlanFeature feature) {
        User currentUser = currentUserService.getCurrentUser();
        Plan plan = findCurrentPaidSubscription(currentUser.getId())
            .map(BillingSubscription::getPlan)
            .orElseGet(() -> currentUser.getPlan() != null ? currentUser.getPlan() : defaultPlan());
        return plan.includes(feature);
    }

    public void requireFeature(PlanFeature feature) {
        if (!hasFeature(feature)) {
            throw new IllegalArgumentException("Recurso indisponível no plano atual");
        }
    }

    public CheckoutResponseDTO createCheckoutSession(CheckoutRequestDTO dto) throws StripeException {
        Plan plan = dto.getPlan();

        if (plan == Plan.BASIC) {
            throw new IllegalArgumentException("O plano Basic não precisa de checkout");
        }

        ensureStripeConfigured();

        User currentUser = currentUserService.getCurrentUser();
        String priceId = stripeProperties.priceIdFor(plan);

        if (!hasText(priceId)) {
            throw new IllegalArgumentException("Price ID do plano " + plan.getLabel() + " não configurado");
        }

        String customerId = ensureStripeCustomer(currentUser);

        Stripe.apiKey = stripeProperties.getSecretKey();

        SessionCreateParams params = SessionCreateParams.builder()
            .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
            .setSuccessUrl(stripeProperties.getSuccessUrl())
            .setCancelUrl(stripeProperties.getCancelUrl())
            .setCustomer(customerId)
            .setClientReferenceId(currentUser.getId())
            .putMetadata("plan", plan.getSlug())
            .addLineItem(SessionCreateParams.LineItem.builder()
                .setPrice(priceId)
                .setQuantity(1L)
                .build())
            .build();

        Session session = Session.create(params);

        return CheckoutResponseDTO.builder()
            .url(session.getUrl())
            .build();
    }

    public CustomerPortalResponseDTO createCustomerPortalSession() throws StripeException {
        ensureStripeConfigured();

        User currentUser = currentUserService.getCurrentUser();
        String customerId = findLatestCustomerId(currentUser.getId())
            .orElse(currentUser.getStripeCustomerId());
        if (!hasText(customerId)) {
            throw new IllegalArgumentException("Nenhum cliente Stripe encontrado para a assinatura atual");
        }

        Stripe.apiKey = stripeProperties.getSecretKey();

        com.stripe.param.billingportal.SessionCreateParams params =
            com.stripe.param.billingportal.SessionCreateParams.builder()
                .setCustomer(customerId)
                .setReturnUrl(stripeProperties.getPortalReturnUrl())
                .build();

        com.stripe.model.billingportal.Session session =
            com.stripe.model.billingportal.Session.create(params);

        return CustomerPortalResponseDTO.builder().url(session.getUrl()).build();
    }

    // ==================== WEBHOOK ====================

    @Transactional
    public void handleWebhook(String payload, String signature) {
        if (!hasText(stripeProperties.getWebhookSecret())) {
            throw new IllegalArgumentException("Webhook secret do Stripe não configurado");
        }

        try {
            Webhook.constructEvent(payload, signature, stripeProperties.getWebhookSecret());

            JsonNode event = objectMapper.readTree(payload);
            String type = event.path("type").asText();
            JsonNode object = event.path("data").path("object");

            switch (type) {
                case "checkout.session.completed" -> handleCheckoutCompleted(object);
                case "customer.subscription.created", "customer.subscription.updated" ->
                    handleSubscriptionChanged(object, false);
                case "customer.subscription.deleted" -> handleSubscriptionChanged(object, true);
            }
        } catch (SignatureVerificationException | JsonProcessingException ex) {
            throw new IllegalArgumentException("Webhook Stripe inválido ou assinatura incorreta");
        } catch (Exception ex) {
            throw new RuntimeException("Erro inesperado ao processar webhook", ex);
        }
    }

    // ==================== MÉTODOS PRIVADOS ====================

    private void handleCheckoutCompleted(JsonNode object) {
        String userId = text(object, "client_reference_id");
        String subscriptionId = text(object, "subscription");
        String customerId = text(object, "customer");
        String planSlug = text(object.path("metadata"), "plan");

        Plan plan = Plan.fromSlug(planSlug).orElse(Plan.BASIC);

        BillingSubscription subscription = findBySubscriptionOrCustomer(subscriptionId, customerId)
            .orElseGet(BillingSubscription::new);

        subscription.setPlan(plan);
        subscription.setStatus(STATUS_ACTIVE);
        subscription.setUserId(userId);
        subscription.setStripeCustomerId(customerId);
        subscription.setStripeSubscriptionId(subscriptionId);
        subscription.setStripeCheckoutSessionId(text(object, "id"));
        subscription.setStripePriceId(stripeProperties.priceIdFor(plan));
        subscription.setCustomerEmail(customerEmailFromCheckout(object));
        subscription.setCancelAtPeriodEnd(false);

        billingSubscriptionRepository.save(subscription);

        // Atualiza plano do usuário
        if (hasText(userId)) {
            userRepository.findById(userId).ifPresent(user -> {
                user.setPlan(plan);
                user.setStripeCustomerId(customerId);
                userRepository.save(user);
            });
        }
    }

    private void handleSubscriptionChanged(JsonNode object, boolean deleted) {
        String subscriptionId = text(object, "id");
        String customerId = text(object, "customer");
        String status = deleted ? "canceled" : text(object, "status");
        JsonNode firstItem = firstSubscriptionItem(object);
        String priceId = text(firstItem.path("price"), "id");

        Optional<BillingSubscription> existing = findBySubscriptionOrCustomer(subscriptionId, customerId);
        Plan plan = planByPriceId(priceId)
            .or(() -> existing.map(BillingSubscription::getPlan))
            .orElse(defaultPlan());

        BillingSubscription subscription = existing.orElseGet(BillingSubscription::new);
        String userId = existing.map(BillingSubscription::getUserId).orElse(null);
        if (!hasText(userId) && hasText(customerId)) {
            userId = userRepository.findByStripeCustomerId(customerId)
                .map(User::getId)
                .orElse(null);
        }

        subscription.setPlan(plan);
        subscription.setStatus(hasText(status) ? status : STATUS_ACTIVE);
        subscription.setUserId(userId);
        subscription.setStripeCustomerId(customerId);
        subscription.setStripeSubscriptionId(subscriptionId);
        subscription.setStripePriceId(priceId);
        subscription.setCurrentPeriodEnd(currentPeriodEnd(object, firstItem));
        subscription.setCancelAtPeriodEnd(object.path("cancel_at_period_end").asBoolean(false));

        billingSubscriptionRepository.save(subscription);

        Plan finalPlan = deleted ? Plan.BASIC : plan;
        if (hasText(customerId)) {
            userRepository.findByStripeCustomerId(customerId).ifPresent(user -> {
                user.setPlan(finalPlan);
                userRepository.save(user);
            });
        }
    }

    private String ensureStripeCustomer(User user) throws StripeException {
        if (hasText(user.getStripeCustomerId())) {
            return user.getStripeCustomerId();
        }

        Stripe.apiKey = stripeProperties.getSecretKey();

        CustomerCreateParams params = CustomerCreateParams.builder()
            .setEmail(user.getEmail())
            .setName(user.getName())
            .build();

        Customer customer = Customer.create(params);

        user.setStripeCustomerId(customer.getId());
        userRepository.save(user);

        return customer.getId();
    }

    // ==================== MÉTODOS AUXILIARES ====================

    private Optional<BillingSubscription> findCurrentPaidSubscription(String userId) {
        return billingSubscriptionRepository.findFirstByUserIdAndStatusInOrderByUpdatedAtDesc(
            userId,
            GRANT_ACCESS_STATUSES
        );
    }

    private Optional<BillingSubscription> findBySubscriptionOrCustomer(String subscriptionId, String customerId) {
        if (hasText(subscriptionId)) {
            return billingSubscriptionRepository.findFirstByStripeSubscriptionIdOrderByUpdatedAtDesc(subscriptionId);
        }
        if (hasText(customerId)) {
            return billingSubscriptionRepository.findFirstByStripeCustomerIdOrderByUpdatedAtDesc(customerId);
        }
        return Optional.empty();
    }

    private Optional<Plan> planByPriceId(String priceId) {
        if (!hasText(priceId)) return Optional.empty();

        return Arrays.stream(Plan.values())
            .filter(plan -> priceId.equals(stripeProperties.priceIdFor(plan)))
            .findFirst();
    }

    private Optional<String> findLatestCustomerId(String userId) {
        return billingSubscriptionRepository.findFirstByUserIdOrderByUpdatedAtDesc(userId)
            .map(BillingSubscription::getStripeCustomerId)
            .filter(this::hasText);
    }

    private JsonNode firstSubscriptionItem(JsonNode object) {
        JsonNode data = object.path("items").path("data");
        return data.isArray() && data.size() > 0 ? data.get(0) : objectMapper.createObjectNode();
    }

    private LocalDateTime currentPeriodEnd(JsonNode object, JsonNode firstItem) {
        long timestamp = object.path("current_period_end").asLong(0);
        if (timestamp <= 0) {
            timestamp = firstItem.path("current_period_end").asLong(0);
        }
        return timestamp > 0
            ? LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneOffset.UTC)
            : null;
    }

    private String customerEmailFromCheckout(JsonNode object) {
        String email = text(object.path("customer_details"), "email");
        return hasText(email) ? email : text(object, "customer_email");
    }

    private Plan defaultPlan() {
        return Plan.fromSlug(defaultPlanValue)
            .or(() -> {
                try {
                    return Optional.of(Plan.valueOf(defaultPlanValue.toUpperCase()));
                } catch (Exception e) {
                    return Optional.empty();
                }
            })
            .orElse(Plan.BASIC);
    }

    private void ensureStripeConfigured() {
        if (!stripeConfigured()) {
            throw new IllegalArgumentException("STRIPE_SECRET_KEY não configurada");
        }
    }

    private boolean stripeConfigured() {
        return hasText(stripeProperties.getSecretKey());
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

    private String text(JsonNode node, String field) {
        JsonNode value = node.path(field);
        if (value.isMissingNode() || value.isNull()) return null;
        String text = value.asText();
        return hasText(text) ? text : null;
    }

    private BillingPlanDTO toPlanDTO(Plan plan) {
        boolean paid = plan != Plan.BASIC;

        Long price;
        String currency;

        if (plan == Plan.BASIC) {
            price = 0L;
            currency = "BRL";
        } else {
            StripePriceInfo priceInfo = resolvePrice(plan);
            price = priceInfo != null ? priceInfo.unitAmount() : null;
            currency = priceInfo != null ? priceInfo.currency() : "BRL";
        }

        return BillingPlanDTO.builder()
            .plan(plan.getSlug())
            .name(plan.getLabel())
            .sortOrder(plan.getSortOrder())
            .paid(paid)
            .checkoutReady(!paid || hasText(stripeProperties.priceIdFor(plan)))
            .features(toFeatureDTOs(plan))
            .price(price)
            .currency(currency)
            .build();
    }

    private StripePriceInfo resolvePrice(Plan plan) {
        // 1. Tenta o cache
        StripePriceInfo cached = priceCache.getIfPresent(plan);
        if (cached != null) return cached;

        // 2. Tenta buscar do Stripe
        StripePriceInfo fromStripe = fetchPriceFromStripe(plan);
        if (fromStripe != null) {
            priceCache.put(plan, fromStripe);
            return fromStripe;
        }

        // 3. Fallback: configuração manual
        return buildFallbackPrice(plan);
    }

    private StripePriceInfo fetchPriceFromStripe(Plan plan) {
        String priceId = stripeProperties.priceIdFor(plan);
        if (!hasText(priceId) || !stripeConfigured()) {
            return null;
        }

        try {
            Stripe.apiKey = stripeProperties.getSecretKey();
            Price price = Price.retrieve(priceId);

            if (price.getUnitAmount() == null) {
                log.warn("Price {} do Stripe não tem unit_amount definido", priceId);
                return null;
            }

            // Stripe retorna currency em lowercase ("brl") — normaliza para ISO 4217
            String currency = price.getCurrency() != null
                ? price.getCurrency().toUpperCase()
                : "BRL";

            log.debug("Preço buscado do Stripe para {}: {} {}",
                plan, price.getUnitAmount(), currency);

            return new StripePriceInfo(price.getUnitAmount(), currency);

        } catch (StripeException e) {
            log.warn("Falha ao buscar preço do Stripe para plano {} (priceId={}): {}",
                plan, priceId, e.getMessage());
            return null;
        } catch (Exception e) {
            log.error("Erro inesperado ao buscar preço do Stripe para plano {}", plan, e);
            return null;
        }
    }

    private StripePriceInfo buildFallbackPrice(Plan plan) {
        StripeProperties.Fallback fb = stripeProperties.getFallback();
        Long amount = switch (plan) {
            case PRO    -> fb.getProAmount();
            case STUDIO -> fb.getStudioAmount();
            default     -> null;
        };

        if (amount == null) {
            log.warn("Nenhum preço configurado (nem no Stripe nem no fallback) para o plano {}", plan);
            return null;
        }

        String currency = hasText(fb.getCurrency()) ? fb.getCurrency().toUpperCase() : "BRL";
        log.info("Usando preço de fallback para plano {}: {} {}", plan, amount, currency);
        return new StripePriceInfo(amount, currency);
    }

    private List<PlanFeatureDTO> toFeatureDTOs(Plan plan) {
        return Arrays.stream(PlanFeature.values())
            .map(feature -> PlanFeatureDTO.builder()
                .key(feature.getKey())
                .label(feature.getLabel())
                .included(plan.includes(feature))
                .build())
            .toList();
    }
}
