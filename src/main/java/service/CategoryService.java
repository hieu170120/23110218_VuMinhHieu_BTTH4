package service;

import dao.impl.CategoryDaoImpl;
import model.Category;
import java.util.List;

public interface CategoryService {

	void addCategory(Category category);

    void updateCategory(Category category);

    void deleteCategory(int categoryId);

    Category getCategoryById(int categoryId);

    List<Category> getAllCategories();
}
