package com.example.userinfo.repository;

import com.example.userinfo.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends  JpaRepository<Payment, Long> {

     List<Payment> findByUserId(final Long userId);
}
