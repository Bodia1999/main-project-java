package com.academy.project.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "creditCard", indexes = {
        @Index(name = "stripeCardIdIndex", columnList = "stripeCardId", unique = true)
})
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameOfCard;
    @Column(name = "stripeCardId")
    private String stripeCardId;

    @ManyToOne
    private User user;

}
