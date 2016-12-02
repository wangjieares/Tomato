package www.atomato.com.tomato.data;

/**
 * Created by bpncool on 2/23/2016.
 */
public class TodoSection {

    private final String name;

    public boolean isExpanded;

    public TodoSection(String name) {
        this.name = name;
        isExpanded = true;
    }

    public String getName() {
        return name;
    }
}
