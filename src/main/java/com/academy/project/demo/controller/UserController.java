package com.academy.project.demo.controller;

import com.academy.project.demo.dto.UserSummary;
import com.academy.project.demo.dto.request.LoginRequest;
import com.academy.project.demo.dto.request.UserUpdateRequest;
import com.academy.project.demo.dto.response.JwtAuthenticationResponse;
import com.academy.project.demo.security.CurrentUser;
import com.academy.project.demo.security.UserPrincipal;
import com.academy.project.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public HttpEntity<UserSummary> getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return new ResponseEntity<>(userService.getCurrentUser(currentUser), HttpStatus.OK);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public HttpEntity<UserSummary> updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        return new ResponseEntity<>(userService.update(userUpdateRequest), HttpStatus.OK);
    }

    @PutMapping("/changePassword")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public HttpEntity<JwtAuthenticationResponse> changePassword(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(userService.changePassword(loginRequest), HttpStatus.OK);
    }
}
