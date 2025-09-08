package dao.impl;

import dao.UserDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import model.User;
import config.JPAConfig;

import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public User get(String username) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.userName = :username", User.class);
            query.setParameter("username", username);
            List<User> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }

    @Override
    public void insert(User user) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            if (user.getCreatedDate() == null) {
                user.setCreatedDate(new java.util.Date()); // Set current date if it's null
            }
            tx.begin();
            em.persist(user);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public boolean checkExistEmail(String email) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class);
            query.setParameter("email", email);
            return query.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean checkExistUsername(String username) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.userName = :username", Long.class);
            query.setParameter("username", username);
            return query.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean checkExistPhone(String phone) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.phone = :phone", Long.class);
            query.setParameter("phone", phone);
            return query.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }

    @Override
    public User getByEmail(String email) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);
            List<User> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }
}
