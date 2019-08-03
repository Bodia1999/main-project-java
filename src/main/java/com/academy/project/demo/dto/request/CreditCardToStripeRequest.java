package com.academy.project.demo.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CreditCardToStripeRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String number;
    @NotBlank
    private String expiryMonth;
    @NotBlank
    private String expiryYear;
    @NotBlank
    private String cvc;
    @NotBlank
    private String customerStripeId;
}
