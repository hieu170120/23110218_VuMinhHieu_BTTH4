import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
//import jakarta.ejb.Stateless;
//import jakarta.annotation.PostConstruct;

public class DatabaseConnectionTest {

    @PersistenceContext(unitName = "myPersistenceUnit")
    private EntityManager entityManager;

    public void testConnection() {
        try {
            // Thực hiện một truy vấn đơn giản để kiểm tra kết nối cơ sở dữ liệu
            entityManager.createQuery("SELECT 1").getResultList();
            System.out.println("Kết nối cơ sở dữ liệu thành công!");
        } catch (Exception e) {
            System.err.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }
}
