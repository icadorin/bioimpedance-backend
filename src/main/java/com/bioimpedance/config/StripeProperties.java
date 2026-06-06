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

    public String priceIdFor(Plan plan) {
        return switch (plan) {
            case BASIC -> prices.getBasic();
            case PRO -> prices.getPro();
            case STUDIO -> prices.getStudio();
        };
    }

    @Getter
    @Setter
    public static class Prices {
        private String basic;
        private String pro;
        private String studio;
    }
}
