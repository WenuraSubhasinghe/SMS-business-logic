package com.sms.businesslogic.controller;

import com.sms.businesslogic.entity.Delivery;
import com.sms.businesslogic.entity.Order;
import com.sms.businesslogic.service.DeliverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {
    @Autowired
    private DeliverService deliverService;

//    @Autowired OrderService orderService;

    @GetMapping
    public List<Delivery> getAllDeliveries() {
        return deliverService.getAllDeliveries();
    }

    @PutMapping("/status/{deliveryId}")
    public ResponseEntity<String> updateDeliveryStatus(@PathVariable Integer deliveryId, @RequestParam String newStatus) {
        Delivery updatedDelivery = deliverService.updateDeliveryStatus(deliveryId, newStatus);

        if (updatedDelivery != null) {
            return new ResponseEntity<>("Delivery status updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Delivery not found. Update failed", HttpStatus.NOT_FOUND);
        }
    }

//    @PostMapping("/updateDelivery")
//    public ResponseEntity<String> updateDelivery(@RequestParam Integer orderId) {
//        Order order = orderService.getOrderById(orderId);
//        if (order == null) {
//            return new ResponseEntity<>("Order not found" , HttpStatus.NOT_FOUND);
//        }
//        Delivery newDelivery = deliverService.updateDelivery(order);
//
//        return new ResponseEntity<>("Delivery updated successfully", HttpStatus.OK);
//    }

    @GetMapping("/track")
    public ResponseEntity<String> trackDeliveryStatus(@RequestParam Integer trackingNumber) {
        Delivery delivery = deliverService.getDeliveryByTrackingNumber(trackingNumber);

        if (delivery == null) {
            return new ResponseEntity<>("Delivery not found" ,HttpStatus.NOT_FOUND);
        }

        String status = delivery.getStatus();
        return new ResponseEntity<>("Delivery status : " + status, HttpStatus.OK);
    }
}
