package com.example.userinfo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(allowGetters = true)
public class UserInfo {
    private User user;
    private List<Payment> payments;

    public User getUser() {
        return this.user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public List<Payment> getPayments() {
        return this.payments;
    }

    public void setPayments(final List<Payment> payments) {
        this.payments = payments;
    }
}
