package com.bioimpedance.config;

import com.bioimpedance.constants.Plan;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "stripe")
public class StripeProperties {
    private String secretKey;
    private String webhookSecret;
    private String successUrl;
    private String cancelUrl;
    private String portalReturnUrl;

    private Prices prices = new Prices();
    private Fallback fallback = new Fallback();

    public String priceIdFor(Plan plan) {
        return switch (plan) {
            case BASIC  -> prices.getBasic();
            case PRO    -> prices.getPro();
            case STUDIO -> prices.getStudio();
        };
    }

    /** Price IDs do Stripe — usados para criar Checkout Sessions. */
    @Getter
    @Setter
    public static class Prices {
        private String basic;
        private String pro;
        private String studio;
    }

    /**
     * Fallback de emergência: usado APENAS se a API do Stripe estiver
     * inacessível. Em operação normal, os preços vêm do Stripe.
     */
    @Getter
    @Setter
    public static class Fallback {
        private Long proAmount;
        private Long studioAmount;
        private String currency = "BRL";
    }
}