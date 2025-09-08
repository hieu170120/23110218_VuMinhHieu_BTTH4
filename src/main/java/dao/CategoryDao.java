package dao;
import config.JPAConfig;
import jakarta.persistence.*;
import model.Category;
public class CategoryDao {
	void insert(Category category) {
		EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin();
			enma.persist(category);
			trans.commit();
		}catch(Exception e) {
			e.printStackTrace();
			trans.rollback();
			throw e;
		}finally {
			enma.close();
		}
	}
}
