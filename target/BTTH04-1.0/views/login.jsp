<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="topbar.jsp"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }
        h2 {
            font-size: 24px;
            text-align: center;
            margin-bottom: 20px;
        }
        section {
            margin-bottom: 15px;
        }
        label {
            display: block;
            font-size: 14px;
            margin-bottom: 5px;
        }
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 5px;
            margin-top: 5px;
            box-sizing: border-box;
        }
        input[type="checkbox"] {
            margin-right: 10px;
        }
        button {
            width: 100%;
            padding: 12px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            margin-top: 20px;
        }
        button:hover {
            background-color: #45a049;
        }
        .mt-2 {
            margin-top: 20px;
        }
        .text-center {
            text-align: center;
        }
        .btn-link {
            color: #007bff;
            text-decoration: none;
        }
        .btn-link:hover {
            text-decoration: underline;
        }
        .alert {
            color: red;
            text-align: center;
        }
    </style>
</head>
<body>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <h2>Đăng nhập</h2>

        <!-- Display error message if any -->
        <c:if test="${not empty alert}">
            <div class="alert">
                <h3>${alert}</h3>
            </div>
        </c:if>

        <!-- Username Input -->
        <section>
            <label for="username">Tài khoản</label>
            <input type="text" id="username" name="username" placeholder="Tài khoản" value="${param.username}" required>
        </section>

        <!-- Password Input -->
        <section>
            <label for="password">Mật khẩu</label>
            <input type="password" id="password" name="password" placeholder="Mật khẩu" required>
        </section>

        <!-- Remember Me Checkbox -->
        <section>
            <label>
                <input type="checkbox" name="remember" <c:if test="${not empty rememberedUser}">checked</c:if> /> Ghi nhớ đăng nhập
            </label>
        </section>

        <!-- Submit Button -->
        <button type="submit">Đăng nhập</button>

        <!-- Forgot Password Link -->
        <div class="mt-2 text-center">
            <a href="${pageContext.request.contextPath}/forget" class="btn-link">Quên mật khẩu?</a>
        </div>
    </form>

    <p class="text-center">
        Test tài khoản: <br>
        usn: <b>admin</b> <br>
        pass: <b>123456</b>
    </p>

</body>
</html>
