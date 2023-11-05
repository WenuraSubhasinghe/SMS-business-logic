package com.sms.businesslogic.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "t_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;
    private Integer totalQuantity;
    private BigDecimal totalPrice;
    private String orderDate;
    private String orderStatus;


    @ManyToOne
    @JoinColumn(name = "fk_user_id", referencedColumnName = "userId")
    private User user;



    @OneToOne
    @JoinColumn(name = "fk_payment_id",referencedColumnName ="paymentId")
    private Payment payment;


    @OneToOne
    @JoinColumn(name = "fk_delivery_id",referencedColumnName = "deliveryId")
    private Delivery delivery;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderProduct> orderedProducts;



}