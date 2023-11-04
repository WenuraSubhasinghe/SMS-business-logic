package com.sms.businesslogic.Repository;

import com.sms.businesslogic.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer> {

}