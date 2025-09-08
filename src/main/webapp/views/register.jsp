<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="topbar.jsp"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Đăng ký</title>

<!-- Bootstrap -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Font Awesome (cho icon) -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

</head>
<body>
	<div class="register-container">
		<h2 class="text-center mb-4">Tạo tài khoản mới</h2>

		<c:if test="${alert !=null}">
			<div class="alert alert-danger text-center">${alert}</div>
		</c:if>

		<form action="${pageContext.request.contextPath}/register" method="post">

			<div class="mb-3 input-group">
				<span class="input-group-text"><i class="fa fa-user"></i></span> <input
					type="text" class="form-control" placeholder="Tài khoản"
					name="username" required>
			</div>

			<div class="mb-3 input-group">
				<span class="input-group-text"><i class="fa fa-lock"></i></span> <input
					type="password" class="form-control" placeholder="Mật khẩu"
					name="password" required>
			</div>

			<div class="mb-3 input-group">
				<span class="input-group-text"><i class="fa fa-id-card"></i></span>
				<input type="text" class="form-control" placeholder="Họ và tên"
					name="fullname" required>
			</div>

			<div class="mb-3 input-group">
				<span class="input-group-text"><i class="fa fa-envelope"></i></span>
				<input type="text" class="form-control" placeholder="Phone"
					name="phone" required>
			</div>
			<div class="mb-3 input-group">
				<span class="input-group-text"><i class="fa fa-envelope"></i></span>
				<input type="email" class="form-control" placeholder="Email"
					name="email" required>
			</div>
			<button type="submit" class="btn btn-primary w-100">Đăng ký</button>
		</form>
	</div>
</body>
</html>