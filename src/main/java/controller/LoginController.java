package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import service.UserService;
import service.impl.UserServiceImpl;

@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(LoginController.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        try {
            logger.info("doPost: Processing login form submission");

            // Lấy dữ liệu từ form
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            boolean isRememberMe = "on".equals(req.getParameter("remember"));

            // Kiểm tra tài khoản hoặc mật khẩu trống
            if (username == null || username.isBlank() ||
                password == null || password.isBlank()) {
                logger.warning("doPost: Username or password is empty");
                req.setAttribute("alert", "Tài khoản hoặc mật khẩu không được rỗng");
                req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
                return;
            }

            // Kiểm tra thông tin đăng nhập
            UserService service = new UserServiceImpl();
            User user = service.login(username, password);
            if (user != null) {
                HttpSession session = req.getSession(true);
                session.setAttribute("account", user);  // Lưu thông tin người dùng vào session
                session.setAttribute("roleId", user.getRoleId());  // Lưu roleId vào session
                logger.info("doPost: User logged in successfully");

                // Lưu cookie nếu người dùng chọn "Ghi nhớ tôi"
                if (isRememberMe) {
                    Cookie cookie = new Cookie("rememberMe", username);
                    cookie.setMaxAge(30 * 60); // 30 phút
                    cookie.setHttpOnly(true);
                    cookie.setPath(req.getContextPath());
                    resp.addCookie(cookie);
                    logger.info("doPost: RememberMe cookie set for username: " + username);
                }

                // Chuyển hướng đến trang phù hợp dựa trên roleId
                int roleId = user.getRoleId();
                if (roleId == 1) {
                    resp.sendRedirect(req.getContextPath() + "/views/admin/category/list-category.jsp");  // Admin
                } else if (roleId == 2) {
                    resp.sendRedirect(req.getContextPath() + "/views/manager/home.jsp");  // Manager
                } else if (roleId == 3) {
                    resp.sendRedirect(req.getContextPath() + "/views/user/home.jsp");  // User
                } else {
                    // Nếu roleId không hợp lệ, chuyển về trang login
                    req.setAttribute("alert", "Quyền truy cập không hợp lệ!");
                    req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
                }
            } else {
                logger.warning("doPost: Invalid username or password");
                req.setAttribute("alert", "Tài khoản hoặc mật khẩu không đúng");
                req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error processing POST request: ", e);
            req.setAttribute("alert", "Có lỗi xảy ra khi xử lý yêu cầu.");
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        }
    }
}
