package com.example.userinfo.controller;

import com.example.userinfo.model.Payment;
import com.example.userinfo.model.User;
import com.example.userinfo.model.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
@RequestMapping("/api")
public class UserPaymentsController {
    public static final String HTTP_LOCALHOST_8080_API = "http://localhost:8080/api";
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/userinfo/{id}")
    UserInfo getUserInfo(@PathVariable(value = "id") final Long userId) {
        final User user = this.restTemplate.getForObject(HTTP_LOCALHOST_8080_API + "/users/" + userId, User.class);
        final String paymentUrl = HTTP_LOCALHOST_8080_API + "/payments/" + userId;
        final ResponseEntity<Payment[]> payments = this.restTemplate.getForEntity(paymentUrl, Payment[].class);
        final UserInfo userInfo = new UserInfo();
        userInfo.setUser(user);
        userInfo.setPayments(Arrays.asList(payments.getBody()));
        return userInfo;
    }
}
