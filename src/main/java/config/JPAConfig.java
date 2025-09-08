package config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAConfig {
    private static final String PERSISTENCE_UNIT_NAME = "dataSource";  // Sử dụng tên persistence unit của bạn
    private static EntityManagerFactory emf;

    // Khởi tạo EntityManagerFactory một lần
    static {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    // Trả về EntityManager từ EntityManagerFactory
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Đóng EntityManagerFactory khi ứng dụng kết thúc
    public static void shutdown() {
        if (emf != null) {
            emf.close();
        }
    }
}
