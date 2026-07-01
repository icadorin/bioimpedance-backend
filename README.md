# Bioimpedance Backend

API REST para sistema de avaliação física corporal. Gerencia clientes, avaliações por múltiplos métodos (Navy, Bioimpedância, Dobras cutâneas, IMC), autenticação com 2FA, billing via Stripe e geração de métricas de progresso.

## Stack

- Java 17+
- Spring Boot 3.x
- Spring Security (JWT + CSRF customizado)
- Spring Data JPA
- Stripe API (billing + webhooks)
- BCrypt (password hashing)
- TOTP (2FA com QR code + backup codes)
- Bucket4j + Caffeine (rate limiting em memória)
- MapStruct (mapeamento DTO ↔ Entity)
- ZXing (geração de QR code para 2FA)

## Funcionalidades

- **Autenticação** JWT com refresh tokens rotativos e "remember me" (7 / 30 dias)
- **2FA (TOTP)** com QR code, backup codes de uso único e criptografia AES-GCM do secret
- **Rate limiting** por IP em endpoints sensíveis (login, registro, 2FA)
- **Fingerprint de sessão** (IP + User-Agent hash) — bloqueia sessão se ambos divergirem
- **CSRF** via cookie `XSRF-TOKEN` + header `X-XSRF-TOKEN`
- **Gestão de clientes** (CRUD completo com validação)
- **4 métodos de avaliação**: Navy, Bioimpedância, Dobras cutâneas (JP3, JP7, DW4), IMC
- **Cálculos metabólicos**: IMC, TMB (Mifflin-St Jeor), TDEE, FFMI
- **Recomendações** de dieta e treino baseadas em objetivo (cutting/bulking/maintenance)
- **Billing** com planos Basic/Pro/Studio via Stripe (Checkout + Customer Portal)
- **Webhooks Stripe** para sincronização automática de assinaturas
- **Dashboard** com estatísticas agregadas e progresso de clientes
- **Jobs agendados** para limpeza de tokens e fingerprints antigos

## Pré-requisitos

- Java 17+
- Banco de dados (PostgreSQL, MySQL ou H2 para dev)
- Chave Stripe (opcional, para billing)
- Secret JWT (mínimo 32 caracteres)
- Secret de criptografia para 2FA

## Configuração

Crie um `application.yml` (ou `application.properties`) com:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/bioimpedance
    username: postgres
    password: postgres
  jpa:
    hibernate:
      ddl-auto: update

jwt:
  secret: sua-chave-secreta-minimo-32-caracteres-aqui
  expiration: 900000 # 15 min (access token)
  refresh-expiration: 604800000 # 7 dias
  refresh-remember-me-expiration: 2592000000 # 30 dias
  two-factor-expiration: 300000 # 5 min (temp token 2FA)

stripe:
  secret-key: sk_test_...
  webhook-secret: whsec_...
  success-url: http://localhost:5173/payments?billing=success
  cancel-url: http://localhost:5173/payments
  portal-return-url: http://localhost:5173/payments
  prices:
    basic: price_...
    pro: price_...
    studio: price_...
  fallback:
    pro-amount: 2900
    studio-amount: 5900
    currency: BRL

app:
  encryption:
    secret: chave-para-criptografar-secret-2fa
    salt: salt-aleatorio-para-pbkdf2

cors:
  allowed-origin: http://localhost:5173

cookie:
  secure: false # true em produção com HTTPS
```

## Instalação

```bash
# Clone o repositório
git clone <repo-url>
cd bioimpedance-backend

# Build
./mvnw clean package

# Rodar
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

## Endpoints principais

