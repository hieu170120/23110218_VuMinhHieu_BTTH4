package dao;

import java.util.List;
import model.Category;

public interface CategoryDao {
    void insert(Category category);
    void update(Category category);
    void delete(int id);
    Category findById(int id);
    List<Category> findAll();
    List<Category> search(String keyword);
    List<Category> findByUserId(int userId);
}
