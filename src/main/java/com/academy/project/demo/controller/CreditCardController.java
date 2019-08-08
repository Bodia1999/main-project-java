package com.academy.project.demo.controller;

import com.academy.project.demo.dto.request.CreditCardRequest;
import com.academy.project.demo.dto.request.stripe.CreditCardToStripeRequest;
import com.academy.project.demo.dto.response.CreditCardResponse;
import com.academy.project.demo.service.CreditCardService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/creditCard")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CreditCardController {

    private final CreditCardService creditCardService;

    @GetMapping("/getOne/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HttpEntity<CreditCardResponse> getOne(@PathVariable Long id) {
        return new ResponseEntity<>(new CreditCardResponse(creditCardService.getOne(id)), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HttpEntity<List<CreditCardResponse>> getAll() {
        return new ResponseEntity<>(creditCardService.getAll().stream().map(CreditCardResponse::new).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping("/save/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public HttpEntity<CreditCardResponse> save(@PathVariable Long id, @RequestBody CreditCardToStripeRequest cardRequest) throws Exception {
        return new ResponseEntity<>(new CreditCardResponse(creditCardService.save(id, cardRequest)), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public HttpEntity<CreditCardResponse> update(@PathVariable Long id, @RequestBody CreditCardRequest cardRequest) {
        return new ResponseEntity<>(new CreditCardResponse(creditCardService.update(id, cardRequest)), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}/{customerId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public void delete(@PathVariable Long id, @PathVariable String customerId) throws StripeException {
        creditCardService.delete(customerId, id);
    }


}
