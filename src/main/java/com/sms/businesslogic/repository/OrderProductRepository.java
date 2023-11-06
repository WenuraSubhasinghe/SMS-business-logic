package com.sms.businesslogic.repository;


import com.sms.businesslogic.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct,Integer> {
    //void deleteByOrder_Id(Integer orderId);
    List<OrderProduct> findAllByOrder_OrderId(Integer orderId);
}
