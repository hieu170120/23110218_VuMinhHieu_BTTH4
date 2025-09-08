package service.inft;

import model.Category;
import java.util.List;

public interface ICategoryService {
	
    // Phương thức thêm mới một Category
    void insert(Category category);

    // Phương thức cập nhật thông tin một Category
    void update(Category category);

    // Phương thức xóa một Category
    void delete(int categoryId);

    // Phương thức tìm Category theo ID
    Category findById(int categoryId);

    // Phương thức lấy tất cả các Category
    List<Category> findAll();

}
