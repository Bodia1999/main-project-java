package com.academy.project.demo.controller;

import com.academy.project.demo.dto.request.LoginRequest;
import com.academy.project.demo.dto.request.SignUpRequest;
import com.academy.project.demo.dto.response.JwtAuthenticationResponse;
import com.academy.project.demo.service.AuthService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationController {
    private final AuthService authService;

    @PostMapping("/signin")
    @ResponseStatus(OK)
    public HttpEntity<JwtAuthenticationResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(authService.authenticateUser(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/signup")
    @ResponseStatus(OK)
    public HttpEntity<Long> register(@Valid @RequestBody SignUpRequest signUpRequest) throws StripeException {
        return new ResponseEntity<>(authService.registerUser(signUpRequest),HttpStatus.OK);
    }
}
