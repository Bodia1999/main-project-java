package com.academy.project.demo.service;

import com.academy.project.demo.dto.request.CreditCardRequest;
import com.academy.project.demo.dto.request.stripe.CreditCardToStripeRequest;
import com.academy.project.demo.entity.CreditCard;
import com.academy.project.demo.exception.WrongInputException;
import com.academy.project.demo.repository.CreditCardRepository;
import com.academy.project.demo.security.CustomUserDetailsService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CreditCardService {

    private final CreditCardRepository creditCardRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final StripeChargesService stripeChargesService;

    public CreditCard getOne(Long id) {
        return creditCardRepository.findById(id).orElseThrow(() -> new WrongInputException("There is no such credit card with " + id + " id"));
    }

    public CreditCard save(Long userId, CreditCardToStripeRequest creditCardToStripeRequest) throws Exception {
        String cardId = stripeChargesService.addCreditCardToCustomer(creditCardToStripeRequest);
        CreditCardRequest creditCardRequest = new CreditCardRequest(userId, creditCardToStripeRequest.getNameOfCreditCard(), cardId );
        return creditCardRepository.save(creditCardToRequest(null, creditCardRequest));
    }

    public List<CreditCard> getAll() {
        return creditCardRepository.findAll();
    }

    public CreditCard update(Long id, CreditCardRequest cardRequest) {
        return creditCardRepository.save(creditCardToRequest(getOne(id), cardRequest));
    }

    private CreditCard creditCardToRequest(CreditCard creditCard, CreditCardRequest cardRequest) {
        if (creditCard == null) {
            creditCard = new CreditCard();
        }
//        creditCard.setToken(cardRequest.getToken());
        creditCard.setNameOfCard(cardRequest.getNameOfCard());
        creditCard.setStripeCardId(cardRequest.getStripeCardId());
        creditCard.setUser(customUserDetailsService.loadUserById(cardRequest.getUserId()));
        return creditCard;
    }

    public void delete( String customerId, Long id) throws StripeException {
        CreditCard one = getOne(id);
        stripeChargesService.deleteCardStripe(customerId, one.getStripeCardId());
        creditCardRepository.delete(getOne(id));
    }
}
