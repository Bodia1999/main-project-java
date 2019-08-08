package com.academy.project.demo.controller;

import com.academy.project.demo.dto.request.stripe.ChargeRequest;
import com.academy.project.demo.dto.request.stripe.CreditCardToStripeRequest;
import com.academy.project.demo.dto.request.stripe.CustomerToStripeRequest;
import com.academy.project.demo.service.StripeChargesService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentSource;
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


    private final StripeChargesService paymentsService;
    @Value("${stripe.secret.key}")
    private String stripeKey;

    @ExceptionHandler(StripeException.class)
    public void handleError(Model model, StripeException ex) throws Exception {
        model.addAttribute("error", ex.getMessage());
        throw new Exception("error: " + ex.getMessage() + " code:"+ ex.getStatusCode());
    }

    @PostMapping("/customers")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public HttpEntity<String> createCustomer(@RequestBody CustomerToStripeRequest customerToStripeRequest) throws StripeException {
        return new ResponseEntity<>(paymentsService.createCustomer(customerToStripeRequest), HttpStatus.OK);
    }

    @PostMapping("/addCard")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public HttpEntity<String> addCard(@RequestBody CreditCardToStripeRequest creditCardToStripeRequest) throws StripeException, Exception {
        return new ResponseEntity<>(paymentsService.addCreditCardToCustomer(creditCardToStripeRequest),HttpStatus.OK);
    }

    @PostMapping("/charge")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public HttpEntity<String> charge(@RequestBody ChargeRequest chargeRequest) throws Exception {
        return new ResponseEntity<>(paymentsService.charge(chargeRequest), HttpStatus.OK);
    }

    @GetMapping("/getAllCardsByCustomer/{customerId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public HttpEntity<List<PaymentSource>> getAllOrOneCardByUser(@PathVariable String customerId) throws StripeException {
        return new ResponseEntity<>(paymentsService.getAllCardByUser(customerId), HttpStatus.OK);
    }

    @GetMapping("/getOneCardByCustomer/{customerId}/{cardId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public HttpEntity<String> getOneCardByUser(@PathVariable String customerId, @PathVariable String cardId) throws StripeException {
        return new ResponseEntity<>(paymentsService.getCardById(customerId, cardId), HttpStatus.OK);
    }

    @PostMapping("/refundMoney")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public HttpEntity<String> refundMoney() throws StripeException {
        return new ResponseEntity<>(paymentsService.refundMoney(), HttpStatus.OK);
    }



}
