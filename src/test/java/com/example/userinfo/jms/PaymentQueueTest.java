package com.example.userinfo.jms;

import com.example.userinfo.model.Payment;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.junit.EmbeddedActiveMQBroker;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;

import static com.example.userinfo.MockDataStore.mockPayment;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentQueueTest {

    public static final String DESTINATION_NAME = "payment";
    public static final String BROKER_URL = "vm://embedded-broker?create=false";
    @Rule
    public EmbeddedActiveMQBroker broker = new EmbeddedActiveMQBroker();

    protected ActiveMQConnectionFactory activeMQConnectionFactory;

    @Autowired
    protected JmsTemplate jmsTemplate;

    @Before
    public void before() {
        activeMQConnectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        activeMQConnectionFactory.setTrustAllPackages(true);
        jmsTemplate.setConnectionFactory(new SingleConnectionFactory(activeMQConnectionFactory));
        jmsTemplate.setMessageConverter(new SimpleMessageConverter());
    }

    @Test
    public void testSendPayment() {
        ActiveMQQueue destination = new ActiveMQQueue(DESTINATION_NAME);
        final Payment mockPayment = mockPayment();
        this.jmsTemplate.convertAndSend(destination, mockPayment);
        jmsTemplate.setReceiveTimeout(5_000);
        assertReceivedMessage(destination, mockPayment);
    }

    private void assertReceivedMessage(ActiveMQQueue destination, Payment mockPayment) {
        Object actualMessage = jmsTemplate.receiveAndConvert(destination);
        assertThat(actualMessage).isInstanceOf(Payment.class);
        final Payment actualPayment = (Payment) actualMessage;
        assertThat(actualPayment.getAmount()).isEqualTo(mockPayment.getAmount());
        assertThat(actualPayment.getPaymentType()).isEqualTo(mockPayment.getPaymentType());
        assertThat(actualPayment.getUser()).isNotNull();
        assertThat(actualPayment.getUser().getId()).isEqualTo(mockPayment.getUser().getId());
    }
}
