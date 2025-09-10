package dao.impl;

import java.util.List;

import config.JPAConfig;
import dao.CategoryDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import model.Category;

public class CategoryDaoImpl implements CategoryDao {

    @Override
    public void insert(Category category) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(category);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Category category) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(category);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(int id) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            Category category = em.find(Category.class, id);
            if (category != null) {
                em.remove(category);
            }
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Category findById(int id) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            return em.find(Category.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Category> findAll() {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c", Category.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi truy vấn danh sách danh mục: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public List<Category> search(String keyword) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<Category> query = em.createQuery(
                "SELECT c FROM Category c WHERE c.name LIKE :kw", Category.class);
            query.setParameter("kw", "%" + keyword + "%");
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi tìm kiếm danh mục: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public List<Category> findByUserId(int userId) {
        EntityManager em = JPAConfig.getEntityManager();
        try {
            TypedQuery<Category> query = em.createQuery(
                "SELECT c FROM Category c WHERE c.user.id = :uid", Category.class);
            query.setParameter("uid", userId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi tìm danh mục theo User ID: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}
