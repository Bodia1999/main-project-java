package com.academy.project.demo.dto.request.event.brite.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
    @SerializedName("type")
    @Expose
    public String type;
}
