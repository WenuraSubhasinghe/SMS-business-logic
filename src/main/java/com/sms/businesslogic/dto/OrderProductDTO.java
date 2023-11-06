package com.sms.businesslogic.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDTO {

    private Integer id;
    private String productName;
    private Integer prodQuantity;
    private Integer prodSubTotal;
    private List<OrderProductDTO> orderProducts;

}
