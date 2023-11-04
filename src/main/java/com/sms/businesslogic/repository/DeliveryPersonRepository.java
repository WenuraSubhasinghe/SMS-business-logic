package com.sms.businesslogic.repository;

import com.sms.businesslogic.entity.DeliveryPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryPersonRepository extends JpaRepository <DeliveryPerson, Long> {
}
