package com.example.userinfo;

import com.example.userinfo.controller.PaymentController;
import com.example.userinfo.model.Payment;
import com.example.userinfo.model.PaymentType;
import com.example.userinfo.model.User;
import com.example.userinfo.service.PaymentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    public static final long USER_ID = 1l;
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

        mvc.perform(get("/api/payments/" + USER_ID)
                .contentType(MediaType.APPLICATION_XML))
                .andExpect(status().isOk());
//                .andExpect(xpath("$", hasSize(1)))
//                .andExpect(xpath("$[0].amount", is(payment.getAmount())));
    }

    private Payment mockPayment() {
        Payment payment = new Payment();
        payment.setUser(mockUser());
        payment.setAmount(1.0);
        payment.setPaymentType(PaymentType.MASTER_CARD);
        return payment;
    }

    private User mockUser() {
        final User mockUser = new User();
        mockUser.setId(1l);
        mockUser.setContent("My user");
        mockUser.setName("User1");
        return mockUser;
    }


}
