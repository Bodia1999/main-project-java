package com.academy.project.demo.dto.response.event.brite.events;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Description {

    @SerializedName("text")
    @Expose
    public String text;


}
