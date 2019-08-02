package com.academy.project.demo.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Currency;

@Getter
@Setter
public class CustomerToStripeRequest {
    private String email;

}
