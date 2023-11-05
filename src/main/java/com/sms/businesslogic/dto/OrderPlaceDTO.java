package com.sms.businesslogic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlaceDTO {
    private Integer orderId;
    private Integer totalQuantity;
    private BigDecimal totalPrice;
    private String orderDate;
    private List<OrderProductDTO> orderProducts;

}
