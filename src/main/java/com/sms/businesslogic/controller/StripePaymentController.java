package com.sms.businesslogic.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
public class StripePaymentController {

    @Value("${stripe.apikey}")
    String stripeKey;

    @GetMapping
    public String hello() {
        return "Hi there.. it is from payment." + stripeKey;
    }
}
