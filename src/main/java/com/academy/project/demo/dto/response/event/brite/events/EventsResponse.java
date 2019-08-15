package com.academy.project.demo.dto.response.event.brite.events;

import java.util.ArrayList;
import java.util.List;

import com.academy.project.demo.dto.response.event.brite.categories.Pagination;
import com.academy.project.demo.dto.response.event.brite.events.address.AddressResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class EventsResponse {

    @SerializedName("pagination")
    @Expose
    public Pagination pagination;
    @SerializedName("events")
    @Expose
    public List<Event> events = new ArrayList<>();



}

