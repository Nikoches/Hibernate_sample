import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.Item;
import persistence.ToDoListApp;
import persistence.User;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoListTest {
    private final SessionFactory sessionFactory = HibernateTestUtil.getSessionFactory();
    private final ToDoListApp todo = ToDoListApp.getInstance(sessionFactory);
    private final Session session = sessionFactory.openSession();
    private final static User user = new User("username",1,"pwd");
    @BeforeEach
    public void setUp() throws IOException {
        Transaction tx = session.beginTransaction();
        session.save(user);
        tx.commit();
        todo.save(new Item(1,"desc",true,"name",user));
    }
    @Test
    public void whenSaveItemAndFindOne() {
        assertEquals(todo.findByid(1),new Item(1,"desc",true,"name",user));
    }

    @Test
    public void whenSaveItemAndRemoveItem() {
        Item item1 = new Item(2,"desc1",true,"name1",user);
        todo.save(item1);
        List<Item> items = new LinkedList<>();
        items.add(new Item(1,"desc",true,"name",user));
        todo.delete(todo.findByid(2));
        assertEquals(items,todo.getAll());
    }
    @Test
    public void whenSaveAndUpdate() {
        Item item = new Item(1,"descUpd",false,"nameUpd",user);
        todo.update(new Item(1,"descUpd",false,"nameUpd",user));
        assertEquals(item,todo.findByid(1));
    }
}
