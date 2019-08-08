package com.academy.project.demo.service;

import com.academy.project.demo.dto.request.stripe.ChargeRequest;
import com.academy.project.demo.dto.request.stripe.CreditCardToStripeRequest;
import com.academy.project.demo.dto.request.stripe.CustomerToStripeRequest;
import com.academy.project.demo.dto.request.OrderRequest;
import com.academy.project.demo.entity.Order;
import com.academy.project.demo.exception.BadRequestException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StripeChargesService {
    private final OrderService orderService;
    @Value("${stripe.secret.key}")
    private String stripeKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeKey;
    }

    public String createCustomer(CustomerToStripeRequest customerToStripeRequest) throws StripeException {
        Stripe.apiKey = stripeKey;
        Map<String, Object> customerParameter = new HashMap<>();
        customerParameter.put("email", customerToStripeRequest.getEmail());
        Customer newCustomer = Customer.create(customerParameter);
        return newCustomer.getId();
    }

    public String addCreditCardToCustomer(CreditCardToStripeRequest creditCardToStripeRequest) throws Exception {
        Customer customer = retrieveCustomer(creditCardToStripeRequest.getCustomerStripeId());
        Map<String, Object> tokenParams = new HashMap<>();
        Map<String, Object> cardParams = new HashMap<>();
        cardParams.put("name", creditCardToStripeRequest.getName());
        cardParams.put("number", creditCardToStripeRequest.getNumber());
        cardParams.put("exp_month", creditCardToStripeRequest.getExpiryMonth());
        cardParams.put("exp_year", creditCardToStripeRequest.getExpiryYear());
        cardParams.put("cvc", creditCardToStripeRequest.getCvc());
        tokenParams.put("card", cardParams);

        Token token = Token.create(tokenParams);

        List<PaymentSource> allCardByUser = getAllCardByUser(creditCardToStripeRequest.getCustomerStripeId());


        for (PaymentSource paymentSource1 : allCardByUser) {
            Card card = (Card) paymentSource1;
            if (card.getFingerprint().equals(token.getCard().getFingerprint())) {
                throw new BadRequestException("Card already exists! Try again with another card");
            }
        }


        Map<String, Object> source = new HashMap<>();
        source.put("source", token.getId());

        PaymentSource paymentSource = customer.getSources().create(source);

        return paymentSource.getId();
    }

    public String charge(ChargeRequest chargeRequest) throws Exception {
        Customer customer = retrieveCustomer(chargeRequest.getCustomerStripeId());
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", chargeRequest.getAmount());
        chargeParams.put("currency", chargeRequest.getCurrency());
        chargeParams.put("customer", customer.getId());
        if (chargeRequest.getSource() != null) {
            chargeParams.put("source", chargeRequest.getSource());
        }

        Charge charge = Charge.create(chargeParams);
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setStripeChargeId(charge.getId());
        orderRequest.setUserId(chargeRequest.getUserId());
        Double amount = charge.getAmount()/100d;
        System.out.println(amount);
        orderRequest.setAmount(String.valueOf(amount));
        orderService.save(orderRequest);

        return new GsonBuilder().setPrettyPrinting().create().toJson(charge);
    }



    private Customer retrieveCustomer(String cusId) throws StripeException {
        return Customer.retrieve(cusId);
    }

    public void deleteCardStripe(String customerId, String cardId) throws StripeException {
        Customer customer = retrieveCustomer(customerId);
        Card card = (Card) customer.getSources().retrieve(cardId);
        card.delete();
    }

    public List<PaymentSource> getAllCardByUser(String customerId) throws StripeException {
        Customer customer = retrieveCustomer(customerId);
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("object", "card");
        PaymentSourceCollection list = customer.getSources().list(stringObjectMap);
        return list.getData();
    }

    public String getCardById(String customerId, String cardId) throws StripeException{
        Customer customer = retrieveCustomer(customerId);
        Gson gson =new Gson();
        String s = gson.toJson(customer.getSources().retrieve(cardId));
        return s;

    }

    public String refundMoney() throws StripeException {
        Map<String, Object> refundParams = new HashMap<>();
        refundParams.put("charge", "ch_1F4qiaIL86epBHHqnh0a4Y1U");

        Refund refund = Refund.create(refundParams);

        if (refund.getStatus().equals("succeeded")) {
            Order order = orderService.findByStripeChargeId("ch_1F4qiaIL86epBHHqnh0a4Y1U");
            new OrderRequest();
            OrderRequest orderRequest = OrderRequest
                    .builder()
                    .stripeChargeId(order.getStripeChargeId())
                    .ifRefunded(true)
                    .ticketEvolutionId(order.getTicketEvolutionId())
                    .build();
            orderService.update(null, order,orderRequest );
        }
        return refund.toJson();
    }


}
