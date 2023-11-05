package com.sms.businesslogic.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDTO {

    private String productName;
    private Integer prodQuantity;
    private Integer prodSubTotal;

}
