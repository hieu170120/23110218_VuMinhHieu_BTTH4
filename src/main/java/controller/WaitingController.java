package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

@WebServlet(urlPatterns = "/waiting")
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
			User u = (User) session.getAttribute("account");
			req.setAttribute("username", u.getUserName());
			
			// Logic phân quyền dựa trên roleId
			switch (u.getRoleId()) {
				case 1: // Admin - Quyền cao nhất
					req.getRequestDispatcher("/views/admin/category/list-category.jsp").forward(req, resp);
					break;
				case 2: // Manager - Quyền trung bình  
					resp.sendRedirect(req.getContextPath() + "/views/manager/home.jsp");
					break;
				case 3: // User - Quyền thấp nhất
					req.getRequestDispatcher("/views/users/home.jsp").forward(req, resp);
					break;
				default:
					// Role không hợp lệ
					req.setAttribute("error", "Quyền truy cập không hợp lệ!");
					resp.sendRedirect(req.getContextPath() + "/login");
					break;
			}
		} else {
			 req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
		}
	}
}