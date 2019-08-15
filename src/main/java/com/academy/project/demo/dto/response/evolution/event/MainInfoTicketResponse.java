package com.academy.project.demo.dto.response.evolution.event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class MainInfoTicketResponse {

    @SerializedName("current_page")
    @Expose
    public Integer currentPage;
    @SerializedName("per_page")
    @Expose
    public Integer perPage;
    @SerializedName("total_entries")
    @Expose
    public Integer totalEntries;
    @SerializedName("events")
    @Expose
    public List<EventResponse> events = new ArrayList<>();

}
