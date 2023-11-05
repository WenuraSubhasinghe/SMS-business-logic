package com.sms.businesslogic.service;

import com.sms.businesslogic.dto.PaymentReply;
import com.sms.businesslogic.dto.PaymentRequest;
import com.sms.businesslogic.entity.Order;
import com.sms.businesslogic.entity.Payment;
import com.sms.businesslogic.repository.OrderRepository;
import com.sms.businesslogic.repository.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Value("${stripe.apikey}")
    private String stripeKey;

    public Payment createPaymentIntent(PaymentRequest paymentRequest) {
        try {
            //  Setting API key
            Stripe.apiKey = stripeKey;

            // Checking if the order exist
            Order order =  orderRepository.findById(paymentRequest.getOrderId()).orElseThrow();

            Long paymentAmount = order.getTotalPrice().longValue();

            // Create a PaymentIntent
            PaymentIntent paymentIntent = PaymentIntent.create(
                    new PaymentIntentCreateParams.Builder()
                            .setCurrency(paymentRequest.getCurrency())
                            .setAmount(paymentAmount)
                            .addPaymentMethodType(paymentRequest.getPaymentMethodType())
                            .setConfirmationMethod(PaymentIntentCreateParams.ConfirmationMethod.MANUAL)
                            .build()
            );

            // Saving the PaymentIntent to the database
            Payment payment = new Payment();
            payment.setOrder(order);
            payment.setPaymentType(paymentRequest.getPaymentMethodType());
            payment.setTotalPayment(order.getTotalPrice());

            return paymentRepository.save(payment);
        } catch (StripeException e) {
            e.printStackTrace();
            return null;
        }
    }
}
