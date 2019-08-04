package com.academy.project.demo.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@Builder
public class CustomerToStripeRequest {
    @NotBlank
    private String email;
}
