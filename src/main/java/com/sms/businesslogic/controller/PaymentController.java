package com.sms.businesslogic.controller;

import com.sms.businesslogic.dto.PaymentRequest;
import com.sms.businesslogic.entity.Payment;
import com.sms.businesslogic.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    @Value("${stripe.apikey}")
    String stripeKey;

    private final PaymentService paymentService;

    @PostMapping
    public Payment createPaymentIntent(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.createPaymentIntent(paymentRequest);
    }
}
