package persistence;

import javax.persistence.*;

@Entity
@Table(name = "items")
public class Item {
    //Можно не указывать имя столбца, если совпадает с таблицей.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "description")
    private String description;
    @Column(name = "done")
    private boolean done;
    @Column(name = "name")
    private String name;

    public Item(Integer id, String description, boolean done, String name, User user) {
        this.id = id;
        this.description = description;
        this.done = done;
        this.name = name;
        this.user = user;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Item() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "Desc = " + description + "IsDone" + done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Item item = (Item) o;

        if (done != item.done) {
            return false;
        }
        if (!id.equals(item.id)) {
            return false;
        }
        if (!description.equals(item.description)) {
            return false;
        }
        if (!name.equals(item.name)) {
            return false;
        }
        return user.equals(item.user);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + (done ? 1 : 0);
        result = 31 * result + name.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }
}
