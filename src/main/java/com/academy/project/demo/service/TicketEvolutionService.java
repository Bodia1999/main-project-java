package com.academy.project.demo.service;

import com.academy.project.demo.dto.request.ticket.evolution.TicketEvolutionRequest;
import com.academy.project.demo.dto.request.ticket.evolution.orders.*;
import com.academy.project.demo.dto.response.event.MainInfoTicketResponse;
import com.academy.project.demo.dto.response.ticket.evolution.tickets.TicketGroupResponse;
import com.academy.project.demo.dto.response.ticket.evolution.tickets.TicketResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketEvolutionService {
    @Value("${ticket.evolution.token}")
    private String xToken;

//    @Value("${ticket.evolution.signature}")
//    private String xSignature;

    @Value("${ticket.evolution.secret.key}")
    private String secretKey;

    @Value("${ticket.evolution.url}")
    private String url;

    private Gson gson = new Gson();


    public MainInfoTicketResponse getAllEventsByQuery(TicketEvolutionRequest ticketEvolutionRequest) throws Exception {
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
        String response = makeRequest(uriComponents, null, HttpMethod.GET);

        return gson.fromJson(response, MainInfoTicketResponse.class);
    }

    public TicketResponse getInfoAboutEvent(String ticketId) throws Exception {

        String newUrl = url + "/ticket_groups";
        UriComponents eventIdUrl = UriComponentsBuilder
                .fromHttpUrl(newUrl)
                .queryParam("event_id", ticketId)
                .build();

        String response = makeRequest(eventIdUrl, null, HttpMethod.GET);
        TicketResponse ticketResponse = gson.fromJson(response, TicketResponse.class);
        TicketResponse ticketResponseModifiedPrice = changePriceInTicketResponse(ticketResponse);

        return ticketResponseModifiedPrice;

    }

    public TicketGroupResponse getInfoAboutTickets(String ticketId) throws Exception {
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(url + "/ticket_groups/" + ticketId)
                .build();
        String response = makeRequest(uriComponents, null, HttpMethod.GET);
        TicketGroupResponse ticketGroupResponse = gson.fromJson(response, TicketGroupResponse.class);
        TicketGroupResponse ticketGroupResponseModified = changePriceInTicketGroupResponse(ticketGroupResponse);

        return ticketGroupResponseModified;

    }

    public void createOrder() throws Exception {
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

        paymentRequest.setType("cash");
        paymentRequests.add(paymentRequest);

        orderRequest.setBuyerId(182066L);
        orderRequest.setBuyerReferenceNumber("123456789");
        orderRequest.setPayments(paymentRequests);
        orderRequest.setShippedItems(shippedItemRequests);
        orderRequests.add(orderRequest);


        String s = gson.toJson(orderTicketEvolutionRequest);
        String newUrl = url + "/orders";
        UriComponents build = UriComponentsBuilder.fromHttpUrl(newUrl).build();

        String response = makeRequest(build, s, HttpMethod.POST);

        System.out.println(response);

    }

    public String createCreditCard() throws Exception {
        String newUrl = url + "/orders";
        String body = "{\n" +
                "  \"orders\": [\n" +
                "    {\n" +
                "      \"shipped_items\": [\n" +
                "        {\n" +
                "          \"items\": [\n" +
                "            {\n" +
                "              \"ticket_group_id\": 555611506,\n" +
                "              \"quantity\": 1,\n" +
                "              \"price\": 342\n" +
                "            }\n" +
                "          ],\n" +
                "          \"type\": \"FedEx\",\n" +
                "          \"service_type\": \"LEAST_EXPENSIVE\",\n" +
                "          \"signature_type\": \"INDIRECT\",\n" +
                "          \"residential\": true,\n" +
                "          \"ship_to_company_name\": \"Leftorium\",\n" +
                "          \"ship_to_name\": \"Ned Flanders\",\n" +
                "          \"address_id\": 661989,\n" +
                "          \"phone_number_id\": 306540\n" +
                "        }\n" +
                "      ],\n" +
                "      \"billing_address_id\": 661989,\n" +
                "      \"payments\": [\n" +
                "        {\n" +
                "          \"type\": \"offline\",\n" +
                "        }\n" +
                "      ],\n" +
                "      \"seller_id\": 1942,\n" +
                "      \"client_id\": 103054,\n" +
                "      \"created_by_ip_address\": \"12.34.56.78\",\n" +
                "      \"service_fee\": 0,\n" +
                "      \"shipping\": 0,\n" +
                "      \"tax\": 0,\n" +
                "      \"additional_expense\": 0,\n" +
                "      \"discount\": 0,\n" +
                "      \"seller_reference_number\": \"123456789\",\n" +
                "      \"external_notes\": \"These notes will be visible to all parties\",\n" +
                "      \"internal_notes\": \"These notes will be visible only to your office (1937)\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

//        String body = "{\n" +
//                "  \"orders\": [\n" +
//                "    {\n" +
//                "      \"seller_id\": \"1942\",\n" +
//                "      \"client_id\": \"103054\",\n" +
//                "      \"payments\": [\n" +
//                "        {\n" +
//                "          \"type\": \"offline\"\n" +
//                "        }\n" +
//                "      ],\n" +
//                "      \"shipped_items\": [\n" +
//                "        {\n" +
//                "          \"items\": [\n" +
//                "            {\n" +
//                "              \"ticket_group_id\": 555611506,\n" +
//                "              \"quantity\": 1,\n" +
//                "              \"price\": 342\n" +
//                "            }\n" +
//                "          ]\n" +
//                "        }\n" +
//                "      ]\n" +
//                "    }\n" +
//                "  ]\n" +
//                "}";


        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(newUrl).build();
        String s = makeRequest(uriComponents, body, HttpMethod.POST);
        return new GsonBuilder().setPrettyPrinting().create().toJson(s);
    }


    private String makeRequest(UriComponents uriComponents, String body, HttpMethod method) throws Exception {
        String modifiedLink = method.toString() + " " + uriComponents.toString().substring(8);
        if (body != null) {
            modifiedLink = modifiedLink.concat("?").concat(body);
        }
        HttpHeaders headers = createHttpHeaders(modifiedLink);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = createInstanceOfRestTemplate().exchange(uriComponents.toUriString(), method, entity, String.class);
        System.out.println("Result - status (" + response.getStatusCode() + ") has body: " + response.hasBody());//        System.out.println(response.getBody());
        return response.getBody();
    }

    private HttpHeaders createHttpHeaders(String valueToEncode) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.set("X-Token", xToken);
        String encode = encode(secretKey, valueToEncode);
        headers.set("X-Signature", encode);
        return headers;
    }


    private RestTemplate createInstanceOfRestTemplate() {
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

    private String encode(String key, String data) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        return Base64.encodeBase64String(sha256_HMAC.doFinal(data.getBytes()));
    }

    private Long changePrice(Double price) {
        return Math.round(price * 1.15);
    }

}
