package com.example.userinfo.controller;

import com.example.userinfo.model.Payment;
import com.example.userinfo.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    PaymentRepository paymentRepository;

    @GetMapping(value = "/payments")
    @ResponseBody
    public List<Payment> getAllPayments() {
        return this.paymentRepository.findAll();
    }

    @GetMapping(value = "/payments/{id}")
    @ResponseBody
    public List<Payment> getUserPayments(@PathVariable("id") Long userId) {
        return this.paymentRepository.findByUserId(userId);
    }

    @PostMapping(value = "/payments", consumes = MediaType.APPLICATION_XML_VALUE)
    public Payment createPayment(@Valid @RequestBody final Payment payment) {
        return this.paymentRepository.save(payment);
    }
}
