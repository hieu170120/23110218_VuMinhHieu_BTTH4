package service;

import model.User;

public interface UserService {
	User login(String username, String password);

	User get(String username);
	User getByEmail(String email);

	void insert(User user);

	boolean register(String email, String password, String username, String fullname, String phone);

	boolean checkExistEmail(String email);

	boolean checkExistUsername(String username);

	boolean checkExistPhone(String phone);
}
