package persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.function.Function;

public class ToDoListApp implements ItemDao {
    private static ToDoListApp toDoListApp;
    private static SessionFactory factory = HibernateUtils.getSessionFactory();

    private ToDoListApp(SessionFactory sessionFactory) {
        factory = sessionFactory;
    }

    private ToDoListApp() {

    }

    public static ToDoListApp getInstance() {
        if (toDoListApp == null) {
            toDoListApp = new ToDoListApp();
        }
        return toDoListApp;
    }

    public static ToDoListApp getInstance(SessionFactory sessionFactory) {
        if (toDoListApp == null) {
            toDoListApp = new ToDoListApp(sessionFactory);
        }
        return toDoListApp;
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = factory.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public Item save(Item item) {
        return this.tx(session -> {
            session.save(item);
            return item;
        });
    }

    @Override
    public Item findByid(Integer id) {
        return this.tx(session -> session.get(Item.class, id));
    }

    @Override
    public Item update(Item item) {
        return this.tx(session -> {
            session.update(item);
            return item;
        });
    }

    @Override
    public Item delete(Item id) {
        return this.tx(session -> {
            session.delete(id);
            return id;
        });
    }

    @Override
    public List<Item> getAll() {
        return this.tx(
                session -> session.createQuery("From persistence.Item").list()
        );
    }

    public List<Item> getByExcecuted(int i) {
        if (i == 1) {
            return getAll();
        } else if (i == 2) {
            return this.tx(
                    session -> session.createQuery("FROM persistence.Item e WHERE e.done = true").list()
            );
        } else if (i == 3) {
            return this.tx(
                    session -> session.createQuery("FROM persistence.Item e WHERE e.done = false").list()
            );
        }
        return this.tx(
                session -> session.createQuery("FROM persistence.Item e WHERE e.done = true").list()
        );
    }
    public User getUser(Integer id) {
        return this.tx(session -> session.get(User.class, id));
    }
    public boolean isAuthorized(String login, String password) {
        User user = this.tx(session -> session.get(User.class, login));
        return user.getPwd().equals(password);
    }
}
