package com.academy.project.demo.controller;

import com.academy.project.demo.dto.response.JwtAuthenticationResponse;
import com.academy.project.demo.dto.request.LoginRequest;
import com.academy.project.demo.dto.request.SignUpRequest;
import com.academy.project.demo.service.AuthService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public JwtAuthenticationResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.authenticateUser(loginRequest);
    }

    @PostMapping("/signup")
    @ResponseStatus(OK)
    public Long register(@Valid @RequestBody SignUpRequest signUpRequest) throws StripeException {
        return authService.registerUser(signUpRequest);
    }
}
