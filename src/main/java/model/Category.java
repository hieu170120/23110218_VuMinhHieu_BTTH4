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

@Entity
@Table(name = "categories")  // Tên bảng trong cơ sở dữ liệu
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Tự động sinh giá trị cho id
    @Column(name = "id", columnDefinition = "INT")  // Kiểu INT
    private int id;

    @Column(name = "name", nullable = false, length = 255, unique = true, columnDefinition = "NVARCHAR(255)")  // Kiểu NVARCHAR(255)
    private String name;

    @Column(name = "icon", nullable = true, length = 255, columnDefinition = "NVARCHAR(255)")  // Kiểu NVARCHAR(255)
    private String icon;

    @Temporal(TemporalType.TIMESTAMP)  // Kiểu TIMESTAMP
    @Column(name = "created_date", columnDefinition = "TIMESTAMP")  // Kiểu TIMESTAMP
    private Date createdDate;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)  // Foreign key to user table
    private User user;
    // Constructor không tham số
    public Category() {}

    // Constructor có tham số
    public Category(int id, String name, String icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    // Getter và Setter cho id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter và Setter cho name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter và Setter cho icon
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    // Getter và Setter cho createdDate
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }

	public void setDescription(String description) {
		// TODO Auto-generated method stub
		
	}
}
