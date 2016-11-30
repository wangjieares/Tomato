package www.atomato.com.tomato.recall;

import android.view.View;

/**
 * Created by wangj on 2016-11-29.
 */
public interface BottomWindowListener {
    void stickClick(View view, int positon);

    void remindClick(View view, int positon);

    void markClick(View view, int positon);

    void editClick(View view, int positon);

    void deleteClick(View view, int positon);
}
