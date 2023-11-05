package com.sms.businesslogic.controller;

import com.sms.businesslogic.dto.PaymentResponse;
import com.sms.businesslogic.dto.PaymentRequest;
import com.sms.businesslogic.service.PaymentService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public PaymentResponse createPaymentIntent(@RequestBody PaymentRequest paymentRequest) throws StripeException {
        return paymentService.createPaymentIntent(paymentRequest);
    }

    @GetMapping("/{paymentId}")
    public PaymentResponse getPaymentDetails(@PathVariable(name = "paymentId") Integer paymentId) {
        return paymentService.getPaymentDetails(paymentId);
    }

    @GetMapping("/user/{userId}")
    public List<PaymentResponse> getAllPaymentsByUserId(@PathVariable(name = "userId") Integer userId) {
        return paymentService.getAllPaymentsByUserId(userId);
    }
}
