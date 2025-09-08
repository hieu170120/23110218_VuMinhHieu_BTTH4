<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thêm danh mục mới</title>
    <style>
        /* CSS giữ nguyên */
    </style>
</head>
<body>
    <div class="container">
        <h1>Thêm danh mục mới</h1>
        
        <!-- Display error message if any -->
        <c:if test="${not empty alert}">
            <div class="alert" style="background-color: #f8d7da; color: #721c24; padding: 15px; border-radius: 5px; margin-bottom: 20px; border: 1px solid #f5c6cb;">
                <strong>Lỗi:</strong> ${alert}
            </div>
        </c:if>
        
        <form role="form" action="${pageContext.request.contextPath}/admin/category/add" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="categoryName">Tên danh mục:</label>
                <input 
                    type="text" 
                    class="form-control" 
                    id="categoryName" 
                    name="name" 
                    placeholder="Vui lòng nhập tên danh mục"
                    required 
                />
            </div>

            <div class="form-group">
                <label for="categoryIcon">Ảnh đại diện:</label>
                <input 
                    type="file" 
                    class="form-control" 
                    id="categoryIcon" 
                    name="icon" 
                    accept="image/*"
                    required 
                />
            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-success">Thêm</button>
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
