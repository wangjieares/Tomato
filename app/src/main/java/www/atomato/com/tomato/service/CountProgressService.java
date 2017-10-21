package www.atomato.com.tomato.service;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import www.atomato.com.tomato.R;
import www.atomato.com.tomato.activity.CountProgressActivity;
import www.atomato.com.tomato.adapter.CountProgressHandle;
import www.atomato.com.tomato.utils.LogUtils;
import www.atomato.com.tomato.utils.SoundUtils;


/**
 * Created by admin on 2017/10/20.
 */

public class CountProgressService extends BaseService{
    private static CountProgressHandle mHandler;//CountDownTimer数据更新
    private CountProgressActivity activity;
    private Bundle bundle;
    private String tag = getClass().getSimpleName();
    private MyBinder myBinder = new MyBinder();
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.e(tag,"onCreate()");
    }
    public void setActivity(CountProgressActivity activity) {
        this.activity = activity;
        mHandler = new CountProgressHandle(activity);
        activity.getCountDownTimerView().startCountDownTime(activity);
        LogUtils.e(tag,"setActivity");
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.e(tag,"onBind");
        bundle = intent.getExtras();
        return myBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        stopSelf();
        SoundUtils.stopSound();
        return super.onUnbind(intent);
    }

    public class MyBinder extends Binder {
        public CountProgressService getService() {
            return CountProgressService.this;
        }
    }
//
//    @Override
//    public void performFinished() {
//        SoundUtils.stopSound();
//        LogUtils.e(tag, "performFinished");
//        viewSQLite = new ViewSQLite(this);
//        if (activity.getmIsNext()) {
//            LogUtils.e(tag, isDesotry() + "");
//            if (!isDesotry()) {
//                SoundUtils.playSounds(this, R.raw.minute, 1, -1);
//                activity.getCountDownTimerView().onRestartAni(this, activity.getTodoTime() * 1000 * 60);
//                LogUtils.e(tag, "继续执行" + "");
//            } else {
//                LogUtils.e(getClass().getName(), "停止执行" + "");
//                viewSQLite.delete(activity.getTodoTitle());
//            }
//        }
//        ContentValues contentValues = new ContentValues();
//        try (Cursor cursor = viewSQLite.query(Constants.TABLE_NAME, null, "todo_title=?", new String[]{activity.getTodoTitle()}, null, null, null)) {
//            cursor.moveToNext();
//            int totalTime = cursor.getInt(cursor.getColumnIndex("todo_total_time"));
//            int totalNum = cursor.getInt(cursor.getColumnIndex("todo_total_num"));
//            contentValues.put("todo_total_time", totalTime + activity.getTodoTime());//总时间 之前时间+当前完成时间
//            contentValues.put("todo_total_num", totalNum + 1);//总时间 之前个数+1
//            viewSQLite.update(Constants.TABLE_NAME, contentValues, "todo_title=?", new String[]{activity.getTodoTitle()});
////            if (isDesotry()) {
////                viewSQLite.delete(todoTitle);
////            }
//        } finally {
//            viewSQLite.closedb();
//        }
//    }
//    //判断是不是需要继续执行
//    private boolean isDesotry() {
//        try (Cursor cursor = viewSQLite.query(Constants.TABLE_NAME, null, "todo_title=?", new String[]{activity.getTodoTitle()}, null, null, null)) {
//            cursor.moveToNext();
////            if(cursor.moveToFirst()){
////                int todo_destory = cursor.getInt(cursor.getColumnIndex("todo_destory"));
////                int todo_plan_time = cursor.getInt(cursor.getColumnIndex("todo_plan_time"));
////                int todo_total_time = cursor.getInt(cursor.getColumnIndex("todo_total_time"));
////                return (todo_destory == 0) && (todo_plan_time == todo_total_time);//0是不销毁 执行个数等于总个数就销毁
////            }
//            return false;
//        }
//    }
}
