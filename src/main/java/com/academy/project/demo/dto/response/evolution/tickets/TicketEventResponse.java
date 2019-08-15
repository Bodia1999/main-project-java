package com.academy.project.demo.dto.response.evolution.tickets;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TicketEventResponse {

    @SerializedName("id")
    @Expose
    public Long id;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("occurs_at")
    @Expose
    public String occursAt;

}