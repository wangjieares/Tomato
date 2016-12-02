package www.atomato.com.tomato.recall;

/**
 * Created by bpncool on 2/24/2016.
 */

import www.atomato.com.tomato.data.TodoSection;

/**
 * interface to listen changes in state of sections
 */
public interface TodoStateChangeListener {
    void onSectionStateChanged(TodoSection todoSection, boolean isOpen);
}