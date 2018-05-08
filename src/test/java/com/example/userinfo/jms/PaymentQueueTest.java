package com.example.userinfo.jms;

import com.example.userinfo.model.Payment;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;

import static com.example.userinfo.MockDataStore.mockPayment;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentQueueTest {

    @Autowired
    protected ActiveMQConnectionFactory activeMQConnectionFactory;

    @Autowired
    protected JmsTemplate jmsTemplate;

    @Before
    public void before() {
        jmsTemplate.setConnectionFactory(new SingleConnectionFactory(activeMQConnectionFactory));
        jmsTemplate.setMessageConverter(new SimpleMessageConverter());
    }

    @Test
    public void testSendPayment() {
        ActiveMQQueue destination = new ActiveMQQueue("payment");
        final Payment message = mockPayment();
        this.jmsTemplate.convertAndSend(destination, message);
    }
}
