package com.sms.businesslogic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "delivery")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer deliveryId;
    private Integer trackingNo;
    private Date deliveryDate;
    private String shippingAddress;
    private String deliveryStatus;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "orderId")
    private Order order;
}
