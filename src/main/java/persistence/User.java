package persistence;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Column(name = "name")
    private String name;
    @Column(name = "user_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "pass")
    private String pwd;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setId(int id) {
        this.id = id;
    }
}
