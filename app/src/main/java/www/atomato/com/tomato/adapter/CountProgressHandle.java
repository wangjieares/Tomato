package www.atomato.com.tomato.adapter;

import android.app.Activity;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by admin on 2017/10/20.
 */

public class CountProgressHandle extends Handler {
    private final WeakReference<Activity> mActivity;
    public CountProgressHandle(Activity activity){
        mActivity = new WeakReference<Activity>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
    }
}
