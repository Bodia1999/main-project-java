package com.academy.project.demo.dto.response.ticket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TicketGroupResponse {

    @SerializedName("id")
    @Expose
    public Long id;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("row")
    @Expose
    public String row;
    @SerializedName("section")
    @Expose
    public String section;
    @SerializedName("quantity")
    @Expose
    public Long quantity;
    @SerializedName("available_quantity")
    @Expose
    public Long availableQuantity;
    @SerializedName("wholesale_price")
    @Expose
    public Double wholesalePrice;
    @SerializedName("eticket")
    @Expose
    public Boolean eticket;
    @SerializedName("retail_price")
    @Expose
    public Double retailPrice;
    @SerializedName("format")
    @Expose
    public String format;
    @SerializedName("wheelchair")
    @Expose
    public Boolean wheelchair;
    @SerializedName("signature")
    @Expose
    public String signature;
    @SerializedName("event")
    @Expose
    public TicketEventResponse event;

}