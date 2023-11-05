package com.sms.businesslogic.service;


import com.sms.businesslogic.dto.OrderDTO;
import com.sms.businesslogic.entity.Order;
import com.sms.businesslogic.entity.User;
import com.sms.businesslogic.repository.OrderRepository;
import com.sms.businesslogic.repository.ProductRepository;
import com.sms.businesslogic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    private OrderDTO convertToDTO(Order order){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setOrderStatus(order.getOrderStatus());
        orderDTO.setOrderStatus(order.getOrderStatus());
        return orderDTO;
    }
}
