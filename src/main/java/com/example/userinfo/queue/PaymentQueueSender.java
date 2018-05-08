package com.example.userinfo.queue;

import com.example.userinfo.model.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;

@Component("paymentQueueSender")
public class PaymentQueueSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentQueueSender.class);

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessage(final Destination destination, Payment payment) {
        LOGGER.info("Sending payment {} to DB", payment);
        jmsTemplate.convertAndSend(destination, payment);
    }
}
