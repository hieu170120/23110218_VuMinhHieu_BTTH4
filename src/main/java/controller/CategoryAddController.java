package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Category;
import model.User;
import service.inft.ICategoryService;
import service.impl.CategoryServiceImpl;

@WebServlet(urlPatterns = {"/admin/category/add"})
@MultipartConfig(
    fileSizeThreshold = 2 * 1024 * 1024,  // 2MB
    maxFileSize       = 10 * 1024 * 1024, // 10MB
    maxRequestSize    = 20 * 1024 * 1024  // 20MB
)
public class CategoryAddController extends HttpServlet {

    private ICategoryService categoryService;

    @Override
    public void init() throws ServletException {
        categoryService = new CategoryServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/category/add-category.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String name = req.getParameter("name");
        if (name == null || name.trim().isEmpty()) {
            req.setAttribute("alert", "Tên danh mục không được để trống!");
            req.getRequestDispatcher("/views/admin/category/add-category.jsp").forward(req, resp);
            return;
        }

        // Xử lý upload icon
        Part iconPart = req.getPart("icon");
        String iconPath = handleFileUpload(iconPart, req, resp);
        if (iconPath == null) return;

        // Tạo Category mới
        Category category = new Category();
        category.setName(name.trim());
        category.setIcon(iconPath);

        // 👉 Gán user mặc định có id = 1 để tránh NULL
        User defaultUser = new User();
        defaultUser.setId(1);
        category.setUser(defaultUser);

        try {
            categoryService.insert(category);
            resp.sendRedirect(req.getContextPath() + "/admin/category/list");
        } catch (Exception e) {
            req.setAttribute("alert", "Có lỗi xảy ra: " + e.getMessage());
            req.getRequestDispatcher("/views/admin/category/add-category.jsp").forward(req, resp);
        }
    }

    private String handleFileUpload(Part iconPart, HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        if (iconPart == null || iconPart.getSize() <= 0) {
            req.setAttribute("alert", "Vui lòng chọn ảnh đại diện!");
            req.getRequestDispatcher("/views/admin/category/add-category.jsp").forward(req, resp);
            return null;
        }

        if (!iconPart.getContentType().startsWith("image/")) {
            req.setAttribute("alert", "File icon phải là ảnh!");
            req.getRequestDispatcher("/views/admin/category/add-category.jsp").forward(req, resp);
            return null;
        }

        if (iconPart.getSize() > 5 * 1024 * 1024) {
            req.setAttribute("alert", "File ảnh quá lớn! Chỉ cho phép file dưới 5MB.");
            req.getRequestDispatcher("/views/admin/category/add-category.jsp").forward(req, resp);
            return null;
        }

        String uploadDir = req.getServletContext().getRealPath("/uploads/category");
        Files.createDirectories(Paths.get(uploadDir));

        String submittedFileName = iconPart.getSubmittedFileName();
        if (submittedFileName == null || submittedFileName.isEmpty()) {
            req.setAttribute("alert", "Tên file không hợp lệ!");
            req.getRequestDispatcher("/views/admin/category/add-category.jsp").forward(req, resp);
            return null;
        }

        String ext = submittedFileName.substring(submittedFileName.lastIndexOf('.')).toLowerCase();
        if (!ext.matches("\\.(jpg|jpeg|png|gif|bmp|webp)")) {
            req.setAttribute("alert", "Chỉ hỗ trợ file ảnh: JPG, PNG, GIF, BMP, WebP!");
            req.getRequestDispatcher("/views/admin/category/add-category.jsp").forward(req, resp);
            return null;
        }

        String savedFileName = "cate_" + System.currentTimeMillis() + "_" +
                Math.abs(submittedFileName.hashCode()) + ext;
        String filePath = uploadDir + File.separator + savedFileName;
        iconPart.write(filePath);

        return "uploads/category/" + savedFileName;
    }
}
