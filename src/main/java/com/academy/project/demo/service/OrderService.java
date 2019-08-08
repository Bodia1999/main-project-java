package com.academy.project.demo.service;

import com.academy.project.demo.dto.request.OrderRequest;
import com.academy.project.demo.entity.Order;
import com.academy.project.demo.exception.WrongInputException;
import com.academy.project.demo.repository.OrderRepository;
import com.academy.project.demo.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomUserDetailsService customUserDetailsService;


    public Order getOne(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new WrongInputException("There is no such order with " + id + " id"));
    }

    public List<Order> getAllByUserId(Long id) {
        return orderRepository.findAllByUserId(id);
    }
    public Order findByStripeChargeId(String chargeId) {
        return orderRepository.findByStripeChargeId(chargeId);
    }

    public Order save(OrderRequest orderRequest) throws Exception {
        return orderRepository.save(creditCardToRequest(null, orderRequest));
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Order update(Long id, Order order, OrderRequest orderRequest) {
        if (order == null) {
            return orderRepository.save(creditCardToRequest(getOne(id), orderRequest));
        } else {
            return orderRepository.save(creditCardToRequest(order, orderRequest));
        }
    }

    private Order creditCardToRequest(Order order, OrderRequest orderRequest) {
        if (order == null) {
            order = new Order();
            order.setCreatedAt(new Date());
        } else {
            order.setUpdatedAt(new Date());
        }
//        creditCard.setToken(cardRequest.getToken());
        order.setAmount(orderRequest.getAmount());
        order.setStripeChargeId(orderRequest.getStripeChargeId());
        order.setTicketEvolutionId(orderRequest.getTicketEvolutionId());

        order.setUser(customUserDetailsService.loadUserById(orderRequest.getUserId()));
        return order;
    }

    public void delete(Long id) {
        orderRepository.delete(getOne(id));
    }
}
