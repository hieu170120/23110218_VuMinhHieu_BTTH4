package controller;



import model.Category;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.impl.*;
import service.CategoryService;
import service.inft.ICategoryService;
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(urlPatterns = {"/admin/category", "/admin/category/add-category", "/admin/category/edit-category", "/admin/category/delete-category"})
public class CategoryController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ICategoryService cateService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        String url = req.getRequestURI();
        
        if (url.contains("categories")) {
            // Lấy danh sách các category
            List<Category> listCategory = cateService.findAll();
            req.setAttribute("listcate", listCategory);
            req.getRequestDispatcher("/views/admin/list-category.jsp").forward(req, resp);
        } else if (url.contains("/admin/category/edit-category")) {
            // Hiển thị form chỉnh sửa category
            int categoryId = Integer.parseInt(req.getParameter("id"));
            Category category = cateService.findById(categoryId);
            req.setAttribute("category", category);
            req.getRequestDispatcher("/views/admin/edit-category.jsp").forward(req, resp);
        } else if (url.contains("/admin/category/delete-category")) {
            // Xóa category
            int categoryId = Integer.parseInt(req.getParameter("id"));
            cateService.delete(categoryId);
            resp.sendRedirect("/admin/category");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String url = req.getRequestURI();
        
        if (url.contains("/admin/category/add-category")) {
            // Thêm mới category
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            Category category = new Category();
            category.setName(name);
            category.setDescription(description);
            cateService.insert(category);
            resp.sendRedirect("/admin/category");
        } else if (url.contains("/admin/category/edit-category")) {
            // Cập nhật category
            int categoryId = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            Category category = cateService.findById(categoryId);
            category.setName(name);
            category.setDescription(description);
            cateService.update(category);
            resp.sendRedirect("/admin/category");
        }
    }
}