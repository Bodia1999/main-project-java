package com.academy.project.demo.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Accessors(chain = true)
@Embeddable
public class Ticket {
    private String price;
    private String typeOfEvents;
    @Column(name = "_row")
    private String row;
    private String section;
    private String occursAt;
    private Integer quantity;
}
