package com.academy.project.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreditCardRequest {
    private Long userId;
    private String nameOfCard;
    private String stripeCardId;
}

