package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

import service.impl.CategoryServiceImpl;

import java.io.IOException;

@WebServlet(urlPatterns = { "/admin/category/delete" })
public class CategoryDeleteController extends HttpServlet {

    private CategoryServiceImpl cateService;  // Khởi tạo CategoryService thủ công

    @Override
    public void init() throws ServletException {
        cateService = new CategoryServiceImpl();  // Khởi tạo service thủ công
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        // Kiểm tra nếu id hợp lệ
        if (id != null && !id.isEmpty()) {
            try {
                int categoryId = Integer.parseInt(id);  // Convert id thành int
                cateService.delete(categoryId);  // Xóa danh mục theo ID
                resp.sendRedirect(req.getContextPath() + "/admin/category/list");  // Redirect đến danh sách danh mục
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid category ID.");  // Xử lý lỗi nếu ID không hợp lệ
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Category ID is missing.");  // Xử lý lỗi nếu thiếu ID
        }
    }
}
