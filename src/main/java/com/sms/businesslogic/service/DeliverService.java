package com.sms.businesslogic.service;

import com.sms.businesslogic.entity.Delivery;
import com.sms.businesslogic.entity.Order;
import com.sms.businesslogic.exception.OrderNotFoundException;
import com.sms.businesslogic.repository.DeliveryRepository;
import com.sms.businesslogic.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class DeliverService {
    private final DeliveryRepository deliveryRepository;

    private final OrderRepository orderRepository;

    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public Delivery updateDeliveryStatus(Integer deliveryId,String newStatus) {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElse(null);
        if (delivery != null) {
            delivery.setDeliveryStatus(newStatus);
            return deliveryRepository.save(delivery);
        }
        return null;
    }

    public Delivery getDeliveryByTrackingNumber(String trackingNumber) {
        return deliveryRepository.findByTrackingNo(trackingNumber);
    }

    public Date calculateDeliveryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);
        return calendar.getTime();
    }

    public String generateTrackingNumber(Integer orderId) {
        int randomNumber = new Random().nextInt(9000) + 1000;
        return "TRACK-" + orderId + "-" + randomNumber;
    }

    public Delivery updateDelivery(Integer orderId, String shippingAddress) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        Delivery newDelivery = new Delivery();
        String trackingNumber = generateTrackingNumber(order.getOrderId());

        try {
            newDelivery.setTrackingNo(trackingNumber);
        } catch (NumberFormatException e) {
            // Handle the case where the tracking number is not a valid integer
            newDelivery.setTrackingNo("TRACK-ERR-404");
        }
        newDelivery.setDeliveryDate(calculateDeliveryDate());
        newDelivery.setDeliveryStatus("In Progress");
        newDelivery.setShippingAddress(shippingAddress);
        newDelivery.setOrder(order);
        orderRepository.save(order);

        return deliveryRepository.save(newDelivery);
    }

    public void deleteDelivery(Integer deliveryId) {
        if (deliveryRepository.existsById(deliveryId)) {
            deliveryRepository.deleteById(deliveryId);
        } else {
            throw new IllegalArgumentException("Delivery not found");
        }
    }
}
