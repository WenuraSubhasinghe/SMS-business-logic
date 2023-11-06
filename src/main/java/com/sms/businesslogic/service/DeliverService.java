package com.sms.businesslogic.service;

import com.sms.businesslogic.entity.Delivery;
import com.sms.businesslogic.entity.Order;
import com.sms.businesslogic.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class DeliverService {
    @Autowired
    private DeliveryRepository deliveryRepository;

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

    public Delivery getDeliveryByTrackingNumber(Integer trackingNumber) {
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

    public Delivery updateDelivery(Order order, String shippingAddress) {
        Delivery newDelivery = new Delivery();
        String trackingNumber = generateTrackingNumber(order.getOrderId());

        try {
            int trackingNumberAsInt = Integer.parseInt(trackingNumber);
            newDelivery.setTrackingNo(trackingNumberAsInt);
        } catch (NumberFormatException e) {
            // Handle the case where the tracking number is not a valid integer
        }
        newDelivery.setDeliveryDate(calculateDeliveryDate());
        newDelivery.setDeliveryStatus("In Progress");
        newDelivery.setShippingAddress(shippingAddress);
        newDelivery.setOrder(order);

        return deliveryRepository.save(newDelivery);
    }

    public void deleteDelivery(Integer deliveryId) {
        deliveryRepository.deleteById(deliveryId);
    }
}
