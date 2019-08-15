package com.academy.project.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    private Long userId;
    private String stripeChargeId;
    private String ticketEvolutionId;
    private Boolean ifRefunded;
    private String amount;
    private String typeOfEvents;
    private String row;
    private String section;
    private String occursAt;
    private Integer quantity;
    private String nameOfEvent;

}
