package com.sms.businesslogic.repository;

import com.sms.businesslogic.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
    Delivery findByTrackingNo(String trackingNumber);
}
