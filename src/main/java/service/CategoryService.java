package service;

import model.Category;
import java.util.List;

public interface CategoryService {

    void addCategory(Category category);  // Thêm danh mục

    void updateCategory(Category category);  // Cập nhật danh mục

    void deleteCategory(int categoryId);  // Xóa danh mục

    Category getCategoryById(int categoryId);  // Lấy danh mục theo ID

    List<Category> getAllCategories();  // Lấy tất cả danh mục
}
