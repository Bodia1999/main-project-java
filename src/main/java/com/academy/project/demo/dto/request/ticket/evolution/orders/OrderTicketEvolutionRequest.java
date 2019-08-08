package com.academy.project.demo.dto.request.ticket.evolution.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderTicketEvolutionRequest {
    @SerializedName("orders")
    @Expose
    public List<OrderRequest> orders = new ArrayList<>();

}

