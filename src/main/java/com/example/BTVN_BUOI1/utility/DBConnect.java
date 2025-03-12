package com.example.BTVN_BUOI1.utility;

import com.example.BTVN_BUOI1.entity.GiangVien;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class DBConnect {
    private static final SessionFactory FACTORY;

    static {
        try {
            Configuration configuration = new Configuration();

            // Cấu hình kết nối SQL Server
            Properties settings = new Properties();
            settings.put(Environment.DIALECT, "org.hibernate.dialect.SQLServer2016Dialect");
            settings.put(Environment.DRIVER, "com.microsoft.sqlserver.jdbc.SQLServerDriver");
            settings.put(Environment.URL, "jdbc:sqlserver://localhost:1433;databaseName=BTVNBuoi7;encrypt=true;trustServerCertificate=true;");
            settings.put(Environment.USER, "sa");
            settings.put(Environment.PASS, "123");
            settings.put(Environment.SHOW_SQL, "true"); // Hiển thị câu lệnh SQL
            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            settings.put(Environment.HBM2DDL_AUTO, "update"); // Tạo bảng nếu chưa có

            configuration.setProperties(settings);

            // Đăng ký entity
            configuration.addAnnotatedClass(GiangVien.class);

            // Tạo SessionFactory
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            FACTORY = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Lỗi khởi tạo Hibernate: " + e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return FACTORY;
    }

    public static void main(String[] args) {
        System.out.println(getSessionFactory()); // Kiểm tra kết nối
    }
}
