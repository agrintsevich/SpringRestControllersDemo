package com.example.userinfo.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;
import org.springframework.util.ErrorHandler;

import javax.jms.MessageListener;

@Configuration
@EnableJms
public class JmsConfiguration implements JmsListenerConfigurer {

    @Autowired
    private ApplicationContext appContext;


    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        final ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("vm://localhost");
        activeMQConnectionFactory.setTrustAllPackages(true);
        return activeMQConnectionFactory;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory
                = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(activeMQConnectionFactory());
        ErrorHandler jmsErrorHandler = lookupBeanByType(JmsErrorHandler.class);
        factory.setErrorHandler(jmsErrorHandler);
        return factory;
    }

    @Override
    public void configureJmsListeners(JmsListenerEndpointRegistrar registrar) {
        SimpleJmsListenerEndpoint endpoint = new SimpleJmsListenerEndpoint();
        endpoint.setId("paymentEndpoint");
        endpoint.setDestination("payment");
        MessageListener jmsMessageListener = lookupBeanByType(PaymentListener.class);
        endpoint.setMessageListener(jmsMessageListener);
        registrar.registerEndpoint(endpoint);
    }

    private <T> T lookupBeanByType(Class<T> clazz) {
        return (T) appContext.getAutowireCapableBeanFactory().autowire(clazz, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
    }


}
