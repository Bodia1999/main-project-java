package com.academy.project.demo.dto.response.event.brite.categories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pagination {

    @SerializedName("object_count")
    @Expose
    public Long objectCount;
    @SerializedName("page_number")
    @Expose
    public Long pageNumber;
    @SerializedName("page_size")
    @Expose
    public Long pageSize;
    @SerializedName("page_count")
    @Expose
    public Long pageCount;
    @SerializedName("has_more_items")
    @Expose
    public Boolean hasMoreItems;

}