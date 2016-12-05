package www.atomato.com.tomato.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import www.atomato.com.tomato.activity.AddItemActivity;

/**
 * Created by wangj on 2016-11-29.
 */

public class RemindService extends BaseService {
    private PendingIntent mPendingIntent;
    private AlarmManager alarmManager;

    @Override
    public void onCreate() {
        super.onCreate();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, AddItemActivity.class);
        mPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        int time = intent.getIntExtra("time", 0) * 1000 * 6;//提醒时间
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, time, mPendingIntent);
        return null;
    }
}
