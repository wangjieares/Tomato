package www.atomato.com.tomato.data;

/**
 * TodoSection
 */
public class TodoSection {

    private final String name;

    public boolean isExpanded;

    public TodoSection(String name) {
        this.name = name;
        isExpanded = false;//默认不展开
    }

    public String getName() {
        return name;
    }
}
