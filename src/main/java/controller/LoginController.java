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

    // Tạo một đối tượng Logger
    private static final Logger logger = Logger.getLogger(LoginController.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        try {
            logger.info("doGet: Processing login page request");

            HttpSession session = req.getSession(false);
            if (session != null && session.getAttribute("account") != null) {
                logger.info("doGet: User already logged in, redirecting to /waiting");
                resp.sendRedirect(req.getContextPath() + "/waiting");
                return;
            }

            // Kiểm tra cookie "remember me"
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if ("rememberMe".equals(c.getName())) {
                        session = req.getSession(true);
                        session.setAttribute("username", c.getValue());
                        logger.info("doGet: RememberMe cookie found, redirecting to /waiting");
                        resp.sendRedirect(req.getContextPath() + "/waiting");
                        return;
                    }
                }
            }

            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
            logger.info("doGet: Forwarding to login.jsp");

        } catch (Exception e) {
            // Ghi lỗi vào log
            logger.log(Level.SEVERE, "Lỗi khi xử lý yêu cầu GET: ", e);
            // Đảm bảo người dùng được chuyển hướng về trang đăng nhập nếu có lỗi
            req.setAttribute("alert", "Có lỗi xảy ra khi xử lý yêu cầu.");
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        }
    }

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
                session.setAttribute("account", user);
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
                resp.sendRedirect(req.getContextPath() + "/waiting");
            } else {
                logger.warning("doPost: Invalid username or password");
                req.setAttribute("alert", "Tài khoản hoặc mật khẩu không đúng");
                req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            // Ghi lỗi vào log
            logger.log(Level.SEVERE, "Lỗi khi xử lý yêu cầu POST: ", e);
            // Thông báo lỗi cho người dùng và chuyển lại về trang đăng nhập
            req.setAttribute("alert", "Có lỗi xảy ra khi xử lý yêu cầu.");
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        }
    }
}
