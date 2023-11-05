package com.sms.businesslogic.dto;

import com.sms.businesslogic.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Integer orderId;
    private Integer totalQuantity;
    private BigDecimal totalPrice;
    private Date orderDate;
    private String orderStatus;
}
