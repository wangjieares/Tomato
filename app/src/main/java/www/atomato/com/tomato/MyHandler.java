package www.atomato.com.tomato;


import android.content.Context;
import android.os.Handler;
import android.os.Message;

import www.atomato.com.tomato.constants.Constants;

/**
 * Created by wangj on 2016-11-23.
 */

public class MyHandler extends Handler {
    public Context context;

    public MyHandler(Context context) {
        this.context = context;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case Constants.CREATE_TODO:
                break;
            case Constants.DELETE_TODO:
                break;
            case Constants.UPDATE_TODO:
                break;
        }
    }
}
