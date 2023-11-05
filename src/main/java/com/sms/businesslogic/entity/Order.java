package com.sms.businesslogic.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Date orderDate;
    private String orderStatus;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

/*    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonIgnore
    private Payment payment;*/

    @OneToOne
    @JoinColumn(name = "payment_id",referencedColumnName ="paymentId")
    private Payment payment;

    /*@OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonIgnore
    private Delivery delivery;*/

    @OneToOne
    @JoinColumn(name = "delivery_id",referencedColumnName = "deliveryId")
    private Delivery delivery;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<OrderProduct> orderedProducts = new HashSet<>();


}