package com.academy.project.demo.dto.request.stripe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class CreditCardToStripeRequest {


    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String address;
    @NotBlank
    private String country;
    @NotBlank
    private String city;
    @NotBlank
    private String zip;
    @NotBlank
    private String name;
    @NotBlank
    private String nameOfCreditCard;
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
