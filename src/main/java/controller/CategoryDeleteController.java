package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

import service.CategoryService;


import java.io.IOException;

@WebServlet(urlPatterns = { "/admin/category/delete" })
public class CategoryDeleteController extends HttpServlet {

//    @Autowired
//    private CategoryService cateService;  // Autowire CategoryService to use JPA methods
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String id = req.getParameter("id");
//
//        // Kiểm tra nếu id hợp lệ
//        if (id != null && !id.isEmpty()) {
//            try {
//                Long categoryId = Long.parseLong(id);  // Convert id to Long
//                cateService.delete(categoryId);  // Delete category using JPA
//                resp.sendRedirect(req.getContextPath() + "/admin/category/list");  // Redirect to category list
//            } catch (NumberFormatException e) {
//                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid category ID.");  // Handle invalid ID format
//            }
//        } else {
//            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Category ID is missing.");  // Handle missing ID
//        }
//    }
}