| Área       | Rotas                                                         | Auth                         |
| ---------- | ------------------------------------------------------------- | ---------------------------- |
| Auth       | `/api/auth/register`, `/login`, `/refresh`, `/logout`         | Público                      |
| Perfil     | `/api/auth/me`, `/profile`                                    | ✓                            |
| 2FA        | `/api/auth/2fa/setup`, `/confirm`, `/disable`, `/verify`      | Público (verify) / Protegido |
| Clientes   | `/api/clients`                                                | ✓                            |
| Avaliações | `/api/assessments`, `/calculate`                              | ✓                            |
| Dashboard  | `/api/dashboard/stats`, `/progress`, `/recent-assessments`    | ✓                            |
| Billing    | `/api/billing/plans`, `/subscription`, `/checkout`, `/portal` | ✓                            |
| Webhook    | `/api/billing/webhook`                                        | Público (Stripe signature)   |

## Segurança

- **JWT** com tokens de acesso (15min) e refresh rotativo (7/30 dias)
- **CSRF** via cookie `XSRF-TOKEN` + header `X-XSRF-TOKEN` em mutações
- **Rate limiting**: 5 tentativas/min em login, 3/10min em registro, 5/2min em 2FA
- **Fingerprint**: bloqueia sessão se IP e User-Agent mudarem simultaneamente
- **2FA**: TOTP com backup codes de uso único; secret armazenado criptografado (AES-256-GCM)
- **Cookies HttpOnly** com SameSite=Strict (produção)
- **Headers**: HSTS, CSP, X-Frame-Options DENY, Referrer-Policy
- **Sanitização de erros**: mensagens internas nunca chegam ao cliente

## Estrutura

```
src/main/java/com/bioimpedance/
├── config/              # SecurityConfig, JacksonConfig, StripeProperties
├── constants/           # Enums (Plan, PlanFeature, AssessmentMethod, Gender, etc)
├── controller/          # REST controllers (Auth, Client, Assessment, Billing, Dashboard)
├── dto/                 # Request/Response DTOs (auth, request, response)
├── entity/              # JPA entities (User, Client, Assessment, BillingSubscription, RefreshToken, SessionFingerprint, TwoFactorTempToken)
├── exception/           # Custom exceptions + GlobalExceptionHandler
├── mapper/              # MapStruct mappers (Assessment, Client)
├── repository/          # Spring Data JPA repositories
├── security/            # JwtAuthenticationFilter, RateLimitFilter, FingerprintService
├── service/             # Business logic (Auth, Billing, Calculation, Recommendation, TwoFactor, RefreshToken, etc)
└── util/                # Calculators (BodyFat, Metabolic, BodyFatInterpreter), CookieUtil, CsrfTokenUtil
```

## Planos e features

| Feature             | Basic | Pro | Studio |
| ------------------- | :---: | :-: | :----: |
| Calculadora         |   ✓   |  ✓  |   ✓    |
| Histórico/Clientes  |       |  ✓  |   ✓    |
| PDFs                |       |  ✓  |   ✓    |
| Dashboard/Gráficos  |       |  ✓  |   ✓    |
| Comparação corporal |       |  ✓  |   ✓    |
| PDF custom/branding |       |     |   ✓    |

Validação via `BillingService.requireFeature(PlanFeature.X)` — lança `IllegalArgumentException` se o plano atual não incluir a feature.

## Cálculos implementados

- **IMC**: peso / altura²
- **TMB**: Mifflin-St Jeor (10W + 6.25H - 5A ± 5/161)
- **TDEE**: TMB × fator de atividade (1.2 a 1.9)
- **FFMI**: massa magra / altura²
- **Navy**: circunferências (cintura, pescoço, quadril)
- **Bioimpedância**: Segal et al. (resistência + reactância)
- **Dobras cutâneas**: Jackson-Pollock 3/7, Durnin-Womersley 4 (com faixas etárias)
- **Recomendações**: macros (proteína/carb/gordura) baseadas em objetivo + tipo de treino e cardio sugeridos

## Jobs agendados

- `RefreshTokenService.cleanupUsedTokens()` — 03:00, remove tokens usados com +14 dias
- `FingerprintService.cleanupOldFingerprints()` — 03:30, remove fingerprints com +90 dias
