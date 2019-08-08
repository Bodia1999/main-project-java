package com.academy.project.demo.service;

import com.academy.project.demo.dto.request.ticket.evolution.TicketEvolutionRequest;
import com.academy.project.demo.dto.request.ticket.evolution.orders.*;
import com.academy.project.demo.dto.response.event.MainInfoTicketResponse;
import com.academy.project.demo.dto.response.ticket.evolution.tickets.TicketGroupResponse;
import com.academy.project.demo.dto.response.ticket.evolution.tickets.TicketResponse;
import com.academy.project.demo.entity.Order;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TicketEvolutionService {
    @Value("${ticket.evolution.token}")
    private String xToken;

    @Value("${ticket.evolution.signature}")
    private String xSignature;

    @Value("${ticket.evolution.url}")
    private String url;

    private Gson gson = new Gson();


    public MainInfoTicketResponse getAllEventsByQuery(TicketEvolutionRequest ticketEvolutionRequest) throws IOException {
        UriComponents uriComponents;
        String newUrl = url + "/events";
        UriComponentsBuilder eventIdUrl = UriComponentsBuilder
                .fromHttpUrl(newUrl);
        if (ticketEvolutionRequest.getCityState().trim().length() != 0) {
            String replacement = ticketEvolutionRequest.getCityState().trim().replace(" ", "+");
            eventIdUrl.queryParam("city_state", replacement);
        }
        uriComponents = eventIdUrl
                .queryParam("page", ticketEvolutionRequest.getPage())
                .queryParam("per_page", ticketEvolutionRequest.getPerPage())
                .queryParam("order_by", "events.popularity_score DESC")
                .build();
        String response = makeRequest(uriComponents, null);

        return gson.fromJson(response, MainInfoTicketResponse.class);
    }

    public TicketResponse getInfoAboutEvent(String ticketId) {

        String newUrl = url + "/ticket_groups";
        UriComponents eventIdUrl = UriComponentsBuilder
                .fromHttpUrl(newUrl)
                .queryParam("event_id", ticketId)
                .build();

        String response = makeRequest(eventIdUrl, null);
        TicketResponse ticketResponse = gson.fromJson(response, TicketResponse.class);
        TicketResponse ticketResponseModifiedPrice = changePriceInTicketResponse(ticketResponse);

        return ticketResponseModifiedPrice;

    }

    public TicketGroupResponse getInfoAboutTickets(String ticketId) {
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(url + "/ticket_groups/" + ticketId)
                .build();
        String response = makeRequest(uriComponents,null);
        TicketGroupResponse ticketGroupResponse = gson.fromJson(response, TicketGroupResponse.class);
        TicketGroupResponse ticketGroupResponseModified = changePriceInTicketGroupResponse(ticketGroupResponse);

        return ticketGroupResponseModified;

    }

    public void createOrder() {
        OrderTicketEvolutionRequest orderTicketEvolutionRequest = new OrderTicketEvolutionRequest();
        OrderRequest orderRequest = new OrderRequest();
        ShippedItemRequest shippedItemRequest = new ShippedItemRequest();
        ItemRequest itemRequest = new ItemRequest();
        PaymentRequest paymentRequest = new PaymentRequest();
        List<ItemRequest> itemRequests = new ArrayList<>();
        List<PaymentRequest> paymentRequests = new ArrayList<>();
        List<ShippedItemRequest> shippedItemRequests = new ArrayList<>();
        List<OrderRequest> orderRequests = new ArrayList<>();

        orderTicketEvolutionRequest.setOrders(orderRequests);

        itemRequest.setTicketGroupId(573652465L);
        itemRequest.setPrice(2025L);
        itemRequest.setQuantity(1L);
        itemRequests.add(itemRequest);

        shippedItemRequest.setItems(itemRequests);
        shippedItemRequests.add(shippedItemRequest);

        paymentRequest.setType("credit_card");
        paymentRequests.add(paymentRequest);

        orderRequest.setBuyerId(182066L);
        orderRequest.setBuyerReferenceNumber("123456789");
        orderRequest.setPayments(paymentRequests);
        orderRequest.setShippedItems(shippedItemRequests);
        orderRequests.add(orderRequest);


        String s = gson.toJson(orderTicketEvolutionRequest);
        String newUrl = url + "/orders";
        UriComponents build = UriComponentsBuilder.fromHttpUrl(newUrl).build();

        String response = makeRequest(build, s);

        System.out.println(response);

    }


    private HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.set("X-Token", xToken);
        headers.set("X-Signature", xSignature);
        return headers;
    }

    private String makeRequest(UriComponents uriComponents, String body) {
        HttpHeaders headers = createHttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        System.out.println(entity.getBody());
        ResponseEntity<String> response = createInstance().exchange(uriComponents.toUriString(), HttpMethod.GET, entity, String.class);
        System.out.println("Result - status (" + response.getStatusCode() + ") has body: " + response.hasBody());
        System.out.println(response.getBody());

        return response.getBody();
    }


    private RestTemplate createInstance() {
        return new RestTemplate();
    }

    private TicketResponse changePriceInTicketResponse(TicketResponse ticketResponse) {
        if (ticketResponse.getTicketGroups().isEmpty()) {
            return ticketResponse;
        }
        for (TicketGroupResponse ticketGroup : ticketResponse.getTicketGroups()) {
            Long aLong = changePrice(ticketGroup.getWholesalePrice());
            ticketGroup.setWholesalePrice(aLong.doubleValue());
        }
        return ticketResponse;
    }

    private TicketGroupResponse changePriceInTicketGroupResponse(TicketGroupResponse ticketGroupResponse) {
        if (ticketGroupResponse == null) {
            return new TicketGroupResponse();
        }

        Long aLong = changePrice(ticketGroupResponse.getWholesalePrice());
        ticketGroupResponse.setWholesalePrice(aLong.doubleValue());
        return ticketGroupResponse;
    }

    private Long changePrice(Double price) {
        return Math.round(price * 1.15);
    }

}
