package com.example.BTVN_BUOI1.repository;

import com.example.BTVN_BUOI1.entity.GiangVien;
import com.example.BTVN_BUOI1.utility.DBConnect;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class GiangVienRepository {

    // Lấy danh sách tất cả giảng viên
    public List<GiangVien> getAll() {
        try (Session session = DBConnect.getSessionFactory().openSession()) {
            return session.createQuery("FROM GiangVien", GiangVien.class).list();
        }
    }

    // Lấy thông tin một giảng viên theo ID
    public GiangVien getOne(Long id) {
        try (Session session = DBConnect.getSessionFactory().openSession()) {
            return session.find(GiangVien.class, id);
        }
    }

    // Thêm giảng viên mới
    public void add(GiangVien gv) {
        Transaction transaction = null;
        try (Session session = DBConnect.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(gv);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi thêm giảng viên: " + e.getMessage());
        }
    }

    // Cập nhật thông tin giảng viên
    public void update(GiangVien gv) {
        Transaction transaction = null;
        try (Session session = DBConnect.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(gv);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi cập nhật giảng viên: " + e.getMessage());
        }
    }

    // Xóa giảng viên theo ID
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = DBConnect.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            GiangVien gv = session.find(GiangVien.class, id);
            if (gv != null) {
                session.delete(gv);
                transaction.commit();
            } else {
                throw new RuntimeException("Giảng viên không tồn tại!");
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi xóa giảng viên: " + e.getMessage());
        }
    }

    // Tìm kiếm giảng viên theo tên và tuổi
    public List<GiangVien> search(String ten, Long tuoiMin, Long tuoiMax) {
        try (Session session = DBConnect.getSessionFactory().openSession()) {
            StringBuilder hql = new StringBuilder("FROM GiangVien g WHERE 1=1");

            if (ten != null && !ten.trim().isEmpty()) {
                hql.append(" AND g.ten LIKE :ten");
            }
            if (tuoiMin != null) {
                hql.append(" AND g.tuoi >= :tuoiMin");
            }
            if (tuoiMax != null) {
                hql.append(" AND g.tuoi <= :tuoiMax");
            }

            Query<GiangVien> query = session.createQuery(hql.toString(), GiangVien.class);

            if (ten != null && !ten.trim().isEmpty()) {
                query.setParameter("ten", "%" + ten + "%");
            }
            if (tuoiMin != null) {
                query.setParameter("tuoiMin", tuoiMin);
            }
            if (tuoiMax != null) {
                query.setParameter("tuoiMax", tuoiMax);
            }

            return query.list();
        }
    }

    public static void main(String[] args) {
        GiangVienRepository repo = new GiangVienRepository();

        // Kiểm tra lấy danh sách giảng viên
        List<GiangVien> danhSach = repo.getAll();
        for (GiangVien gv : danhSach) {
            System.out.println(gv);
        }

        // Kiểm tra lấy một giảng viên theo ID
        System.out.println(repo.getOne(1L));
    }
}
