<<<<<<< HEAD:src/main/java/com/sms/businesslogic/Repository/OrderRepository.java
package com.sms.businesslogic.Repository;

import com.sms.businesslogic.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer> {

}
=======
package com.sms.businesslogic.repository;

import com.sms.businesslogic.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
>>>>>>> main:src/main/java/com/sms/businesslogic/repository/OrderRepository.java
