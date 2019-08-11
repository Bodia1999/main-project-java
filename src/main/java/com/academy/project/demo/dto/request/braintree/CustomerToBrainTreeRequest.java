package com.academy.project.demo.dto.request.braintree;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@Builder
public class CustomerToBrainTreeRequest {
    @NotBlank
    private String email;
}
