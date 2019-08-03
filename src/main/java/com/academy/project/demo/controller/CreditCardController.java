package com.academy.project.demo.controller;

import com.academy.project.demo.dto.request.CreditCardRequest;
import com.academy.project.demo.dto.response.CreditCardResponse;
import com.academy.project.demo.service.CreditCardService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public CreditCardResponse getOne(@PathVariable Long id) {
        return new CreditCardResponse(creditCardService.getOne(id));
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<CreditCardResponse> getAll() {
        return creditCardService.getAll().stream().map(CreditCardResponse::new).collect(Collectors.toList());
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public CreditCardResponse save(@RequestBody CreditCardRequest cardRequest) {
        return new CreditCardResponse(creditCardService.save(cardRequest));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public CreditCardResponse update(@PathVariable Long id, @RequestBody CreditCardRequest cardRequest) {
        return new CreditCardResponse(creditCardService.update(id, cardRequest));
    }

    @DeleteMapping("/delete/{id}/{customerId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public void delete(@PathVariable Long id, @PathVariable String customerId) throws StripeException {
        creditCardService.delete(customerId,id);
    }

}
