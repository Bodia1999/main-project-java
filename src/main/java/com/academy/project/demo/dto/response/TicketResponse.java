package com.academy.project.demo.dto.response;

import com.academy.project.demo.entity.Order;
import lombok.Data;

@Data
public class TicketResponse {
    private String nameOfEvent;
    private String typeOfEvents;
    private String row;
    private String section;
    private String occursAt;
    private Integer quantity;

    public TicketResponse (Order order) {
        this.nameOfEvent = order.getNameOfEvent();
        this.typeOfEvents = order.getTypeOfEvents();
        this.row = order.getRow();
        this.section = order.getSection();
        this.occursAt = order.getOccursAt();
        this.quantity = order.getQuantity();
    }

}
