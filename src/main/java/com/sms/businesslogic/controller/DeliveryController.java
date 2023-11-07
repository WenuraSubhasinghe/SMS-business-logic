package com.sms.businesslogic.controller;

import com.sms.businesslogic.entity.Delivery;
import com.sms.businesslogic.entity.Order;
import com.sms.businesslogic.service.DeliverService;
import com.sms.businesslogic.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/deliveries")
@AllArgsConstructor
public class DeliveryController {
    @Autowired
    private DeliverService deliverService;

    @Autowired OrderService orderService;

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

    @PostMapping("/createDelivery/{orderId}")
    public ResponseEntity<String> updateDelivery(@PathVariable Integer orderId, @RequestParam String shippingAddress) {
        try {
            Delivery newDelivery = deliverService.updateDelivery(orderId, shippingAddress);

            return new ResponseEntity<>("Delivery updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating the delivery: " + e.getMessage());
        }
    }

    @GetMapping("/track")
    public ResponseEntity<String> trackDeliveryStatus(@RequestParam Integer trackingNumber) {
        Delivery delivery = deliverService.getDeliveryByTrackingNumber(trackingNumber);

        if (delivery == null) {
            return new ResponseEntity<>("Delivery not found" ,HttpStatus.NOT_FOUND);
        }

        String status = delivery.getDeliveryStatus();
        return new ResponseEntity<>("Delivery status : " + status, HttpStatus.OK);
    }

    @DeleteMapping("/deleteDelivery/{deliveryId}")
    public ResponseEntity<?> deleteDelivery(@PathVariable Integer deliveryId) {
        try {
            deliverService.deleteDelivery(deliveryId);
            return ResponseEntity.ok("Delivery deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error deleting the delivery: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting the delivery: " + e.getMessage());
        }
    }
}
