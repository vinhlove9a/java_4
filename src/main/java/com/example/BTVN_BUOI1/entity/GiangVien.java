package com.example.BTVN_BUOI1.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "giang_vien")
public class GiangVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Khóa chính tự động tăng
    private Long id;

    @Column(name = "mssv", length = 100, nullable = false, unique = true)
    private String mssv;

    @Column(name = "ten", length = 100, nullable = false)
    private String ten;

    @Column(name = "tuoi")
    private Long tuoi;

    @Column(name = "gioi_tinh")
    private Boolean gioiTinh; // Dùng Boolean để ánh xạ BIT (true = 1, false = 0)

    @Column(name = "que_quan", length = 100)
    private String queQuan;

    // Constructor hợp lệ
    public GiangVien(String mssv, String ten, Long tuoi, String queQuan, Boolean gioiTinh) {
        this.mssv = mssv;
        this.ten = ten;
        this.tuoi = tuoi;
        this.queQuan = queQuan;
        this.gioiTinh = gioiTinh;
    }

    @Override
    public String toString() {
        return "GiangVien{" +
                "id=" + id +
                ", mssv='" + mssv + '\'' +
                ", ten='" + ten + '\'' +
                ", tuoi=" + tuoi +
                ", gioiTinh=" + (gioiTinh != null ? (gioiTinh ? "Nam" : "Nữ") : "Không xác định") +
                ", queQuan='" + queQuan + '\'' +
                '}';
    }
}
