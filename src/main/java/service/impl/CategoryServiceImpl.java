package service.impl;

import java.util.List;

import dao.CategoryDao;
import dao.impl.CategoryDaoImpl;
import model.Category;
import model.User;
import service.inft.ICategoryService;

public class CategoryServiceImpl implements ICategoryService {
    private final CategoryDao dao = new CategoryDaoImpl();

    @Override
    public void insert(Category c) {
        dao.insert(c);
    }

    @Override
    public void update(Category c) {
        dao.update(c);
    }

    @Override
    public void delete(int id) {
        dao.delete(id);
    }

    @Override
    public Category findById(int id) {
        return dao.findById(id);
    }

//    @Override
//    public List<Category> findAll() {
//        return dao.findAll();
//    }

//    @Override
//    public List<Category> search(String keyword) {
//        return dao.search(keyword);
//    }

    @Override
    public List<Category> findAll() {
        return dao.findAll();
    }

}
