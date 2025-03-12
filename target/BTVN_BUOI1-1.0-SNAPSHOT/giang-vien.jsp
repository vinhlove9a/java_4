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
</head>
<body>
<h2>Tìm kiếm Giảng Viên</h2>

<form action="/giang-vien/tim-kiem" method="get">
    <label for="ten">Tên:</label>
    <input type="text" id="ten" name="ten"
           value="<%= request.getAttribute("ten") != null ? request.getAttribute("ten") : "" %>">

    <label for="tuoiMin">Tuổi từ:</label>
    <input type="number" id="tuoiMin" name="tuoiMin"
           value="<%= request.getAttribute("tuoiMin") != null ? request.getAttribute("tuoiMin") : "" %>">

    <label for="tuoiMax">đến:</label>
    <input type="number" id="tuoiMax" name="tuoiMax"
           value="<%= request.getAttribute("tuoiMax") != null ? request.getAttribute("tuoiMax") : "" %>">

    <button type="submit">Tìm kiếm</button>
</form>


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
    <button type="submit">add</button>
</form>
<c:if test="${not empty error}">
    <div style="color: red;">${error}</div>
</c:if>

<c:if test="${not empty success}">
    <div style="color: green;">${success}</div>
</c:if>

<h2>Danh sách Giảng Viên</h2>
<%-- Hiển thị thông báo thành công --%>
<c:if test="${not empty success}">
    <div style="color: green; font-weight: bold; margin-bottom: 10px;">
            ${success}
    </div>
</c:if>
<table border="1">
    <tr>
        <th>Mã GV</th>
        <th>Họ tên</th>
        <th>Tuổi</th>
        <th>Quê quán</th>
        <th>Giới tính</th>
        <th>Hành động</th>
    </tr>
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
                        <a href="/giang-vien/detail?id=${gv.id}">
                            <button>Detail</button>
                        </a>
                        <a href="/giang-vien/view-update?id=${gv.id}">
                            <button>Edit</button>
                        </a>
                        <a href="/giang-vien/remove?id=${gv.id}"
                           onclick="return confirm('Bạn có chắc chắn muốn xóa không?');">
                            <button>Remove</button>
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <tr>
                <td colspan="6" style="text-align: center; color: red;">Không tìm thấy giảng viên nào.</td>
            </tr>
        </c:otherwise>
    </c:choose>
</table>


</body>
</html>