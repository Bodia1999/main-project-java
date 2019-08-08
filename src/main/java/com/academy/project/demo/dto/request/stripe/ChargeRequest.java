package com.academy.project.demo.dto.request.stripe;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChargeRequest {
    private Integer amount;
    private enum Currency {
        USD, EUR, UAH
    }
    private Currency currency;
    private String customerStripeId;
    private Long userId;
    private String source;
}
