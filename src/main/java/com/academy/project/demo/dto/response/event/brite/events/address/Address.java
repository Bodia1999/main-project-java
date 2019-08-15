package com.academy.project.demo.dto.response.event.brite.events.address;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {

    @SerializedName("address_1")
    @Expose
    public String address1;
    @SerializedName("address_2")
    @Expose
    public Object address2;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("region")
    @Expose
    public String region;
    @SerializedName("postal_code")
    @Expose
    public String postalCode;
    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("latitude")
    @Expose
    public String latitude;
    @SerializedName("longitude")
    @Expose
    public String longitude;
    @SerializedName("localized_address_display")
    @Expose
    public String localizedAddressDisplay;
    @SerializedName("localized_area_display")
    @Expose
    public String localizedAreaDisplay;
    @SerializedName("localized_multi_line_address_display")
    @Expose
    public List<String> localizedMultiLineAddressDisplay = new ArrayList<>();

}
