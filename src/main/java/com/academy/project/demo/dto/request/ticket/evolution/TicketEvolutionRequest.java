package com.academy.project.demo.dto.request.ticket.evolution;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TicketEvolutionRequest {

    @NotNull
    private String cityState ;

    @NotNull
    private String q;

    @NotNull
    private String categoryId ;

    @NotBlank
    private Integer page;

    @NotBlank
    private Integer perPage;
}
