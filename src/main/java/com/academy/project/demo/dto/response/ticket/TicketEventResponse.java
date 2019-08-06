package com.academy.project.demo.dto.response.ticket;

import com.fasterxml.jackson.annotation.*;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;


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