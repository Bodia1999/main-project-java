package com.academy.project.demo.dto.request;

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
    private String source;
}
