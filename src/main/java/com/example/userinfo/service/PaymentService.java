package com.example.userinfo.service;

import com.example.userinfo.model.Payment;
import com.example.userinfo.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment addPayment(final Payment payment) {
        return (Payment) paymentRepository.save(payment);
    }

    public List<Payment> getPaymentsByUserId(Long userId) {
        return this.paymentRepository.findByUserId(userId);
    }
}
