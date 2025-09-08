package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import service.UserService;
import service.impl.UserServiceImpl;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");

        // Kiểm tra sự tồn tại của username, email, và phone trong cơ sở dữ liệu
        if (userService.checkExistUsername(username)) {
            req.setAttribute("alert", "Tài khoản đã tồn tại!");
            req.getRequestDispatcher("views/register.jsp").forward(req, resp);
            return;
        }

        if (userService.checkExistEmail(email)) {
            req.setAttribute("alert", "Email đã tồn tại!");
            req.getRequestDispatcher("views/register.jsp").forward(req, resp);
            return;
        }

        if (phone != null && !phone.isEmpty() && userService.checkExistPhone(phone)) {
            req.setAttribute("alert", "Số điện thoại đã tồn tại!");
            req.getRequestDispatcher("views/register.jsp").forward(req, resp);
            return;
        }

        User user = new User();
        user.setUserName(username);
        user.setPassWord(password);
        user.setFullName(fullname);
        user.setEmail(email);
        user.setPhone(phone);
        user.setRoleId(3); // Mặc định là người dùng bình thường

        // Ensure createdDate is set
        if (user.getCreatedDate() == null) {
            user.setCreatedDate(new java.util.Date()); // Set the current date if it's null
        }

        try {
            userService.insert(user);
            resp.sendRedirect(req.getContextPath() + "/login"); // Redirect to login page
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("alert", "Đã xảy ra lỗi trong quá trình đăng ký. Vui lòng thử lại!");
            req.getRequestDispatcher("views/register.jsp").forward(req, resp); // Return to registration page with error message
        }
    }
}
