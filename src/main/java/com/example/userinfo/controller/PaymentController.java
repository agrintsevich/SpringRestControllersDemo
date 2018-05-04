package com.example.userinfo.controller;

import com.example.userinfo.model.EntityList;
import com.example.userinfo.model.Payment;
import com.example.userinfo.queue.PaymentQueueSender;
import com.example.userinfo.service.PaymentService;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentQueueSender paymentQueueSender;

    @GetMapping(value = "/payments/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public EntityList<Payment> getUserPayments(@PathVariable("id") Long userId) {
        return new EntityList(paymentService.getPaymentsByUserId(userId));
    }

    @PostMapping(value = "/payments", consumes = MediaType.APPLICATION_XML_VALUE)
    public void createPayment(@Valid @RequestBody final Payment payment) {
        paymentQueueSender.sendMessage(new ActiveMQQueue("payment"), payment);
    }
}
