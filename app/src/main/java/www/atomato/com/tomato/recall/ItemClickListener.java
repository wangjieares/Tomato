package www.atomato.com.tomato.recall;

import android.view.View;

/**
 * Created by lenovo on 2/23/2016.
 */
public interface ItemClickListener {
    void itemClicked(View item);
    void itemClicked(TodoSection todoSection);
}
