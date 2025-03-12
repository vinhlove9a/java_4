<%--
  Created by IntelliJ IDEA.
  User: Vinh
  Date: 3/11/2025
  Time: 3:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cập nhật giảng viên</title>
</head>
<body>
<h2>Cập nhật thông tin giảng viên</h2>
<form action="/giang-vien/add" method="post">
    <label>Mã GV:</label>
    <input type="text" name="mssv" value="${giangVien.mssv}"/><br>

    <label>Họ tên:</label>
    <input type="text" name="ten" value="${giangVien.ten}"/><br>

    <label>Tuổi:</label>
    <input type="number" name="tuoi" value="${giangVien.tuoi}"/><br>

    <label>Quê quán:</label>
    <input type="text" name="queQuan" value="${giangVien.queQuan}"/><br>

    <label>Giới tính:</label>
    <input type="radio" name="gioiTinh" value="true" ${giangVien.gioiTinh ? "checked" : ""} /> Nam
    <input type="radio" name="gioiTinh" value="false" ${!giangVien.gioiTinh ? "checked" : ""} /> Nữ
    <br>

    <c:if test="${empty giangVien}">
        <button type="submit">Cap nhap</button>
    </c:if>
</form>


<br>
<a href="/giang-vien/hien-thi-tat-ca">Quay lại danh sách</a>
</body>
</html>
