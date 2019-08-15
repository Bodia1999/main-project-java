package com.academy.project.demo.dto.response.event.brite.events.address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressResponse {

    @SerializedName("address")
    @Expose
    public Address address;

}