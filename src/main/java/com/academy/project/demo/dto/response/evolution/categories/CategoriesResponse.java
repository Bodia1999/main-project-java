package com.academy.project.demo.dto.response.evolution.categories;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriesResponse {

    @SerializedName("categories")
    @Expose
    public List<Category> categories = null;
    @SerializedName("total_entries")
    @Expose
    public Long totalEntries;
    @SerializedName("page")
    @Expose
    public Long page;

}
