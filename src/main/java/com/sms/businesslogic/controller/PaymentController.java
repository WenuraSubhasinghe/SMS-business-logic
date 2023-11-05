package com.sms.businesslogic.controller;

import com.sms.businesslogic.dto.PaymentResponse;
import com.sms.businesslogic.dto.PaymentRequest;
import com.sms.businesslogic.service.PaymentService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public PaymentResponse createPaymentIntent(@RequestBody PaymentRequest paymentRequest) throws StripeException {
        return paymentService.createPaymentIntent(paymentRequest);
    }
}
