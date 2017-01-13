package www.atomato.com.tomato.recall;

import android.view.View;

import www.atomato.com.tomato.data.TodoSection;

/**
 * Created by lenovo on 2/23/2016.
 */
public interface ItemClickListener {
    void itemClicked(View item);
    void itemClicked(TodoSection todoSection);
    void ItemAddClick(View item);
    void ItemReminkClick(View item);
}
