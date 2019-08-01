package com.academy.project.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "creditCard")
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private String nameOfCard;

    @ManyToOne
    private User user;

    public CreditCard (Long id, String token){
        this.id = id;
        this.token = token;
    }
}
