package com.academy.project.demo.controller;

import com.academy.project.demo.dto.request.ChargeRequest;
import com.academy.project.demo.dto.request.CreditCardToStripeRequest;
import com.academy.project.demo.dto.request.CustomerToStripeRequest;
import com.academy.project.demo.service.StripeChargesService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.PaymentSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

//    @PostMapping("/charge")
//    public String charge(ChargeRequest chargeRequest)
//            throws AuthenticationException,
//            StripeException{
//        chargeRequest.setDescription("Example charge");
//        chargeRequest.setCurrency(ChargeRequest.Currency.USD);
//        return paymentsService.charge(chargeRequest);
//    }

    @ExceptionHandler(StripeException.class)
    public void handleError(Model model, StripeException ex) throws Exception {
        model.addAttribute("error", ex.getMessage());
        throw new Exception("error: " + ex.getMessage() + " code:"+ ex.getStatusCode());
    }

    @PostMapping("/customers")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public String createCustomer(@RequestBody CustomerToStripeRequest customerToStripeRequest) throws StripeException {
        return paymentsService.createCustomer(customerToStripeRequest);
    }

    @PostMapping("/addCard")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public String addCard(@RequestBody CreditCardToStripeRequest creditCardToStripeRequest) throws StripeException, Exception {
        return paymentsService.addCreditCardToCustomer(creditCardToStripeRequest);
    }

    @PostMapping("/charge")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public String charge(@RequestBody ChargeRequest chargeRequest) throws StripeException {
        return paymentsService.charge(chargeRequest);
    }

    @GetMapping("/getAllCardsByCustomer/{customerId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public List<PaymentSource> getAllCardsByCustomer(@PathVariable String customerId) throws StripeException {
        return paymentsService.getAllCardByUser(customerId);
    }


}
