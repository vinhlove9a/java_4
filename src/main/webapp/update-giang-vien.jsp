<%--
  Created by IntelliJ IDEA.
  User: Vinh
  Date: 3/11/2025
  Time: 3:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Cập nhật Giảng viên</title>
    <!-- Thêm Bootstrap 5 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="bg-light">

<div class="container mt-4">
    <h2 class="text-center text-primary">Cập nhật thông tin Giảng viên</h2>

    <div class="card">
        <div class="card-header bg-warning text-white">Thông tin Giảng viên</div>
        <div class="card-body">
            <form action="/giang-vien/view-update" method="post" class="row g-3">
                <!-- Truyền ID giảng viên để cập nhật -->
                <input type="hidden" name="id" value="${giangVien.id}"/>

                <div class="col-md-6">
                    <label class="form-label">Mã GV:</label>
                    <input type="text" name="mssv" value="${giangVien.mssv}" class="form-control" required>
                </div>

                <div class="col-md-6">
                    <label class="form-label">Họ tên:</label>
                    <input type="text" name="ten" value="${giangVien.ten}" class="form-control" required>
                </div>

                <div class="col-md-4">
                    <label class="form-label">Tuổi:</label>
                    <input type="number" name="tuoi" value="${giangVien.tuoi}" class="form-control" required min="18">
                </div>

                <div class="col-md-4">
                    <label class="form-label">Quê quán:</label>
                    <input type="text" name="queQuan" value="${giangVien.queQuan}" class="form-control" required>
                </div>

                <div class="col-md-4">
                    <label class="form-label">Giới tính:</label><br>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="gioiTinh" value="true" ${giangVien != null && giangVien.gioiTinh ? "checked" : ""}>
                        <label class="form-check-label">Nam</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="gioiTinh" value="false" ${giangVien != null && !giangVien.gioiTinh ? "checked" : ""}>
                        <label class="form-check-label">Nữ</label>
                    </div>
                </div>

                <div class="col-md-12">
                    <button type="submit" class="btn btn-primary w-100">Cập nhật</button>
                </div>
            </form>
        </div>
    </div>

    <div class="text-center mt-3">
        <a href="/giang-vien/hien-thi-tat-ca" class="btn btn-secondary">Quay lại danh sách</a>
    </div>
</div>

</body>
</html>


