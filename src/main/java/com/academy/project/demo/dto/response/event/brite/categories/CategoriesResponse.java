package com.academy.project.demo.dto.response.event.brite.categories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CategoriesResponse {

    @SerializedName("pagination")
    @Expose
    public Pagination pagination;
    @SerializedName("categories")
    @Expose
    public List<Category> categories = new ArrayList<>();

}