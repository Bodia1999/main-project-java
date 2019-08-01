package com.academy.project.demo.service;

import com.academy.project.demo.dto.UserSummary;
import com.academy.project.demo.dto.request.UserUpdateRequest;
import com.academy.project.demo.entity.User;
import com.academy.project.demo.exception.WrongInputException;
import com.academy.project.demo.repository.UserRepository;
import com.academy.project.demo.security.CustomUserDetailsService;
import com.academy.project.demo.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;

    public UserSummary getCurrentUser(UserPrincipal userPrincipal) {
        return UserSummary.builder()
                .id(userPrincipal.getId())
                .email(userPrincipal.getEmail())
                .surname(userPrincipal.getSurname())
                .phoneNumber(userPrincipal.getPhoneNumber())
                .name(userPrincipal.getName()).role(userPrincipal.getAuthorities().toString())
                .card(userPrincipal.getCreditCardResponses())
                .build();
    }

    public UserSummary update (UserUpdateRequest userUpdateRequest){
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
        User save = userRepository.save(user);
        UserPrincipal userPrincipal = UserPrincipal.create(save);
        return UserSummary.builder()
                .id(userPrincipal.getId())
                .email(userPrincipal.getEmail())
                .surname(userPrincipal.getSurname())
                .phoneNumber(userPrincipal.getPhoneNumber())
                .name(userPrincipal.getName()).role(userPrincipal.getAuthorities().toString())
                .card(userPrincipal.getCreditCardResponses())
                .build();
    }
}
