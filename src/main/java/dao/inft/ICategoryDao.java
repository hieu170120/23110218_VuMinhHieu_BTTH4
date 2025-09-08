package dao.inft;
import java.util.List;

import model.Category;
public interface ICategoryDao {
	// Phương thức thêm mới một Category vào cơ sở dữ liệu
    void insert(Category category);

    // Phương thức cập nhật thông tin của một Category
    void update(Category category);

    // Phương thức xóa một Category từ cơ sở dữ liệu
    void delete(int categoryId);

    // Phương thức tìm Category theo ID
    Category findById(int categoryId);

    // Phương thức lấy tất cả các Category
    List<Category> findAll();
}
