package com.sms.businesslogic.dto;

import com.sms.businesslogic.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductDTO {
    private String productName;
    private Integer quantity;
    private String description;
    private BigDecimal price;
    private Integer categoryId;
}
