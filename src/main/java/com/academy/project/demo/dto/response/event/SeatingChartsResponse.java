package com.academy.project.demo.dto.response.event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SeatingChartsResponse {


    @SerializedName("medium")
    @Expose
    public String medium;
    @SerializedName("large")
    @Expose
    public String large;

}