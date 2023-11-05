package com.sms.businesslogic.service;

import com.sms.businesslogic.dto.PaymentResponse;
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

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Value("${stripe.apikey}")
    private String stripeKey;

    public PaymentResponse createPaymentIntent(PaymentRequest paymentRequest) {
        try {
            Stripe.apiKey = stripeKey;

            Order order =  orderRepository.findById(paymentRequest.getOrderId()).orElseThrow();

            long paymentAmount = order.getTotalPrice().longValue();

            PaymentIntent paymentIntent = PaymentIntent.create(
                    new PaymentIntentCreateParams.Builder()
                            .setCurrency(paymentRequest.getCurrency())
                            .setAmount(paymentAmount*100)
                            .addPaymentMethodType(paymentRequest.getPaymentMethodType())
                            .setConfirmationMethod(PaymentIntentCreateParams.ConfirmationMethod.MANUAL)
                            .build()
            );

            Payment payment = new Payment();
            payment.setOrder(order);
            payment.setPaymentType(paymentRequest.getPaymentMethodType());
            payment.setTotalPayment(order.getTotalPrice());
            payment.setLocalDateTime(LocalDateTime.now());

            payment = paymentRepository.save(payment);

            PaymentResponse paymentResponse = new PaymentResponse();
            paymentResponse.setPaymentId(payment.getPaymentId());
            paymentResponse.setOrderId(payment.getOrder().getOrderId());
            paymentResponse.setUsername(payment.getOrder().getUser().getUsername());
            paymentResponse.setTotalPayment(payment.getTotalPayment());
            paymentResponse.setPaymentType(payment.getPaymentType());
            paymentResponse.setLocalDateTime(payment.getLocalDateTime());

            return paymentResponse;
        } catch (StripeException e) {
            e.printStackTrace();
            return null;
        }
    }
}
