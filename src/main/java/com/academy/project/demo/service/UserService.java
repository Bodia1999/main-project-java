package com.academy.project.demo.service;

import com.academy.project.demo.dto.UserSummary;
import com.academy.project.demo.dto.request.LoginRequest;
import com.academy.project.demo.dto.request.UserUpdateRequest;
import com.academy.project.demo.dto.response.JwtAuthenticationResponse;
import com.academy.project.demo.entity.User;
import com.academy.project.demo.exception.WrongInputException;
import com.academy.project.demo.repository.UserRepository;
import com.academy.project.demo.security.CustomUserDetailsService;
import com.academy.project.demo.security.JwtTokenProvider;
import com.academy.project.demo.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    public UserSummary getCurrentUser(UserPrincipal userPrincipal) {
        return UserSummary.builder()
                .id(userPrincipal.getId())
                .email(userPrincipal.getEmail())
                .surname(userPrincipal.getSurname())
                .phoneNumber(userPrincipal.getPhoneNumber()).
                stripeCustomerId(userPrincipal.getStripeCustomerId())
                .name(userPrincipal.getName()).role(userPrincipal.getAuthorities().toString())
                .card(userPrincipal.getCreditCardResponses())
                .orderResponses(userPrincipal.getOrderResponses())
                .build();
    }

    public UserSummary update(UserUpdateRequest userUpdateRequest) {
        User user = userRepository
                .findById(userUpdateRequest.getId())
                .orElseThrow(() -> new WrongInputException("User with id = " + userUpdateRequest.getId() + " " +
                        "doesn`t exist"));
        System.out.println(user);
        if (userUpdateRequest.getEmail() != null) {
            user.setEmail(userUpdateRequest.getEmail());
        }
        if (userUpdateRequest.getName() != null) {
            user.setName(userUpdateRequest.getName());
        }
        if (userUpdateRequest.getSurname() != null) {
            user.setSurname(userUpdateRequest.getSurname());
        }
        if (userUpdateRequest.getPhoneNumber() != null) {
            user.setPhoneNumber(userUpdateRequest.getPhoneNumber());
        }

        if (userUpdateRequest.getStripeCustomerId() != null) {
            user.setStripeCustomerId(userUpdateRequest.getStripeCustomerId());
        }
        User save = userRepository.save(user);
        UserPrincipal userPrincipal = UserPrincipal.create(save);
        return UserSummary.builder()
                .id(userPrincipal.getId())
                .email(userPrincipal.getEmail())
                .surname(userPrincipal.getSurname())
                .phoneNumber(userPrincipal.getPhoneNumber())
                .stripeCustomerId(userPrincipal.getStripeCustomerId())
                .name(userPrincipal.getName()).role(userPrincipal.getAuthorities().toString())
                .card(userPrincipal.getCreditCardResponses())
                .orderResponses(userPrincipal.getOrderResponses())
                .build();
    }

    public JwtAuthenticationResponse changePassword(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new WrongInputException("There is no such user with " + loginRequest.getEmail() + " email!"));
        boolean matches = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
        if (!matches) {
            throw new WrongInputException("Passwords do not match");
        } else {
            user.setPassword(passwordEncoder.encode(loginRequest.getNewPassword()));
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getNewPassword()
                )
        );
        String jwt = tokenProvider.generateToken(authentication);
        return  new JwtAuthenticationResponse(jwt);
    }
}
