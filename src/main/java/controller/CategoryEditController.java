package controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Category;
import service.impl.CategoryServiceImpl;

@WebServlet(urlPatterns = "/admin/category/edit")
@MultipartConfig(
    fileSizeThreshold = 2 * 1024 * 1024,
    maxFileSize       = 10 * 1024 * 1024,
    maxRequestSize    = 20 * 1024 * 1024
)
public class CategoryEditController extends HttpServlet {
    private final CategoryServiceImpl service = new CategoryServiceImpl();

    @Override 
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            // Kiểm tra xem tham số id có hợp lệ không
            String idStr = req.getParameter("id");
            if (idStr != null && !idStr.trim().isEmpty()) {
                int id = Integer.parseInt(idStr);
                Category category = service.getCategoryById(id);  
                if (category != null) {
                    req.setAttribute("category", category);
                    req.getRequestDispatcher("/views/admin/category/edit-category.jsp").forward(req, resp);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Category not found.");
                }
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid category ID.");
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid category ID.");
        }
    }

    @Override 
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        // Kiểm tra tham số id và xử lý lỗi nếu không hợp lệ
        String idStr = req.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            req.setAttribute("alert", "Invalid category ID.");
            req.getRequestDispatcher("/views/admin/category/edit-category.jsp").forward(req, resp);
            return;
        }

        int id = -1;
        try {
            id = Integer.parseInt(idStr);  // Chuyển đổi sang int sau khi kiểm tra
        } catch (NumberFormatException e) {
            req.setAttribute("alert", "Category ID must be a valid number.");
            req.getRequestDispatcher("/views/admin/category/edit-category.jsp").forward(req, resp);
            return;
        }

        String name = req.getParameter("name");
        String oldIcon = req.getParameter("oldIcon");

        // Validate input
        if (name == null || name.trim().isEmpty()) {
            req.setAttribute("alert", "Category name cannot be empty.");
            req.setAttribute("category", service.getCategoryById(id));
            req.getRequestDispatcher("/views/admin/category/edit-category.jsp").forward(req, resp);
            return;
        }

        String iconPath = oldIcon;
        Part iconPart = req.getPart("icon");

        if (iconPart != null && iconPart.getSize() > 0) {
            // Validate file type
            if (!iconPart.getContentType().startsWith("image/")) {
                req.setAttribute("alert", "File icon must be an image.");
                req.setAttribute("category", service.getCategoryById(id));
                req.getRequestDispatcher("/views/admin/category/edit-category.jsp").forward(req, resp);
                return;
            }

            // Validate file size
            if (iconPart.getSize() > 5 * 1024 * 1024) {
                req.setAttribute("alert", "File size is too large! Maximum size is 5MB.");
                req.setAttribute("category", service.getCategoryById(id));
                req.getRequestDispatcher("/views/admin/category/edit-category.jsp").forward(req, resp);
                return;
            }

            String uploadDir = getServletContext().getRealPath("/uploads/category");
            Files.createDirectories(Paths.get(uploadDir));

            String submitted = Paths.get(iconPart.getSubmittedFileName()).getFileName().toString();
            if (submitted == null || submitted.isEmpty()) {
                req.setAttribute("alert", "Invalid file name.");
                req.setAttribute("category", service.getCategoryById(id));
                req.getRequestDispatcher("/views/admin/category/edit-category.jsp").forward(req, resp);
                return;
            }

            String ext = "";
            int dot = submitted.lastIndexOf('.');
            if (dot >= 0) {
                ext = submitted.substring(dot).toLowerCase();
                if (!ext.matches("\\.(jpg|jpeg|png|gif|bmp|webp)")) {
                    req.setAttribute("alert", "Supported image formats are JPG, PNG, GIF, BMP, WebP.");
                    req.setAttribute("category", service.getCategoryById(id));
                    req.getRequestDispatcher("/views/admin/category/edit-category.jsp").forward(req, resp);
                    return;
                }
            } else {
                req.setAttribute("alert", "File must have an extension.");
                req.setAttribute("category", service.getCategoryById(id));
                req.getRequestDispatcher("/views/admin/category/edit-category.jsp").forward(req, resp);
                return;
            }

            // Generate a new filename to avoid duplicates
            String saved = "cate_" + System.currentTimeMillis() + "_" + Math.abs(submitted.hashCode()) + ext;
            iconPart.write(uploadDir + File.separator + saved);
            iconPath = "uploads/category/" + saved;

            // Delete old file if exists
            if (oldIcon != null && oldIcon.startsWith("uploads/")) {
                try { Files.deleteIfExists(Paths.get(getServletContext().getRealPath("/"), oldIcon)); } catch (Exception ignore) {}
            }
        }

        // Update the category with the new icon
        service.updateCategory(new Category(id, name.trim(), iconPath));
        resp.sendRedirect(req.getContextPath() + "/admin/category/list");
    }
}
