package com.academy.project.demo.dto.response.event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventResponse {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("occurs_at_local")
    @Expose
    public String occursAtLocal;
    @SerializedName("products_count")
    @Expose
    public Integer productsCount;
    @SerializedName("products_eticket_count")
    @Expose
    public Integer productsEticketCount;
    @SerializedName("seating_chart")
    @Expose
    public SeatingChartsResponse seatingChart;
    @SerializedName("venue")
    @Expose
    public VenueResponse venue;

}

