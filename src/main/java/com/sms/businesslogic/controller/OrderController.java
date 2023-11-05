package com.sms.businesslogic.controller;


import com.sms.businesslogic.dto.OrderDTO;
import com.sms.businesslogic.entity.Order;
import com.sms.businesslogic.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-controller")
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;

    @GetMapping("/orders")
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }









}
