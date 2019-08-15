package com.academy.project.demo.dto.response.evolution.tickets;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketResponse {

    @SerializedName("total_entries")
    @Expose
    public Long totalEntries;
    @SerializedName("ticket_groups")
    @Expose
    public List<TicketGroupResponse> ticketGroups = new ArrayList<>();

}