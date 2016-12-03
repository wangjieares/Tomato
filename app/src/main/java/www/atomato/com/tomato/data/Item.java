package www.atomato.com.tomato.data;

/**
 * 代办集Item
 */
public class Item {

    private final int id;
    private final String name;

    public Item(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
