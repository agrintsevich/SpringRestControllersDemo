package com.example.userinfo.repository;

import com.example.userinfo.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public class UserRepository extends CustomJpaRepository {

    public Optional<User> findById(Long userId) {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction transaction = entityManager.getTransaction();
        User user = null;
        try {
            transaction.begin();
            user = (User) entityManager.createQuery("SELECT u FROM User u where u.id = :userId")
                    .setParameter("userId", userId)
                    .getSingleResult();
            entityManager.getTransaction().commit();
        } catch (RuntimeException e) {
            tryRollback(transaction);
            throw e;
        } finally {
            entityManager.close();
            return Optional.of(user);
        }
    }

    public List<User> findAll() {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction transaction = entityManager.getTransaction();
        List<User> users = new ArrayList<>();
        try {
            transaction.begin();
            users = entityManager.createQuery("SELECT u FROM User u")
                    .getResultList();
            entityManager.getTransaction().commit();
        } catch (RuntimeException e) {
            tryRollback(transaction);
            throw e;
        } finally {
            entityManager.close();
            return users;
        }
    }
}
