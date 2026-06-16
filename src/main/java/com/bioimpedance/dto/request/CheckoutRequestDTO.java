package com.bioimpedance.dto.request;

import com.bioimpedance.constants.Plan;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckoutRequestDTO {

    @NotNull
    private Plan plan;

    // Campo `email` removido: o BillingService usa sempre o email do usuário
    // autenticado (via CurrentUserService), nunca o email informado pelo cliente.
    // Manter o campo criava uma superfície desnecessária que poderia induzir
    // confusão sobre qual email seria usado na criação do customer Stripe.
}