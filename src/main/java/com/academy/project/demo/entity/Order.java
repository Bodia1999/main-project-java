package com.academy.project.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "_order")
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String stripeChargeId;

    @Column
    private String amount;

    @Column
    private String ticketEvolutionId;

    @Column
    private Boolean ifRefunded;

    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;

    @ManyToOne
    private User user;


}
