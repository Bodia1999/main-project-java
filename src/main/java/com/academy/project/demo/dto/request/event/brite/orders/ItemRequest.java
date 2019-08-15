package com.academy.project.demo.dto.request.event.brite.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemRequest {

    @SerializedName("ticket_group_id")
    @Expose
    public Long ticketGroupId;
    @SerializedName("quantity")
    @Expose
    public Long quantity;
    @SerializedName("price")
    @Expose
    public Long price;

}
