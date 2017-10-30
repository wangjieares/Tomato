package www.atomato.com.tomato.data;

/**
 * Created by Administrator on 2017/10/30.
 */

public class CardViewData {
    public CardViewData(String title, String content) {
        this.title = title;
        this.content = content;
    }

    private String title;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
