package com.academy.project.demo.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditCardRequest {
    private Long userId;
//    private String token;
    private String nameOfCard;
    private String stripeCardId;
}

