package com.sms.businesslogic.controller;


import com.sms.businesslogic.dto.OrderPlaceDTO;
import com.sms.businesslogic.entity.Order;
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
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
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

/*    @PutMapping("updateOrder/{orderID}")
    public ResponseEntity<?> updateOrder(@RequestBody OrderPlaceDTO orderPlaceDTO,@PathVariable Integer orderID){
        try {
            orderService.updateOrder(orderPlaceDTO,orderID);
            return ResponseEntity.ok("Order updated sucessfully");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating the order: "+e.getMessage());
        }

    }*/



}
