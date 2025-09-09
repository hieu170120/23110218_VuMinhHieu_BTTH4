package dao.inft;

import java.util.List;
import model.Category;

public interface ICategoryDao {
    
    // Thêm mới một Category
    void insert(Category category);

    // Cập nhật thông tin Category
    void update(Category category);

    // Xóa Category theo ID
    void delete(int categoryId);

    // Tìm Category theo ID
    Category findById(int categoryId);

    // Lấy tất cả Category
    List<Category> findAll();

    // Tìm kiếm Category theo từ khóa
    List<Category> search(String keyword);

    // Lấy Category theo userId (dùng cho phân quyền)
    List<Category> findByUserId(int userId);
}
