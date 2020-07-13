package persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.function.Function;

public class ToDoListApp implements ItemDao {
    private final static ToDoListApp toDoListApp = new ToDoListApp();
    private final SessionFactory factory = HibernateUtils.getSessionFactory();
    private ToDoListApp() {
    }

    public static ToDoListApp getInstance() {
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
            throw e;
        } finally {
            session.close();
        }
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
        Session session = HibernateUtils.getSessionFactory().openSession();
        return session.get(Item.class, id);
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
}
