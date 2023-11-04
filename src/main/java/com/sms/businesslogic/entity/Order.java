package com.sms.businesslogic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;
    private Integer totalQuantity;
    private BigDecimal totalPrice;
    private String shippingAddress;
    private Date orderDate;
    private String orderStatus;
    private String deliveryStatus;
}
