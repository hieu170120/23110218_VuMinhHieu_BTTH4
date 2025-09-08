package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Category;
import repository.CategoryRepository;

@WebServlet(urlPatterns = {"/admin/category/add"})
@MultipartConfig(
    fileSizeThreshold = 2 * 1024 * 1024,
    maxFileSize       = 10 * 1024 * 1024,
    maxRequestSize    = 20 * 1024 * 1024
)
public class CategoryAddController extends HttpServlet {

    private CategoryRepository categoryRepository;
    private EntityManager entityManager;

    @Override
    public void init() throws ServletException {
        entityManager = Persistence.createEntityManagerFactory("dataSource").createEntityManager();
        categoryRepository = new CategoryRepository(entityManager);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/admin/category/add-category.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String name = req.getParameter("name");
        if (name == null || name.trim().isEmpty()) {
            req.setAttribute("alert", "Tên danh mục không được để trống!");
            req.getRequestDispatcher("/admin/category/add-category.jsp").forward(req, resp);
            return;
        }

        Part iconPart = req.getPart("icon");
        String iconPath = null;

        if (iconPart != null && iconPart.getSize() > 0) {
            if (!iconPart.getContentType().startsWith("image/")) {
                req.setAttribute("alert", "File icon phải là ảnh!");
                req.getRequestDispatcher("/admin/category/add-category.jsp").forward(req, resp);
                return;
            }
            if (iconPart.getSize() > 5 * 1024 * 1024) {
                req.setAttribute("alert", "File ảnh quá lớn! Chỉ cho phép file dưới 5MB.");
                req.getRequestDispatcher("/admin/category/add-category.jsp").forward(req, resp);
                return;
            }
            String uploadDir = getServletContext().getRealPath("/uploads/category");
            Files.createDirectories(Paths.get(uploadDir));
            String submittedFileName = iconPart.getSubmittedFileName();
            if (submittedFileName == null || submittedFileName.isEmpty()) {
                req.setAttribute("alert", "Tên file không hợp lệ!");
                req.getRequestDispatcher("/admin/category/add-category.jsp").forward(req, resp);
                return;
            }
            String ext = "";
            int dot = submittedFileName.lastIndexOf('.');
            if (dot >= 0) {
                ext = submittedFileName.substring(dot).toLowerCase();
                if (!ext.matches("\\.(jpg|jpeg|png|gif|bmp|webp)")) {
                    req.setAttribute("alert", "Chỉ hỗ trợ file ảnh: JPG, PNG, GIF, BMP, WebP!");
                    req.getRequestDispatcher("/admin/category/add-category.jsp").forward(req, resp);
                    return;
                }
            } else {
                req.setAttribute("alert", "File phải có extension!");
                req.getRequestDispatcher("/admin/category/add-category.jsp").forward(req, resp);
                return;
            }
            String savedFileName = "cate_" + System.currentTimeMillis() + "_" + Math.abs(submittedFileName.hashCode()) + ext;
            String filePath = uploadDir + File.separator + savedFileName;
            iconPart.write(filePath);
            iconPath = "uploads/category/" + savedFileName;
        } else {
            req.setAttribute("alert", "Vui lòng chọn ảnh đại diện!");
            req.getRequestDispatcher("/admin/category/add-category.jsp").forward(req, resp);
            return;
        }

        Category category = new Category();
        category.setName(name.trim());
        category.setIcon(iconPath);

        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            categoryRepository.insert(category);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            req.setAttribute("alert", "Có lỗi xảy ra: " + e.getMessage());
            req.getRequestDispatcher("/admin/category/add-category.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/admin/category/list");
    }

    @Override
    public void destroy() {
        if (entityManager != null) {
            entityManager.close();
        }
    }
}