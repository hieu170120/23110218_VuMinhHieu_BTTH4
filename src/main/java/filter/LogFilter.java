package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

import java.io.IOException;

@WebFilter(urlPatterns = {"/admin/*", "/manager/*", "/user/*"})  // Áp dụng filter cho các URL cần phân quyền
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Khởi tạo filter (nếu cần)
    }

    @Override
    public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        // Kiểm tra nếu session đã có account
        if (session != null && session.getAttribute("account") != null) {
            User u = (User) session.getAttribute("account");

            // Logic phân quyền dựa trên roleId
            switch (u.getRoleId()) {
                case 1: // Admin
                    // Cho phép tiếp tục yêu cầu
                    chain.doFilter(request, response);
                    break;
                case 2: // Manager
                    // Kiểm tra nếu user truy cập vào trang quản lý của Admin
                    if (req.getRequestURI().contains("/admin/")) {
                        resp.sendRedirect(req.getContextPath() + "/views/manager/home.jsp");
                    } else {
                        chain.doFilter(request, response);
                    }
                    break;
                case 3: // User
                    // Kiểm tra nếu user truy cập vào trang quản lý của Admin hoặc Manager
                    if (req.getRequestURI().contains("/admin/") || req.getRequestURI().contains("/manager/")) {
                        resp.sendRedirect(req.getContextPath() + "/views/users/home.jsp");
                    } else {
                        chain.doFilter(request, response);
                    }
                    break;
                default:
                    // Role không hợp lệ
                    req.setAttribute("error", "Quyền truy cập không hợp lệ!");
                    resp.sendRedirect(req.getContextPath() + "/login");
                    break;
            }
        } else {
            // Nếu không có session người dùng, chuyển hướng tới trang đăng nhập
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }

    @Override
    public void destroy() {
        // Làm sạch filter (nếu cần)
    }
}
