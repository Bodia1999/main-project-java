package com.academy.project.demo.service;


import com.academy.project.demo.dto.request.CreditCardToStripeRequest;
import com.academy.project.demo.dto.request.CustomerToStripeRequest;
import com.academy.project.demo.dto.response.JwtAuthenticationResponse;
import com.academy.project.demo.dto.request.LoginRequest;
import com.academy.project.demo.dto.request.SignUpRequest;
import com.academy.project.demo.entity.Role;
import com.academy.project.demo.entity.RoleName;
import com.academy.project.demo.entity.User;
import com.academy.project.demo.exception.AppException;
import com.academy.project.demo.exception.ConflictException;
import com.academy.project.demo.repository.RoleRepository;
import com.academy.project.demo.repository.UserRepository;
import com.academy.project.demo.security.JwtTokenProvider;
import com.academy.project.demo.security.UserPrincipal;
import com.stripe.exception.StripeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
public class AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider tokenProvider;
    private StripeChargesService stripeChargesService;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider, StripeChargesService stripeChargesService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.stripeChargesService = stripeChargesService;
    }

    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        log.info("User with [email: {}] has logged in", userPrincipal.getEmail());
        return new JwtAuthenticationResponse(jwt);
    }

    public Long registerUser(SignUpRequest signUpRequest) throws StripeException {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new ConflictException("Email [email: " + signUpRequest.getEmail() + "] is already taken");
        }

        String stripeCustomerId = stripeChargesService
                .createCustomer(CustomerToStripeRequest.builder().email(signUpRequest.getEmail()).build());

        User user = new User(signUpRequest.getName(),
                signUpRequest.getSurname(),
                signUpRequest.getEmail(),
                signUpRequest.getPassword(),
                signUpRequest.getPhoneNumber(),
                stripeCustomerId);

        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set. Add default roles to database."));

        user.setRoles(Collections.singleton(userRole));

        log.info("Successfully registered user with [email: {}]", user.getEmail());

        return userRepository.save(user).getId();
    }
}
