package service.impl;

import model.Category;
import service.inft.ICategoryService;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;

public class CategoryServiceImpl implements ICategoryService {

	@PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insert(Category category) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(category);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
    @Override
    public void update(Category category) {
        // Update logic if needed
    }

    @Override
    public void delete(int categoryId) {
        Category category = entityManager.find(Category.class, categoryId);
        if (category != null) {
            entityManager.remove(category);
        }
    }

    @Override
    public Category findById(int categoryId) {
        return entityManager.find(Category.class, categoryId);
    }

    @Override
    public List<Category> findAll() {
        return entityManager.createQuery("SELECT c FROM Category c", Category.class).getResultList();
    }
}
