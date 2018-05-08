package com.example.userinfo.jms;

import com.example.userinfo.model.Payment;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static com.example.userinfo.MockDataStore.mockPayment;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentQueueTest {

    @Autowired
    protected JmsTemplate jmsTemplate;

    @Test
    public void testSendPayment() {
        ActiveMQQueue destination = new ActiveMQQueue("payment");
        final Payment message = mockPayment();
        this.jmsTemplate.convertAndSend(destination, message);
        this.jmsTemplate.setReceiveTimeout(10_000);
        assertThat(this.jmsTemplate.receive(destination)).isNotNull();
    }
}
