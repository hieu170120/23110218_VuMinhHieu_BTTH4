package service.impl;

import java.util.Date;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import model.User;
import service.UserService;

public class UserServiceImpl implements UserService {
	UserDao userDao = new UserDaoImpl();
    @Override
    public User login(String username, String password) {
        User user = userDao.get(username);
        if (user != null && password.equals(user.getPassWord())) {
            return user;
        }
        return null;
    }

    @Override
    public User get(String username) {
        return userDao.get(username);
    }

    @Override
    public void insert(User user) {
        userDao.insert(user);
    }

    @Override
    public boolean checkExistEmail(String email) {
        return userDao.checkExistEmail(email);
    }

    @Override
    public boolean checkExistUsername(String username) {
        return userDao.checkExistUsername(username);
    }

    @Override
    public boolean checkExistPhone(String phone) {
        return userDao.checkExistPhone(phone);
    }

    @Override
    public User getByEmail(String email) {   // thêm mới
        return userDao.getByEmail(email);
    }

	@Override
	public boolean register(String email, String password, String username, String fullname, String phone) {
		try {
			// Kiểm tra email, username, phone đã tồn tại chưa
			if (checkExistEmail(email)) {
				return false; // Email đã tồn tại
			}
			if (checkExistUsername(username)) {
				return false; // Username đã tồn tại
			}
			if (checkExistPhone(phone)) {
				return false; // Phone đã tồn tại
			}
			
			// Tạo user mới
			User user = new User();
			user.setUserName(username);
			user.setPassWord(password);
			user.setEmail(email);
			user.setFullName(fullname);
			user.setPhone(phone);
			user.setRoleId(3); // Default role là user
			user.setCreatedDate(new Date());
			
			// Lưu vào database
			insert(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
