package com.academy.project.demo.controller;

import com.academy.project.demo.dto.request.SignUpRequest;
import com.academy.project.demo.dto.request.braintree.ChargeRequest;
import com.academy.project.demo.dto.request.braintree.CreditCardToBrainTreeRequest;
import com.academy.project.demo.service.BrainTreeChargesService;
import com.braintreegateway.CreditCard;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/chargeController")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChargeController {


    private final BrainTreeChargesService paymentsService;
    @Value("${stripe.secret.key}")
    private String stripeKey;

    @ExceptionHandler(StripeException.class)
    public void handleError(Model model, StripeException ex) throws Exception {
        model.addAttribute("error", ex.getMessage());
        throw new Exception("error: " + ex.getMessage() + " code:"+ ex.getStatusCode());
    }

    @PostMapping("/customers")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public HttpEntity<String> createCustomer(@RequestBody SignUpRequest signUpRequest) throws StripeException {
        return new ResponseEntity<>(paymentsService.createCustomer(signUpRequest), HttpStatus.OK);
    }

    @PostMapping("/addCard")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public HttpEntity<String> addCard(@RequestBody CreditCardToBrainTreeRequest creditCardToBrainTreeRequest) throws StripeException, Exception {
        return new ResponseEntity<>(paymentsService.addCreditCardToCustomer(creditCardToBrainTreeRequest),HttpStatus.OK);
    }

    @PostMapping("/charge/{ticketId}/{quantity}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public HttpEntity<String> charge(@RequestBody ChargeRequest chargeRequest, @PathVariable String ticketId, @PathVariable Integer quantity) throws Exception {
        return new ResponseEntity<>(paymentsService.charge(chargeRequest, ticketId, quantity), HttpStatus.OK);
    }

    @GetMapping("/getAllCardsByCustomer/{customerId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public HttpEntity<List<CreditCard>> getAllOrOneCardByUser(@PathVariable String customerId) throws StripeException {
        return new ResponseEntity<>(paymentsService.getAllCardByUser(customerId), HttpStatus.OK);
    }

    @GetMapping("/getOneCardByCustomer/{customerId}/{cardId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public HttpEntity<String> getOneCardByUser(@PathVariable String customerId, @PathVariable String cardId) throws StripeException {
        return new ResponseEntity<>(paymentsService.getCardById(customerId, cardId), HttpStatus.OK);
    }

    @PostMapping("/refundMoney/{transactionId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public HttpEntity<String> refundMoney(@PathVariable String transactionId) throws StripeException {
        return new ResponseEntity<>(paymentsService.refundMoney(transactionId), HttpStatus.OK);
    }



}
