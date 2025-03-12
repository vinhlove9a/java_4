package com.example.BTVN_BUOI1.servlet;

import com.example.BTVN_BUOI1.entity.GiangVien;
import com.example.BTVN_BUOI1.repository.GiangVienRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "GiangVienServlet", value = {"/GiangVienServlet",
        "/giang-vien/hien-thi-tat-ca",
        "/giang-vien/detail",
        "/giang-vien/remove",
        "/giang-vien/view-update",
        "/giang-vien/tim-kiem",
        "/giang-vien/add",
})
public class GiangVienServlet extends HttpServlet {
    GiangVienRepository giangVienRepo = new GiangVienRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();

        // Lấy danh sách giảng viên
        List<GiangVien> danhSachGiangVien = giangVienRepo.getAll();
        request.setAttribute("danhSachGiangVien", danhSachGiangVien);

        if (uri.equals("/giang-vien/hien-thi-tat-ca")) {
            request.getRequestDispatcher("/giang-vien.jsp").forward(request, response);

        } else if (uri.equals("/giang-vien/detail")) {
            String idParam = request.getParameter("id");
            if (idParam != null) {
                try {
                    Long id = Long.parseLong(idParam);
                    GiangVien gv = giangVienRepo.getOne(id);
                    if (gv != null) {
                        request.setAttribute("giangVien", gv);
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "ID không hợp lệ");
                }
            }
            request.getRequestDispatcher("/giang-vien.jsp").forward(request, response);
        } else if (uri.equals("/giang-vien/remove")) {
            String idParam = request.getParameter("id");
            if (idParam != null) {
                try {
                    Long id = Long.parseLong(idParam);
                    giangVienRepo.delete(id);
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "ID không hợp lệ");
                }
            }
            response.sendRedirect("/giang-vien/hien-thi-tat-ca");
            return;
        } else if (uri.equals("/giang-vien/view-update")) {
            Long id = Long.parseLong(request.getParameter("id"));
            GiangVien gv = giangVienRepo.getOne(id);
            request.setAttribute("giangVien", gv);
            request.getRequestDispatcher("/update-giang-vien.jsp").forward(request, response);
        } else if (uri.equals("/giang-vien/tim-kiem")) {
            String ten = request.getParameter("ten");
            String tuoiMinParam = request.getParameter("tuoiMin");
            String tuoiMaxParam = request.getParameter("tuoiMax");

            Long tuoiMin = (tuoiMinParam != null && !tuoiMinParam.isEmpty()) ? Long.parseLong(tuoiMinParam) : null;
            Long tuoiMax = (tuoiMaxParam != null && !tuoiMaxParam.isEmpty()) ? Long.parseLong(tuoiMaxParam) : null;

            // 🔹 Gọi Repository để tìm kiếm
            List<GiangVien> dsGiangVien = giangVienRepo.search(ten, tuoiMin, tuoiMax);

            // 🔹 Truyền dữ liệu vào request để hiển thị trên JSP
            request.setAttribute("danhSachGiangVien", dsGiangVien);
            request.setAttribute("isSearching", true); // Đánh dấu trạng thái tìm kiếm
        } else {
            // 🔹 Nếu không tìm kiếm, hiển thị toàn bộ danh sách
            List<GiangVien> dsGiangVien = giangVienRepo.getAll();
            request.setAttribute("dsGiangVien", dsGiangVien);
            request.setAttribute("isSearching", false); // Không phải tìm kiếm
        }

        request.getRequestDispatcher("/giang-vien.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();

        if (uri.equals("/giang-vien/view-update")) {
            try {
                // 🟢 Lấy dữ liệu từ request
                Long id = Long.parseLong(request.getParameter("id"));
                String hoTen = request.getParameter("ten");
                Long tuoi = Long.parseLong(request.getParameter("tuoi"));
                String queQuan = request.getParameter("queQuan");

                // 🟢 Lấy giá trị giới tính
                boolean gioiTinh = Boolean.parseBoolean(request.getParameter("gioiTinh"));

                // 🟢 Kiểm tra giảng viên có tồn tại không
                GiangVien gv = giangVienRepo.getOne(id);
                if (gv == null) {
                    request.setAttribute("error", "Không tìm thấy giảng viên!");
                    request.getRequestDispatcher("/giang-vien/hien-thi-tat-ca").forward(request, response);
                    return;
                }

                // 🟢 Cập nhật thông tin
                gv.setTen(hoTen);
                gv.setTuoi(tuoi);
                gv.setQueQuan(queQuan);
                gv.setGioiTinh(gioiTinh);

                // 🟢 Thực hiện cập nhật
                giangVienRepo.update(gv);

                response.sendRedirect("/giang-vien/hien-thi-tat-ca");

            } catch (Exception e) {
                request.setAttribute("error", "Lỗi khi cập nhật giảng viên: " + e.getMessage());
                request.getRequestDispatcher("/update-giang-vien.jsp").forward(request, response);
            }
        }
        if (uri.equals("/giang-vien/add")) {
            try {
                // 🟢 Lấy dữ liệu từ request
                String mssv = request.getParameter("mssv");
                String hoTen = request.getParameter("ten");
                String tuoiParam = request.getParameter("tuoi");
                String queQuan = request.getParameter("queQuan");
                String gioiTinhParam = request.getParameter("gioiTinh");

                // 🛑 Kiểm tra dữ liệu nhập vào
                if (mssv == null || mssv.trim().isEmpty() ||
                        hoTen == null || hoTen.trim().isEmpty() ||
                        tuoiParam == null || tuoiParam.trim().isEmpty() ||
                        queQuan == null || queQuan.trim().isEmpty() ||
                        gioiTinhParam == null || gioiTinhParam.trim().isEmpty()) {

                    request.setAttribute("error", "Vui lòng nhập đầy đủ thông tin!");

                    // 🟢 Lưu danh sách giảng viên để hiển thị lại mà không bị mất
                    List<GiangVien> danhSachGiangVien = giangVienRepo.getAll();
                    request.setAttribute("danhSachGiangVien", danhSachGiangVien);

                    request.getRequestDispatcher("/giang-vien.jsp").forward(request, response);
                    return;
                }

                // Chuyển đổi kiểu dữ liệu
                Long tuoi = Long.parseLong(tuoiParam);
                boolean gioiTinh = Boolean.parseBoolean(gioiTinhParam);

                // 🟢 Tạo đối tượng GiangVien mới
                GiangVien gv = new GiangVien();
                gv.setMssv(mssv);
                gv.setTen(hoTen);
                gv.setTuoi(tuoi);
                gv.setQueQuan(queQuan);
                gv.setGioiTinh(gioiTinh);

                // 🟢 Thêm vào database
                giangVienRepo.add(gv);

                // 🟢 Gửi thông báo thành công
                request.getSession().setAttribute("success", "Thêm thành công");

                // 🟢 Chuyển hướng về danh sách
                response.sendRedirect("/giang-vien/hien-thi-tat-ca");
            } catch (Exception e) {
                // 🟢 Chỉ forward nếu lỗi xảy ra trước khi commit response
                request.setAttribute("error", "Lỗi khi thêm giảng viên: " + e.getMessage());
                request.getRequestDispatcher("/giang-vien.jsp").forward(request, response);
            }
        }

    }


}
