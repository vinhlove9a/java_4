package com.example.BTVN_BUOI1.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "giang_vien")

public class GiangVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Khóa chính tự động tăng
    private Long id;

    @Column(name = "mssv", length = 100)
    private String mssv;

    @Column(name = "ten", length = 100)
    private String ten;

    @Column(name = "tuoi")
    private Long tuoi;

    @Column(name = "gioi_tinh")
    private Boolean gioiTinh; // Dùng Boolean để ánh xạ BIT (true = 1, false = 0)

    @Column(name = "que_quan", length = 100)
    private String queQuan;
    @Override
    public String toString() {
        return "GiangVien{" +
                "id=" + id +
                ", ten='" + ten + '\'' +
                ", tuoi=" + tuoi +
                ", gioiTinh='" + gioiTinh + '\'' +
                ", mssv='" + mssv + '\'' +
                ", queQuan='" + queQuan + '\'' +
                '}';
    }

}
