package dao.impl;

import java.util.List;

import config.JPAConfig;
import dao.inft.ICategoryDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Category;

public class CategoryDaoImpl implements ICategoryDao {

    @Override
    public void insert(Category category) {
        EntityManager enma = JPAConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();

        try {
            trans.begin();
            enma.persist(category); // Insert vào bảng
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            enma.close();
        }
    }

    @Override
    public void update(Category category) {
        EntityManager enma = JPAConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();

        try {
            trans.begin();
            enma.merge(category); // Cập nhật thông tin category
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            enma.close();
        }
    }

    @Override
    public void delete(int categoryId) {
        EntityManager enma = JPAConfig.getEntityManager();
        EntityTransaction trans = enma.getTransaction();

        try {
            trans.begin();
            Category category = enma.find(Category.class, categoryId); // Tìm Category theo ID
            if (category != null) {
                enma.remove(category); // Xóa Category
            }
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            enma.close();
        }
    }

    @Override
    public Category findById(int categoryId) {
        EntityManager enma = JPAConfig.getEntityManager();
        try {
            return enma.find(Category.class, categoryId); // Tìm Category theo ID
        } finally {
            enma.close();
        }
    }

    @Override
    public List<Category> findAll() {
        EntityManager enma = JPAConfig.getEntityManager();
        try {
            return enma.createQuery("SELECT c FROM Category c", Category.class).getResultList(); // Lấy tất cả Category
        } finally {
            enma.close();
        }
    }
}
