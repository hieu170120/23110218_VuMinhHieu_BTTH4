package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Category;
import service.impl.CategoryServiceImpl;

@WebServlet(urlPatterns = { "/admin/category/list" })
public class CategoryListController extends HttpServlet {

    private CategoryServiceImpl cateService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Lấy danh sách tất cả các danh mục từ service
            List<Category> cateList = cateService.findAll();
            
            // Đưa danh sách vào request để forward đến JSP
            req.setAttribute("cateList", cateList);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/category/list-category.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            // Nếu có lỗi, thông báo lỗi và forward đến JSP
            req.setAttribute("error", "Có lỗi xảy ra khi tải danh sách danh mục: " + e.getMessage());
            RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/category/list-category.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
