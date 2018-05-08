package com.example.userinfo.controller;

import com.example.userinfo.model.Payment;
import com.example.userinfo.service.PaymentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static com.example.userinfo.MockDataStore.mockPayment;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class PaymentControllerTest {

    public static final long USER_ID = 1l;

    public static final String PAYMENT_XML_CONTENT = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
            "<entityList>" +
            "<payment>" +
            "<user>" +
            "<id>1</id>" +
            "<name>User1</name>" +
            "<content>My user</content>" +
            "</user>" +
            "<amount>1.0</amount>" +
            "<paymentType>MASTER_CARD</paymentType>" +
            "</payment>" +
            "</entityList>";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PaymentService paymentService;

    @Test
    public void givenPayments_whenGetPayments_thenReturnXmlArray()
            throws Exception {
        Payment payment = mockPayment();
        List<Payment> allPayments = Arrays.asList(payment);
        given(paymentService.getPaymentsByUserId(USER_ID)).willReturn(allPayments);

        mvc.perform(get("/api/payments/" + USER_ID).contentType(MediaType.APPLICATION_XML_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_XML_VALUE))
                .andExpect(content().xml(PAYMENT_XML_CONTENT));
    }




}
