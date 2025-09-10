package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/waiting")
public class WaitingController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session != null && session.getAttribute("account") != null) {
            // Nếu người dùng đã đăng nhập, tiếp tục với yêu cầu
            resp.sendRedirect(req.getContextPath() + "/waiting/home");  // Chuyển hướng người dùng tới trang đích
        } else {
            // Nếu người dùng chưa đăng nhập, chuyển đến trang đăng nhập
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        }
    }
}
