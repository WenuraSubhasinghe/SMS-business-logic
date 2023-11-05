package com.sms.businesslogic.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Integer orderId;
    private Integer totalQuantity;
    private BigDecimal totalPrice;
    private String orderDate;
    private String orderStatus;
}
