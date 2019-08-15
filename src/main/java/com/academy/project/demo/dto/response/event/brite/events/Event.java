package com.academy.project.demo.dto.response.event.brite.events;
import com.academy.project.demo.dto.response.event.brite.events.address.AddressResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Event {

    @SerializedName("name")
    @Expose
    public Name name;

    @SerializedName("description")
    @Expose
    public Description description;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("start")
    @Expose
    public Start start;
    @SerializedName("organization_id")
    @Expose
    public String organizationId;
    @SerializedName("capacity")
    @Expose
    public Object capacity;
    @SerializedName("currency")
    @Expose
    public String currency;
    @SerializedName("organizer_id")
    @Expose
    public String organizerId;
    @SerializedName("venue_id")
    @Expose
    public String venueId;
    @SerializedName("category_id")
    @Expose
    public String categoryId;
    @SerializedName("subcategory_id")
    @Expose
    public String subcategoryId;
    @SerializedName("format_id")
    @Expose
    public String formatId;
    @SerializedName("resource_uri")
    @Expose
    public String resourceUri;
    @SerializedName("is_externally_ticketed")
    @Expose
    public Boolean isExternallyTicketed;
    @SerializedName("logo")
    @Expose
    public Logo logo;

    public AddressResponse addressResponse;
}