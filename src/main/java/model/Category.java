package model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "categories")
@NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c")
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private int id;

    @Column(name = "name", nullable = false, length = 255, unique = true, columnDefinition = "NVARCHAR(255)")
    private String name;

    @Column(name = "icon", nullable = true, length = 255, columnDefinition = "NVARCHAR(255)")
    private String icon;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", insertable = false, updatable = false, nullable = true)
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    public Category() {}

    public Category(int id, String name, String icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }

    public Date getCreatedDate() { return createdDate; }
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", createdDate=" + createdDate +
                ", user=" + (user != null ? user.getId() : null) +
                '}';
    }

    public void setDescription(String description) {
        // TODO Auto-generated method stub
    }
}
