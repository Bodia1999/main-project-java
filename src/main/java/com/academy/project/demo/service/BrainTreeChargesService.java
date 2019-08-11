package com.academy.project.demo.service;

import com.academy.project.demo.dto.request.OrderRequest;
import com.academy.project.demo.dto.request.SignUpRequest;
import com.academy.project.demo.dto.request.braintree.ChargeRequest;
import com.academy.project.demo.dto.request.braintree.CreditCardToBrainTreeRequest;
import com.academy.project.demo.dto.response.ticket.evolution.tickets.TicketGroupResponse;
import com.academy.project.demo.entity.Order;
import com.academy.project.demo.exception.BadRequestException;
import com.academy.project.demo.exception.WrongInputException;
import com.braintreegateway.*;
import com.google.gson.GsonBuilder;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BrainTreeChargesService {
    private final OrderService orderService;
    private final TicketEvolutionService ticketEvolutionService;

    private static BraintreeGateway gateway = new BraintreeGateway(
            Environment.SANDBOX,
            "5kzr7ny3bhwpdg8n",
            "8b6zxn2ymhr8zh6f",
            "a48351fc221800ad1b3b431e3e78e323"
    );

    public String createCustomer(SignUpRequest signUpRequest) throws StripeException {
        CustomerRequest customerRequest = new CustomerRequest()
                .email(signUpRequest.getEmail())
                .firstName(signUpRequest.getName())
                .lastName(signUpRequest.getSurname())
                .phone(signUpRequest.getPhoneNumber());
        Result<com.braintreegateway.Customer> result = gateway.customer().create(customerRequest);
        if (!result.isSuccess()) {
            throw new BadRequestException("Cannot create a customer");
        }
        return result.getTarget().getId();

    }

    public String addCreditCardToCustomer(CreditCardToBrainTreeRequest creditCardToBrainTreeRequest) throws Exception {
        Customer customer = retrieveCustomer(creditCardToBrainTreeRequest.getCustomerStripeId());
        CreditCardRequest creditCard = new CreditCardRequest()
                .cardholderName(creditCardToBrainTreeRequest.getName())
                .customerId(creditCardToBrainTreeRequest.getCustomerStripeId())
                .cvv(creditCardToBrainTreeRequest.getCvc())
                .expirationMonth(creditCardToBrainTreeRequest.getExpiryMonth())
                .expirationYear(creditCardToBrainTreeRequest.getExpiryYear())
                .number(creditCardToBrainTreeRequest.getNumber())
                .billingAddress()
                .firstName(creditCardToBrainTreeRequest.getFirstName())
                .lastName(creditCardToBrainTreeRequest.getLastName())
                .streetAddress(creditCardToBrainTreeRequest.getAddress())
                .countryName(creditCardToBrainTreeRequest.getCountry())
                .postalCode(creditCardToBrainTreeRequest.getCvc())
                .locality(creditCardToBrainTreeRequest.getCity()).done();


        Result<CreditCard> result = gateway.creditCard().create(creditCard);
        String token = result.getTarget().getToken();
        for (CreditCard creditCard1 : customer.getCreditCards()) {
            if (creditCard1.getUniqueNumberIdentifier().equals(result.getTarget().getUniqueNumberIdentifier())) {
                deleteCardStripe(creditCardToBrainTreeRequest.getCustomerStripeId(), token);
                throw new BadRequestException("Card already exists! Try again with another card");
            }

        }

        return token;
    }

    public String charge(ChargeRequest chargeRequest, String ticketId, Integer quantity) throws Exception {
        TicketGroupResponse infoAboutTickets = ticketEvolutionService.getInfoAboutTickets(ticketId);
        CreditCard creditCard = gateway.creditCard().find(chargeRequest.getSource());
        TransactionRequest transactionRequest = new TransactionRequest()
                .amount(new BigDecimal(infoAboutTickets.getWholesalePrice() * quantity))
                .paymentMethodToken(creditCard.getToken())
                .options()
                .submitForSettlement(true)
                .done();
        Result<Transaction> sale = gateway.transaction().sale(transactionRequest);
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setStripeChargeId(sale.getTarget().getId());
        orderRequest.setUserId(chargeRequest.getUserId());
        orderRequest.setAmount(sale.getTarget().getAmount().toString());
        orderRequest.setTypeOfEvents(infoAboutTickets.getType());
        orderRequest.setOccursAt(infoAboutTickets.getEvent().getOccursAt());
        orderRequest.setQuantity(quantity);
        orderRequest.setRow(infoAboutTickets.getRow());
        orderRequest.setSection(infoAboutTickets.getSection());
        orderRequest.setIfRefunded(false);
        orderService.save(orderRequest);
        return new GsonBuilder().setPrettyPrinting().create().toJson(sale.getTarget());
    }


    private Customer retrieveCustomer(String cusId) {
        return gateway.customer().find(cusId);
    }

    public void deleteCardStripe(String customerId, String cardId) {
        Customer customer = retrieveCustomer(customerId);
        for (CreditCard creditCard : customer.getCreditCards()) {
            if (creditCard.getToken().equals(cardId)) {
                gateway.creditCard().delete(cardId);
            }
        }
    }

    public List<CreditCard> getAllCardByUser(String customerId) {
        Customer customer = retrieveCustomer(customerId);
        return customer.getCreditCards();
    }

    public String getCardById(String customerId, String cardId) {
        Customer customer = retrieveCustomer(customerId);
        for (CreditCard creditCard : customer.getCreditCards()) {
            if (creditCard.getToken().equals(cardId)) {
                return new GsonBuilder().setPrettyPrinting().create().toJson(creditCard);
            }
        }
        throw new WrongInputException("There is no such credit card with id = " + cardId);
    }

    public String refundMoney(String transactionId) {
        Result<Transaction> refund = gateway.transaction().refund(transactionId);

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
