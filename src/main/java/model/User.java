package model;

import jakarta.persistence.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")  // Tên bảng trong cơ sở dữ liệu
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Tự động sinh giá trị cho id
	    @Column(name = "id", columnDefinition = "INT")  // Ánh xạ với cột 'id' kiểu INT
	    private int id;

	    @Column(name = "username", nullable = false, unique = true, columnDefinition = "VARCHAR(255)")  // Kiểu VARCHAR
	    private String userName;

	    @Column(name = "password", nullable = false, columnDefinition = "VARCHAR(255)")  // Kiểu VARCHAR
	    private String passWord;

	    @Column(name = "email", nullable = false, unique = true, columnDefinition = "VARCHAR(255)")  // Kiểu VARCHAR
	    private String email;

	    @Column(name = "full_name", columnDefinition = "VARCHAR(255)")  // Kiểu VARCHAR
	    private String fullName;

	    @Column(name = "phone", columnDefinition = "VARCHAR(15)")  // Kiểu VARCHAR
	    private String phone;

	    @Column(name = "avatar", columnDefinition = "VARCHAR(255)")  // Kiểu VARCHAR
	    private String avatar;

	    @Column(name = "role_id", columnDefinition = "INT")  // Kiểu INT
	    private int roleId;

	    // Thay đổi @Temporal(TemporalType.TIMESTAMP) thành LocalDateTime để phù hợp hơn với SQL Server
	    @Column(name = "created_date", columnDefinition = "DATETIME2")  // Kiểu DATETIME2 thay vì TIMESTAMP
	    private Date createdDate; // Hoặc java.time.LocalDateTime nếu bạn muốn sử dụng LocalDateTime

	    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<Category> categories;

	    // Constructors, Getters và Setters

	    public User() {}

	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public String getUserName() {
	        return userName;
	    }

	    public void setUserName(String userName) {
	        this.userName = userName;
	    }

	    public String getPassWord() {
	        return passWord;
	    }

	    public void setPassWord(String passWord) {
	        this.passWord = passWord;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getFullName() {
	        return fullName;
	    }

	    public void setFullName(String fullName) {
	        this.fullName = fullName;
	    }

	    public String getPhone() {
	        return phone;
	    }

	    public void setPhone(String phone) {
	        this.phone = phone;
	    }

	    public String getAvatar() {
	        return avatar;
	    }

	    public void setAvatar(String avatar) {
	        this.avatar = avatar;
	    }

	    public int getRoleId() {
	        return roleId;
	    }

	    public void setRoleId(int roleId) {
	        this.roleId = roleId;
	    }

	    public Date getCreatedDate() {
	        return createdDate;
	    }

	    public void setCreatedDate(Date createdDate) {
	        this.createdDate = createdDate;
	    }
}
