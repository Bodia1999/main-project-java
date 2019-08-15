package com.academy.project.demo.dto.response.evolution.tickets;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketListResponse {

    @SerializedName("id")
    @Expose
    public Long id;
    @SerializedName("consignment")
    @Expose
    public Object consignment;
    @SerializedName("seat")
    @Expose
    public Long seat;
    @SerializedName("spec")
    @Expose
    public String spec;
    @SerializedName("cost")
    @Expose
    public String cost;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("hold_id")
    @Expose
    public Long holdId;
    @SerializedName("hold_until")
    @Expose
    public String holdUntil;
    @SerializedName("eticket_id")
    @Expose
    public Object eTicketId;

}
