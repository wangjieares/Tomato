package www.atomato.com.tomato.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import www.atomato.com.tomato.activity.AddItemActivity;
import www.atomato.com.tomato.utils.BaseService;

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

//        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        //读者可以修改此处的Minutes从而改变提醒间隔时间
//        //此处是设置每隔90分钟启动一次
//        //这是90分钟的毫秒数
//        int Minutes = 90*60*1000;
//        //SystemClock.elapsedRealtime()表示1970年1月1日0点至今所经历的时间
//        long triggerAtTime = SystemClock.elapsedRealtime() + Minutes;
//        //此处设置开启AlarmReceiver这个Service
//        Intent i = new Intent(this, AlarmReceiver.class);
//        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
//        //ELAPSED_REALTIME_WAKEUP表示让定时任务的出发时间从系统开机算起，并且会唤醒CPU。
//        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
//        return super.onStartCommand(intent, flags, startId);


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        int time = intent.getIntExtra("time", 0) * 1000 * 6;//提醒时间
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, time, mPendingIntent);
        return new MyBinder();
    }

    private class MyBinder extends Binder{
        public RemindService getService() {
            return RemindService.this;
        }
    }
}
