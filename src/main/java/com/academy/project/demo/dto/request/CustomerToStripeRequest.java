package com.academy.project.demo.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class CustomerToStripeRequest {
    private String email;
}
