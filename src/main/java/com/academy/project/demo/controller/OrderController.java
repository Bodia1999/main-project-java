package com.academy.project.demo.controller;

import com.academy.project.demo.dto.response.OrderResponse;
import com.academy.project.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/getAllById/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public List<OrderResponse> getAllByUserId(@PathVariable Long userId) {
        return orderService.getAllByUserId(userId).stream().map(OrderResponse::new).collect(Collectors.toList());
    }
}
