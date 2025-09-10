<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý danh mục</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            text-align: center;
            margin-bottom: 30px;
        }
        .welcome {
            background-color: #e7f3ff;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
            border-left: 4px solid #2196F3;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        .btn:hover {
            background-color: #45a049;
        }
        .btn-danger {
            background-color: #f44336;
        }
        .btn-danger:hover {
            background-color: #da190b;
        }
        .btn-warning {
            background-color: #ff9800;
        }
        .btn-warning:hover {
            background-color: #e68900;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
            font-weight: bold;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        .center {
            text-align: center;
        }
        .logout {
            float: right;
            background-color: #f44336;
        }
        .logout:hover {
            background-color: #da190b;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Quản lý danh mục</h1>
        
        <!-- Welcome message -->
        <div class="welcome">
            <strong>Xin chào, ${username}!</strong> 
            <a href="${pageContext.request.contextPath}/logout" class="btn logout">Đăng xuất</a>
        </div>
        
        <!-- Add new category button -->
        <a href="${pageContext.request.contextPath}/views/admin/category/add-category.jsp" class="btn">Thêm danh mục mới</a>
        
        <!-- Categories table -->
        <table>
            <thead>
                <tr>
                    <th>STT</th>
                    <th>Ảnh đại diện</th>
                    <th>Tên danh mục</th>
                    <th>Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty cateList}">
                        <c:forEach items="${cateList}" var="cate" varStatus="STT">
                            <tr class="odd gradeX">
                                <!-- Hiển thị số thứ tự -->
                                <td>${STT.index + 1}</td>

                                <!-- Hiển thị hình ảnh -->
                                <td>
                                    <c:if test="${not empty cate.icon}">
                                        <img height="150" width="200" src="${pageContext.request.contextPath}/${cate.icon}" alt="${cate.name}"/>
                                    </c:if>
                                    <c:if test="${empty cate.icon}">
                                        <span style="color: #999;">Không có ảnh</span>
                                    </c:if>
                                </td>

                                <!-- Hiển thị tên danh mục -->
                                <td>${cate.name}</td>

                                <!-- Các liên kết Sửa và Xóa -->
                                <td class="center">
                                    <a href="<c:url value='/views/admin/category/edit-category.jsp?id=${cate.id}'/>" class="btn btn-warning">Sửa</a>
                                    | 
                                    <a href="<c:url value='/admin/category/delete?id=${cate.id}'/>" class="btn btn-danger" 
                                       onclick="return confirm('Bạn có chắc chắn muốn xóa danh mục này?')">Xóa</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="4" class="center">Không có danh mục nào</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
        
        <!-- Back to home -->
        <div style="margin-top: 20px; text-align: center;">
            <a href="${pageContext.request.contextPath}/" class="btn">Về trang chủ</a>
        </div>
    </div>
</body>
</html>
