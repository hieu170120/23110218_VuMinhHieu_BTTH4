package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Category;


public class CategoryRepository {

    private EntityManager entityManager;

    // Constructor to inject EntityManager
    public CategoryRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Insert a new category into the database
    public void insert(Category category) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(category); // Persist category entity
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback(); // Rollback in case of an error
            }
            throw e;
        }
    }

    // Additional methods for category CRUD operations can be added here
}
