package com.academy.project.demo.dto;

import com.academy.project.demo.dto.response.CreditCardResponse;
import com.academy.project.demo.entity.CreditCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserSummary {
    private Long id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private String role;
    private List<CreditCardResponse> card;
}
