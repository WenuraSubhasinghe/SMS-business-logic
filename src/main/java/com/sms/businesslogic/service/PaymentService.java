package com.sms.businesslogic.service;

import com.sms.businesslogic.dto.PaymentResponse;
import com.sms.businesslogic.dto.PaymentRequest;
import com.sms.businesslogic.entity.Order;
import com.sms.businesslogic.entity.Payment;
import com.sms.businesslogic.entity.User;
import com.sms.businesslogic.exception.OrderNotFoundException;
import com.sms.businesslogic.exception.PaymentNotFoundException;
import com.sms.businesslogic.exception.UserNotFoundException;
import com.sms.businesslogic.repository.OrderRepository;
import com.sms.businesslogic.repository.PaymentRepository;
import com.sms.businesslogic.repository.UserRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Value("${stripe.apikey}")
    private String stripeKey;

    public PaymentResponse createPaymentIntent(PaymentRequest paymentRequest) throws StripeException {
        Stripe.apiKey = stripeKey;

        Order order =  orderRepository.findById(paymentRequest.getOrderId()).orElseThrow(
                () -> new OrderNotFoundException("Order not found for the orderId : " + paymentRequest.getOrderId())
        );

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
    }

    public PaymentResponse getPaymentDetails(Integer paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(
                () -> new PaymentNotFoundException("Payment not found for the paymentId : " + paymentId)
        );
        return mapToResponse(payment);
    }

    public List<PaymentResponse> getAllPaymentsByUserId(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found for the userId : " + userId)
        );

        List<Payment> payments = paymentRepository.findByOrderUserUserId(userId);
        List<PaymentResponse> paymentResponses = new ArrayList<>();

        for (Payment payment: payments) {
            PaymentResponse paymentResponse = mapToResponse(payment);
            paymentResponses.add(paymentResponse);
        }

        return paymentResponses;
    }

    public PaymentResponse mapToResponse(Payment payment) {
        PaymentResponse paymentResponse = new PaymentResponse();

        paymentResponse.setPaymentId(payment.getPaymentId());
        paymentResponse.setUsername(payment.getOrder().getUser().getUsername());
        paymentResponse.setTotalPayment(payment.getTotalPayment());
        paymentResponse.setOrderId(payment.getOrder().getOrderId());
        paymentResponse.setPaymentType(payment.getPaymentType());
        paymentResponse.setLocalDateTime(payment.getLocalDateTime());

        return paymentResponse;
    }

    public String deletePaymentByPaymentId(Integer paymentId) {
        Payment paymentToDelete = paymentRepository.findById(paymentId).orElseThrow(
                () -> new PaymentNotFoundException("Payment not found for the Id : " + paymentId)
        );

        paymentRepository.delete(paymentToDelete);

        return "Payment with the id " + paymentId + " was deleted successfully";
    }
}
