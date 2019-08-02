package com.academy.project.demo.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditCardToStripeRequest {

    private String name;
    private String number;
    private String expiryMonth;
    private String expiryYear;
    private String cvc;
    private String customerStripeId;
}
