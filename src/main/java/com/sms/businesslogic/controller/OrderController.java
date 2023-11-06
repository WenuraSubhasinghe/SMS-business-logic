package com.sms.businesslogic.controller;

import com.sms.businesslogic.dto.OrderDTO;
import com.sms.businesslogic.dto.OrderPlaceDTO;
import com.sms.businesslogic.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-controller")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orders")
    public List<OrderDTO> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("/orders/{userID}")
    public List<OrderDTO> getAllOrdersByUserName(@PathVariable Integer userID){
        return orderService.getAllOrdersByUserName(userID);
    }


    @PostMapping("/placeOrder/{username}")
    public ResponseEntity<?> createOrder(@RequestBody OrderPlaceDTO orderPlaceDTO,@PathVariable String username){
        try {
            orderService.createOrder(orderPlaceDTO,username);
            return ResponseEntity.ok("Order placed sucessfully");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating the order: "+e.getMessage());
        }
    }

    @DeleteMapping("/deleteOrder/{orderID}")
    public String deleteOrder(@PathVariable Integer orderID){
        return orderService.deleteOrder(orderID);
    }
}
