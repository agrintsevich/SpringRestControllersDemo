package com.example.userinfo.queue;

import com.example.userinfo.model.Payment;
import com.example.userinfo.service.PaymentService;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@Component
public class PaymentListener implements MessageListener {

    @Autowired
    private PaymentService paymentService;

    @Override
    public void onMessage(Message message) {
        if (message instanceof ActiveMQObjectMessage) {
            try {
                final Payment payment = (Payment) ((ActiveMQObjectMessage) message).getObject();
                paymentService.addPayment(payment);
            } catch (JMSException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            throw new IllegalArgumentException("Message Error");
        }
    }
}
