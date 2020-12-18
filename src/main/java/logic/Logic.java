package logic;

import persistence.Item;
import persistence.ItemDao;
import persistence.ToDoListApp;
import com.google.gson.Gson;

import java.util.List;

public class Logic {
    private final static Logic LOGIC = new Logic();
    private final ItemDao itemDao = ToDoListApp.getInstance();

    private Logic() {

    }

    public static Logic getInstance() {
        return LOGIC;
    }

    public Item createItem(String name, String desc, String done) {
        Item item = new Item();
        item.setName(name);
        item.setDescription(desc);
        item.setDone(done != null);
        return item;
    }

    public void save(Item item) {
        itemDao.save(item);
    }

    public void update(Item item) {
        itemDao.save(item);
    }

    public Item delete(Item item) {
        return itemDao.delete(item);
    }

    public List<Item> getAll() {
        return itemDao.getAll();
    }

    public Item getById(Integer id) {
        return itemDao.findByid(id);
    }

    public String getJsonTable(List items) {
        return new Gson().toJson(items);
    }

    public boolean isAuthorized(String login, String pwd) {
        return itemDao.isAuthorized(login, pwd);
    }
}
