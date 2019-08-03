package com.academy.project.demo.service;

import com.academy.project.demo.dto.request.ChargeRequest;
import com.academy.project.demo.dto.request.CreditCardToStripeRequest;
import com.academy.project.demo.dto.request.CustomerToStripeRequest;
import com.google.gson.GsonBuilder;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripeChargesService {
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

    public String addCreditCardToCustomer(CreditCardToStripeRequest creditCardToStripeRequest) throws StripeException, CloneNotSupportedException {
        Customer customer = retrieveCustomer(creditCardToStripeRequest.getCustomerStripeId());
//        PaymentSourceCollection externalAccountCollection = customer.getSources();
        Map<String, Object> tokenParams = new HashMap<>();
        Map<String, Object> cardParams = new HashMap<>();
        cardParams.put("name", creditCardToStripeRequest.getName());
        cardParams.put("number", creditCardToStripeRequest.getNumber());
        cardParams.put("exp_month", creditCardToStripeRequest.getExpiryMonth());
        cardParams.put("exp_year", creditCardToStripeRequest.getExpiryYear());
        cardParams.put("cvc", creditCardToStripeRequest.getCvc());
        tokenParams.put("card", cardParams);

        Token token = Token.create(tokenParams);

        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("object", "card");
        PaymentSourceCollection list = customer.getSources().list(stringObjectMap);

        for (int i = 0; i < list.getData().size(); i++) {
           Card card = (Card) list.getData().get(i);
           if (card.getFingerprint().equals(token.getCard().getFingerprint())){
               throw new CloneNotSupportedException("Card already exists! Try again with another card");
           }
        }


        Map<String, Object> source = new HashMap<>();
        source.put("source", token.getId());

        PaymentSource paymentSource = customer.getSources().create(source);

        return paymentSource.getId();
    }

    public String charge(ChargeRequest chargeRequest) throws StripeException {
        Customer customer = retrieveCustomer(chargeRequest.getCustomerStripeId());
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", chargeRequest.getAmount());
        chargeParams.put("currency", chargeRequest.getCurrency());
        chargeParams.put("customer", customer.getId());
        if (chargeRequest.getSource() != null) {
            chargeParams.put("source", chargeRequest.getSource());
        }

        Charge charge = Charge.create(chargeParams);

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


}