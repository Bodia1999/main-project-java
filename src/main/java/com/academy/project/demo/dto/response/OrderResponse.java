package com.academy.project.demo.dto.response;

import com.academy.project.demo.entity.Order;
import lombok.Data;

import java.util.Date;

@Data
public class OrderResponse {

    private Long id;
    private String stripeChargeId;
    private String ticketEvolutionId;
    private Boolean ifRefunded;
    private Date createdAt;
    private Date updatedAt;
    private String amount;


    public OrderResponse(Order order) {
        this.id = order.getId();
        this.stripeChargeId = order.getStripeChargeId();
        this.ticketEvolutionId = order.getTicketEvolutionId();
        this.ifRefunded = order.getIfRefunded();
        this.createdAt = order.getCreatedAt();
        this.updatedAt = order.getUpdatedAt();
        this.amount = order.getAmount();
    }
}
