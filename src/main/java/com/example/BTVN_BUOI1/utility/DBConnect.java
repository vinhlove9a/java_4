package com.example.BTVN_BUOI1.utility;

import com.example.BTVN_BUOI1.entity.GiangVien;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class DBConnect {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();

                // Cấu hình Hibernate
                settings.put(Environment.DIALECT, "org.hibernate.dialect.SQLServer2016Dialect");
                settings.put(Environment.DRIVER, "com.microsoft.sqlserver.jdbc.SQLServerDriver");
                settings.put(Environment.URL, "jdbc:sqlserver://localhost:1433;databaseName=BTVNBuoi7;encrypt=true;trustServerCertificate=true;useUnicode=true;characterEncoding=UTF-8;");
                settings.put(Environment.USER, "sa");
                settings.put(Environment.PASS, "123");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "validate"); // Chỉ xác thực schema, tránh mất dữ liệu

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(GiangVien.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                System.err.println("Lỗi khởi tạo Hibernate: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static void closeSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    public static void main(String[] args) {
        System.out.println(getSessionFactory()); // Kiểm tra kết nối
        closeSessionFactory(); // Đóng Hibernate khi test xong
    }
}
