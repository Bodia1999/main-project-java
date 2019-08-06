package com.academy.project.demo.dto.response.event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class VenueResponse {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("slug")
    @Expose
    public String slug;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("slug_url")
    @Expose
    public String slugUrl;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("location")
    @Expose
    public String location;
    @SerializedName("time_zone")
    @Expose
    public String timeZone;

}
