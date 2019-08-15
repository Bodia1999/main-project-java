package com.academy.project.demo.dto.request.ticket.evolution.orders;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {

    @SerializedName("shipped_items")
    @Expose
    public List<ShippedItemRequest> shippedItems = new ArrayList<>();
    @SerializedName("payments")
    @Expose
    public List<PaymentRequest> payments = new ArrayList<>();
    @SerializedName("buyer_id")
    @Expose
    public Long buyerId;
    @SerializedName("buyer_reference_number")
    @Expose
    public String buyerReferenceNumber;
    @SerializedName("external_notes")
    @Expose
    public String externalNotes;
    @SerializedName("internal_notes")
    @Expose
    public String internalNotes;

}