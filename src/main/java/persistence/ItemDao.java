package persistence;

import java.util.List;

public interface ItemDao {

    Item save(Item item);

    Item findByid(Integer id);

    Item update(Item item);

    Item delete(Item id);

    List<Item> getAll();
    boolean isAuthorized(String login, String pwd);
}
