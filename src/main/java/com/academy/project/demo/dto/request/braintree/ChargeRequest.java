package com.academy.project.demo.dto.request.braintree;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChargeRequest {
    private String amount;
    private String customerStripeId;
    private Long userId;
    private String source;
}
