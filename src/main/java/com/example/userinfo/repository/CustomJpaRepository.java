package com.example.userinfo.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

public class CustomJpaRepository<E> {
    @PersistenceUnit
    protected EntityManagerFactory entityManagerFactory;

    public E save(E entity) {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
        } catch (RuntimeException e) {
            tryRollback(transaction);
            throw e;
        } finally {
            entityManager.close();
            return entity;
        }
    }

    public void delete(E entity) {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.remove(entity);
            transaction.commit();
        } catch (RuntimeException e) {
            tryRollback(transaction);
            throw e;
        } finally {
            entityManager.close();
        }
    }

    protected void tryRollback(EntityTransaction transaction) {
        if (transaction != null && transaction.isActive())
            transaction.rollback();
    }
}
