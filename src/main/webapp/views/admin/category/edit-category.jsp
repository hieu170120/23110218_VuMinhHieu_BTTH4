<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sửa danh mục</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 600px;
            margin: 0 auto;
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            text-align: center;
            margin-bottom: 30px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
            color: #555;
        }
        .form-control {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
            box-sizing: border-box;
        }
        .form-control:focus {
            outline: none;
            border-color: #4CAF50;
            box-shadow: 0 0 5px rgba(76, 175, 80, 0.3);
        }
        .btn {
            padding: 12px 24px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            margin-right: 10px;
        }
        .btn-primary {
            background-color: #007bff;
            color: white;
        }
        .btn-primary:hover {
            background-color: #0056b3;
        }
        .btn-danger {
            background-color: #f44336;
            color: white;
        }
        .btn-danger:hover {
            background-color: #da190b;
        }
        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }
        .btn-secondary:hover {
            background-color: #5a6268;
        }
        .back-link {
            text-align: center;
            margin-top: 20px;
        }
        .back-link a {
            color: #007bff;
            text-decoration: none;
        }
        .back-link a:hover {
            text-decoration: underline;
        }
        .current-image {
            margin: 10px 0;
            padding: 10px;
            background-color: #f8f9fa;
            border-radius: 5px;
        }
        .current-image img {
            max-width: 200px;
            max-height: 150px;
            border: 1px solid #ddd;
            border-radius: 3px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Sửa danh mục</h1>
        
        <!-- Display error message if any -->
        <c:if test="${not empty alert}">
            <div class="alert" style="background-color: #f8d7da; color: #721c24; padding: 15px; border-radius: 5px; margin-bottom: 20px; border: 1px solid #f5c6cb;">
                <strong>Lỗi:</strong> ${alert}
            </div>
        </c:if>
        
        <form role="form" action="${pageContext.request.contextPath}/admin/category/edit" method="post" enctype="multipart/form-data">
            <input type="hidden" name="id" value="${category.id}" />
            <input type="hidden" name="oldIcon" value="${category.icon}" />
            
            <div class="form-group">
                <label for="categoryName">Tên danh mục:</label>
                <input 
                    type="text" 
                    class="form-control" 
                    id="categoryName" 
                    name="name" 
                    value="${category.name}"
                    placeholder="Vui lòng nhập tên danh mục"
                    required 
                />
            </div>

            <div class="form-group">
                <label for="categoryIcon">Ảnh đại diện mới:</label>
                <input 
                    type="file" 
                    class="form-control" 
                    id="categoryIcon" 
                    name="icon" 
                    accept="image/*"
                />
                <small style="color: #666;">Để trống nếu không muốn thay đổi ảnh</small>
                
                <!-- Hiển thị ảnh hiện tại -->
                <c:if test="${not empty category.icon}">
                    <div class="current-image">
                        <strong>Ảnh hiện tại:</strong><br>
                        <img src="${pageContext.request.contextPath}/${category.icon}" alt="${category.name}" />
                    </div>
                </c:if>
            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-primary">Cập nhật</button>
                <button type="reset" class="btn btn-danger">Hủy</button>
                <a href="${pageContext.request.contextPath}/admin/category/list" class="btn btn-secondary">Danh sách</a>
            </div>
        </form>
        
        <div class="back-link">
            <a href="${pageContext.request.contextPath}/waiting">← Quay lại trang quản lý</a>
        </div>
    </div>
</body>
</html>
