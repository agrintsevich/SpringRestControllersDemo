package com.example.userinfo;

import com.example.userinfo.model.Payment;
import com.example.userinfo.model.PaymentType;
import com.example.userinfo.model.User;

public class MockDataStore {

    public static Payment mockPayment() {
        Payment payment = new Payment();
        payment.setUser(mockUser());
        payment.setAmount(1.0);
        payment.setPaymentType(PaymentType.MASTER_CARD);
        return payment;
    }

    public static User mockUser() {
        final User mockUser = new User();
        mockUser.setId(1l);
        mockUser.setContent("My user");
        mockUser.setName("User1");
        return mockUser;
    }
}
