package com.example.userinfo.repository;

import com.example.userinfo.model.Payment;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentRepository extends CustomJpaRepository {

    public List<Payment> findByUserId(final Long userId) {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction transaction = entityManager.getTransaction();
        List<Payment> payments = new ArrayList<>();
        try {
            transaction.begin();
            payments = entityManager.createQuery("SELECT p FROM Payment p where p.user.id = :userId")
                    .setParameter("userId", userId)
                    .getResultList();
            entityManager.getTransaction().commit();
        } catch (RuntimeException e) {
            tryRollback(transaction);
            throw e;
        } finally {
            entityManager.close();
            return payments;
        }
    }

}
