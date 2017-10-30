package www.atomato.com.tomato.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import www.atomato.com.tomato.utils.ToastUtils;

/**
 * Created by wangj on 2016-11-29.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ToastUtils.show(context,"倒是见了！！");
    }
}
