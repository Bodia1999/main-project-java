package com.academy.project.demo.dto.response.ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "total_entries",
        "ticket_groups"
})
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