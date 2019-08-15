package com.academy.project.demo.dto.response.event.brite.categories;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {

    @SerializedName("resource_uri")
    @Expose
    public String resourceUri;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("name_localized")
    @Expose
    public String nameLocalized;
    @SerializedName("short_name")
    @Expose
    public String shortName;
    @SerializedName("short_name_localized")
    @Expose
    public String shortNameLocalized;

}