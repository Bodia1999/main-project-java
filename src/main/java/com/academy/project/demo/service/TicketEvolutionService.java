package com.academy.project.demo.service;

import com.academy.project.demo.dto.response.event.MainInfoTicketResponse;
import com.academy.project.demo.dto.response.ticket.TicketResponse;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Service
public class TicketEvolutionService {
    @Value("${ticket.evolution.token}")
    private String xToken;

    @Value("${ticket.evolution.signature}")
    private String xSignature;

    @Value("${ticket.evolution.url}")
    private String url;

    public MainInfoTicketResponse getAllEventsByQuery() throws IOException {
        String url1 = url + "/events?page=1&per_page=10";
        RestTemplate restTemplate = createInstance();
        Gson gson = new Gson();

        HttpHeaders headers = createHttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(url1, HttpMethod.GET, entity, String.class);
        System.out.println("Result - status (" + response.getStatusCode() + ") has body: " + response.hasBody());

        return gson.fromJson(response.getBody(), MainInfoTicketResponse.class);
    }

    public TicketResponse getInfoAboutTicket(String ticketId) {

        String url2 = url + "/ticket_groups";
        UriComponents eventIdUrl = UriComponentsBuilder
                .fromHttpUrl(url2)
                .queryParam("event_id", ticketId)
                .build();
        Gson gson = new Gson();

        HttpHeaders headers = createHttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = createInstance().exchange(eventIdUrl.toUriString(), HttpMethod.GET, entity, String.class);
        System.out.println("Result - status (" + response.getStatusCode() + ") has body: " + response.hasBody());
        return gson.fromJson(response.getBody(), TicketResponse.class);

//
    }


    private HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.set("X-Token", xToken);
        headers.set("X-Signature", xSignature);
        return headers;
    }

    private RestTemplate createInstance() {
        return new RestTemplate();
    }
}
