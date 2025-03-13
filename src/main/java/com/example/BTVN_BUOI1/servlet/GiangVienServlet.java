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
        "/giang-vien/add"})
public class GiangVienServlet extends HttpServlet {
    private final GiangVienRepository giangVienRepo = new GiangVienRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (uri.endsWith("/giang-vien/hien-thi-tat-ca")) {
            hienThiTatCa(request, response);
        } else if (uri.endsWith("/giang-vien/detail")) {
            hienThiChiTiet(request, response);
        } else if (uri.endsWith("/giang-vien/remove")) {
            xoaGiangVien(request, response);
        } else if (uri.endsWith("/giang-vien/view-update")) {
            hienThiFormCapNhat(request, response);
        } else if (uri.endsWith("/giang-vien/tim-kiem")) {
            timKiemGiangVien(request, response);
        } else {
            hienThiTatCa(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (uri.endsWith("/giang-vien/add")) {
            themGiangVien(request, response);
        } else if (uri.endsWith("/giang-vien/view-update")) {
            capNhatGiangVien(request, response);
        }
    }

    private void hienThiTatCa(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<GiangVien> danhSachGiangVien = giangVienRepo.getAll();
        request.setAttribute("danhSachGiangVien", danhSachGiangVien);
        request.getRequestDispatcher("/giang-vien.jsp").forward(request, response);
    }

    private void hienThiChiTiet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            GiangVien gv = giangVienRepo.getOne(id);
            request.setAttribute("giangVien", gv);
            List<GiangVien> danhSachGiangVien = giangVienRepo.getAll();
            request.setAttribute("danhSachGiangVien", danhSachGiangVien);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID không hợp lệ");
        }
        request.getRequestDispatcher("/giang-vien.jsp").forward(request, response);
    }

    private void xoaGiangVien(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            giangVienRepo.delete(id);
        } catch (NumberFormatException ignored) {
        }
        response.sendRedirect("/giang-vien/hien-thi-tat-ca");
    }

    private void hienThiFormCapNhat(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        GiangVien gv = giangVienRepo.getOne(id);
        request.setAttribute("giangVien", gv);
        request.getRequestDispatcher("/update-giang-vien.jsp").forward(request, response);
    }

    private void timKiemGiangVien(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ten = request.getParameter("ten");
        Long tuoiMin = parseLong(request.getParameter("tuoiMin"));
        Long tuoiMax = parseLong(request.getParameter("tuoiMax"));

        List<GiangVien> dsGiangVien = giangVienRepo.search(ten, tuoiMin, tuoiMax);
        request.setAttribute("danhSachGiangVien", dsGiangVien);
        request.setAttribute("isSearching", true);
        request.getRequestDispatcher("/giang-vien.jsp").forward(request, response);
    }

    private void themGiangVien(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String mssv = request.getParameter("mssv");
            String hoTen = request.getParameter("ten");
            Long tuoi = parseLong(request.getParameter("tuoi"));
            String queQuan = request.getParameter("queQuan");
            boolean gioiTinh = Boolean.parseBoolean(request.getParameter("gioiTinh"));

            if (mssv == null || mssv.trim().isEmpty() || hoTen == null || hoTen.trim().isEmpty()) {
                request.setAttribute("error", "Vui lòng nhập đầy đủ thông tin!");
                hienThiTatCa(request, response);
                return;
            }
            GiangVien gv = new GiangVien(mssv, hoTen, tuoi, queQuan, gioiTinh);
            giangVienRepo.add(gv);
            request.setAttribute("success", "Thêm giảng viên thành công!");
            hienThiTatCa(request, response);
            response.sendRedirect("/giang-vien/hien-thi-tat-ca");
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi khi thêm giảng viên: " + e.getMessage());
            request.getRequestDispatcher("/giang-vien.jsp").forward(request, response);
        }
    }

    private void capNhatGiangVien(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            String hoTen = request.getParameter("ten");
            Long tuoi = parseLong(request.getParameter("tuoi"));
            String queQuan = request.getParameter("queQuan");
            boolean gioiTinh = Boolean.parseBoolean(request.getParameter("gioiTinh"));

            GiangVien gv = giangVienRepo.getOne(id);
            if (gv == null) {
                request.setAttribute("error", "Không tìm thấy giảng viên!");
                hienThiTatCa(request, response);
                return;
            }

            gv.setTen(hoTen);
            gv.setTuoi(tuoi);
            gv.setQueQuan(queQuan);
            gv.setGioiTinh(gioiTinh);

            giangVienRepo.update(gv);
            response.sendRedirect("/giang-vien/hien-thi-tat-ca");
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi khi cập nhật giảng viên: " + e.getMessage());
            request.getRequestDispatcher("/update-giang-vien.jsp").forward(request, response);
        }
    }

    private Long parseLong(String value) {
        try {
            return (value != null && !value.isEmpty()) ? Long.parseLong(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
