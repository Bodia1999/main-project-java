package com.academy.project.demo.service;

import com.academy.project.demo.dto.request.OrderRequest;
import com.academy.project.demo.dto.request.SignUpRequest;
import com.academy.project.demo.dto.request.stripe.ChargeRequest;
import com.academy.project.demo.dto.request.stripe.CreditCardToStripeRequest;
import com.academy.project.demo.entity.Order;
import com.academy.project.demo.exception.BadRequestException;
import com.braintreegateway.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;

//import com.stripe.model.Customer;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StripeChargesService {
    private final OrderService orderService;
//    @Value("${stripe.secret.key}")
//    private String stripeKey;

    private static BraintreeGateway gateway = new BraintreeGateway(
            Environment.SANDBOX,
            "5kzr7ny3bhwpdg8n",
            "8b6zxn2ymhr8zh6f",
            "a48351fc221800ad1b3b431e3e78e323"
    );

//    ClientTokenRequest clientTokenRequest = new ClientTokenRequest()
//            .customerId(aCustomerId);
//    String clientToken = gateway.clientToken().generate(clientTokenRequest);


//    @PostConstruct
//    public void init() {
//        Stripe.apiKey = stripeKey;
//    }

    public String createCustomer(SignUpRequest signUpRequest) throws StripeException {
//        Stripe.apiKey = stripeKey;
//        Map<String, Object> customerParameter = new HashMap<>();
//        customerParameter.put("email", customerToStripeRequest.getEmail());
//        Customer newCustomer = Customer.create(customerParameter);
//        return newCustomer.getId();

        CustomerRequest customerRequest = new CustomerRequest()
                .email(signUpRequest.getEmail())
                .firstName(signUpRequest.getName())
                .lastName(signUpRequest.getSurname())
                .phone(signUpRequest.getPhoneNumber());
        Result<com.braintreegateway.Customer> result = gateway.customer().create(customerRequest);

        result.isSuccess();
// true

        return result.getTarget().getId();

    }

    public String addCreditCardToCustomer(CreditCardToStripeRequest creditCardToStripeRequest) throws Exception {
//        Customer customer = retrieveCustomer(creditCardToStripeRequest.getCustomerStripeId());
//        Map<String, Object> tokenParams = new HashMap<>();
//        Map<String, Object> cardParams = new HashMap<>();
//        cardParams.put("name", creditCardToStripeRequest.getName());
//        cardParams.put("number", creditCardToStripeRequest.getNumber());
//        cardParams.put("exp_month", creditCardToStripeRequest.getExpiryMonth());
//        cardParams.put("exp_year", creditCardToStripeRequest.getExpiryYear());
//        cardParams.put("cvc", creditCardToStripeRequest.getCvc());
//        tokenParams.put("card", cardParams);
//
//        Token token = Token.create(tokenParams);
//
//        List<PaymentSource> allCardByUser = getAllCardByUser(creditCardToStripeRequest.getCustomerStripeId());
//
//
//        for (PaymentSource paymentSource1 : allCardByUser) {
//            Card card = (Card) paymentSource1;
//            if (card.getFingerprint().equals(token.getCard().getFingerprint())) {
//                throw new BadRequestException("Card already exists! Try again with another card");
//            }
//        }
//
//
//        Map<String, Object> source = new HashMap<>();
//        source.put("source", token.getId());
//
//        PaymentSource paymentSource = customer.getSources().create(source);
//
//        return paymentSource.getId();
        Customer customer = retrieveCustomer(creditCardToStripeRequest.getCustomerStripeId());
        CreditCardRequest creditCard = new CreditCardRequest()
                .cardholderName(creditCardToStripeRequest.getName())
                .customerId(creditCardToStripeRequest.getCustomerStripeId())
                .cvv(creditCardToStripeRequest.getCvc())
                .expirationMonth(creditCardToStripeRequest.getExpiryMonth())
                .expirationYear(creditCardToStripeRequest.getExpiryYear())
                .number(creditCardToStripeRequest.getNumber())
                .billingAddress()
                .firstName(creditCardToStripeRequest.getFirstName())
                .lastName(creditCardToStripeRequest.getLastName())
                .streetAddress(creditCardToStripeRequest.getAddress())
                .countryName(creditCardToStripeRequest.getCountry())
                .postalCode(creditCardToStripeRequest.getCvc())
                .locality(creditCardToStripeRequest.getCity()).done();


        Result<CreditCard> result = gateway.creditCard().create(creditCard);
        String token = result.getTarget().getToken();
        for (CreditCard creditCard1 : customer.getCreditCards()) {
            if (creditCard1.getUniqueNumberIdentifier().equals(result.getTarget().getUniqueNumberIdentifier())) {
                deleteCardStripe(creditCardToStripeRequest.getCustomerStripeId(), token);
                throw new BadRequestException("Card already exists! Try again with another card");
            }

        }

//        List<> allCardByUser = getAllCardByUser(creditCardToStripeRequest.getCustomerStripeId());
////
////
//        for (PaymentSource paymentSource1 : allCardByUser) {
//            Card card = (Card) paymentSource1;
//            if (card.getFingerprint().equals(token.getCard().getFingerprint())) {
//                throw new BadRequestException("Card already exists! Try again with another card");
//            }
//        }
        return token;
    }

    public String charge(ChargeRequest chargeRequest) throws Exception {
//        Customer customer = retrieveCustomer(chargeRequest.getCustomerStripeId());
//        Map<String, Object> chargeParams = new HashMap<>();
//        chargeParams.put("amount", chargeRequest.getAmount());
//        chargeParams.put("currency", chargeRequest.getCurrency());
//        chargeParams.put("customer", customer.getId());
//        if (chargeRequest.getSource() != null) {
//            chargeParams.put("source", chargeRequest.getSource());
//        }
//
//        Charge charge = Charge.create(chargeParams);
//        OrderRequest orderRequest = new OrderRequest();
//        orderRequest.setStripeChargeId(charge.getId());
//        orderRequest.setUserId(chargeRequest.getUserId());
//        Double amount = charge.getAmount() / 100d;
//        System.out.println(amount);
//        orderRequest.setAmount(String.valueOf(amount));
//        orderService.save(orderRequest);
//
//        return new GsonBuilder().setPrettyPrinting().create().toJson(charge);
        CreditCard creditCard = gateway.creditCard().find(chargeRequest.getSource());
        TransactionRequest transactionRequest = new TransactionRequest()
                .amount(new BigDecimal(chargeRequest.getAmount()))
                .paymentMethodToken(creditCard.getToken())
                .options()
                .submitForSettlement(true)
                .done();
        Result<Transaction> sale = gateway.transaction().sale(transactionRequest);
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setStripeChargeId(sale.getTarget().getOrderId());
        orderRequest.setUserId(chargeRequest.getUserId());
        orderRequest.setAmount(sale.getTarget().getAmount().toString());
        orderService.save(orderRequest);
        return new GsonBuilder().setPrettyPrinting().create().toJson(sale.getTarget());
    }


    private Customer retrieveCustomer(String cusId) throws StripeException {
        return gateway.customer().find(cusId);
    }

    public void deleteCardStripe(String customerId, String cardId) throws StripeException {
        Customer customer = retrieveCustomer(customerId);
        for (CreditCard creditCard : customer.getCreditCards()) {
            if (creditCard.getToken().equals(cardId)) {
                gateway.creditCard().delete(cardId);
            }
        }
    }

    public List<CreditCard> getAllCardByUser(String customerId) throws StripeException {
        Customer customer = retrieveCustomer(customerId);
//        Map<String, Object> stringObjectMap = new HashMap<>();
//        stringObjectMap.put("object", "card");
//        PaymentSourceCollection list = customer.getSources().list(stringObjectMap);
        return customer.getCreditCards();
    }

    public String getCardById(String customerId, String cardId) throws StripeException {
        System.out.println(customerId + " " + cardId);
        Customer customer = retrieveCustomer(customerId);
        String answer = "";
        for (CreditCard creditCard : customer.getCreditCards()) {
            System.out.println(creditCard.getUniqueNumberIdentifier());
            if (creditCard.getToken().equals(cardId)) {
                return new GsonBuilder().setPrettyPrinting().create().toJson(creditCard);
            }
        }

        return answer;

    }

    public String refundMoney(String transactionId) throws StripeException {
        Result<Transaction> refund = gateway.transaction().refund(transactionId);

//        Map<String, Object> refundParams = new HashMap<>();
//        refundParams.put("charge", "ch_1F4qiaIL86epBHHqnh0a4Y1U");
//
//        Refund refund = Refund.create(refundParams);

        if (refund.isSuccess()) {
            Order order = orderService.findByStripeChargeId(transactionId);
            new OrderRequest();
            OrderRequest orderRequest = OrderRequest
                    .builder()
                    .stripeChargeId(order.getStripeChargeId())
                    .ifRefunded(true)
                    .ticketEvolutionId(order.getTicketEvolutionId())
                    .build();
            orderService.update(null, order, orderRequest);
        }
        return new GsonBuilder().setPrettyPrinting().create().toJson(refund);
    }


}
