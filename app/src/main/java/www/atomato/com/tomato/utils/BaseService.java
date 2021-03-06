package www.atomato.com.tomato.utils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import www.atomato.com.tomato.utils.LogUtils;

/**
 * Created by wangj on 2016-11-29.
 */

public class BaseService extends Service{
    private String tag = getClass().getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.e(tag, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.e(tag, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        LogUtils.i(tag, "onRebind");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtils.i(tag, "onCreate");
        return super.onUnbind(intent);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.i(tag, "onCreate");
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i(tag,"onCreate");
    }
}
