package com.example.userinfo.repository;

import com.example.userinfo.model.Payment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PaymentRepositoryTest {

    public static final long USER_ID = 1l;
    @Autowired
    protected PaymentRepository paymentRepository;

    @Test
    public void testFindPaymentsByUserId() {
        final List<Payment> payments = paymentRepository.findByUserId(USER_ID);
        assertThat(payments.size()).isEqualTo(15);
    }
}
