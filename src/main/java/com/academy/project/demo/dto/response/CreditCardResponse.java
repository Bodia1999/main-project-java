package com.academy.project.demo.dto.response;

import com.academy.project.demo.entity.CreditCard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardResponse {

    private Long id;
    private String token;
    private String nameOfCard;

    public CreditCardResponse(CreditCard creditCard){
        this.id = creditCard.getId();
        this.token = creditCard.getToken();
        this.nameOfCard = creditCard.getNameOfCard();
    }
}
