package com.academy.project.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequest {
    private String typeOfEvents;
    private String row;
    private String section;
    private String occursAt;
    private Integer quantity;
    private String nameOfEvent;
}
