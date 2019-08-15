package com.academy.project.demo.dto.request.event.brite;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TicketEvolutionRequest {

    @NotNull
    private String cityState ;

    @NotBlank
    private Integer page;

    @NotBlank
    private Integer perPage;
}
