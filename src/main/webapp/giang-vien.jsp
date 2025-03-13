<%--
  Created by IntelliJ IDEA.
  User: Vinh
  Date: 3/11/2025
  Time: 2:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Quản lý Giảng viên</title>
    <!-- Thêm Bootstrap 5 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="bg-light">

<div class="container mt-4">
    <h2 class="text-center text-primary">Quản lý Giảng viên</h2>

    <!-- Form Tìm kiếm -->
    <div class="card mb-4">
        <div class="card-header bg-primary text-white">Tìm kiếm Giảng viên</div>
        <div class="card-body">
            <form action="/giang-vien/tim-kiem" method="get" class="row g-3">
                <div class="col-md-4">
                    <label for="ten" class="form-label">Tên:</label>
                    <input type="text" id="ten" name="ten" value="${ten}" class="form-control">
                </div>
                <div class="col-md-3">
                    <label for="tuoiMin" class="form-label">Tuổi từ:</label>
                    <input type="number" id="tuoiMin" name="tuoiMin" value="${tuoiMin}" class="form-control">
                </div>
                <div class="col-md-3">
                    <label for="tuoiMax" class="form-label">đến:</label>
                    <input type="number" id="tuoiMax" name="tuoiMax" value="${tuoiMax}" class="form-control">
                </div>
                <div class="col-md-2 d-flex align-items-end">
                    <button type="submit" class="btn btn-primary w-100">Tìm kiếm</button>
                </div>
            </form>
        </div>
    </div>

    <!-- Form Thêm Giảng viên -->
    <div class="card mb-4">
        <div class="card-header bg-success text-white">Thêm Giảng viên</div>
        <div class="card-body">
            <form action="/giang-vien/add" method="post" class="row g-3">
                <div class="col-md-6">
                    <label class="form-label">Mã GV:</label>
                    <input type="text" name="mssv" value="${giangVien.mssv}" class="form-control">
                </div>
                <div class="col-md-6">
                    <label class="form-label">Họ tên:</label>
                    <input type="text" name="ten" value="${giangVien.ten}" class="form-control">
                </div>
                <div class="col-md-4">
                    <label class="form-label">Tuổi:</label>
                    <input type="number" name="tuoi" value="${giangVien.tuoi}" class="form-control">
                </div>
                <div class="col-md-4">
                    <label class="form-label">Quê quán:</label>
                    <input type="text" name="queQuan" value="${giangVien.queQuan}" class="form-control">
                </div>
                <div class="col-md-4">
                    <label class="form-label">Giới tính:</label><br>
                    <input type="radio" name="gioiTinh" value="true" ${giangVien != null && giangVien.gioiTinh ? "checked" : ""}> Nam
                    <input type="radio" name="gioiTinh" value="false" ${giangVien != null && !giangVien.gioiTinh ? "checked" : ""}> Nữ
                </div>
                <div class="col-md-12">
                    <button type="submit" class="btn btn-success w-100">Thêm giảng viên</button>
                </div>
            </form>
        </div>
    </div>

    <!-- Hiển thị thông báo -->
    <c:if test="${not empty success || not empty error}">
        <div class="alert ${not empty success ? 'alert-success' : 'alert-danger'}" role="alert">
                ${not empty success ? success : error}
        </div>
    </c:if>
    <!-- Danh sách Giảng viên -->
    <div class="card">
        <div class="card-header bg-dark text-white">Danh sách Giảng viên</div>
        <div class="card-body">
            <table class="table table-striped table-hover">
                <thead class="table-dark">
                <tr>
                    <th>Mã GV</th>
                    <th>Họ tên</th>
                    <th>Tuổi</th>
                    <th>Quê quán</th>
                    <th>Giới tính</th>
                    <th>Hành động</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${not empty danhSachGiangVien}">
                        <c:forEach var="gv" items="${danhSachGiangVien}">
                            <tr>
                                <td>${gv.mssv}</td>
                                <td>${gv.ten}</td>
                                <td>${gv.tuoi}</td>
                                <td>${gv.queQuan}</td>
                                <td>${gv.gioiTinh ? "Nam" : "Nữ"}</td>
                                <td>
                                    <a href="/giang-vien/detail?id=${gv.id}" class="btn btn-info btn-sm">Chi tiết</a>
                                    <a href="/giang-vien/view-update?id=${gv.id}" class="btn btn-warning btn-sm">Sửa</a>
                                    <a href="/giang-vien/remove?id=${gv.id}" class="btn btn-danger btn-sm"
                                       onclick="return confirmDelete('${gv.ten}');">Xóa</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="6" class="text-center text-danger">Không tìm thấy giảng viên nào.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- JavaScript Xác nhận Xóa -->
<script>
    function confirmDelete(name) {
        return confirm('Bạn có chắc chắn muốn xóa giảng viên: ' + name + ' không?');
    }
</script>

</body>
</html>

