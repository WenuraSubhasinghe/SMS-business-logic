package com.sms.businesslogic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentResponse {
    private Integer paymentId;
    private String paymentType;
    private BigDecimal totalPayment;
    private LocalDateTime localDateTime;
    private Integer orderId;
    private String username;
}
