package www.atomato.com.tomato.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.atomato.com.tomato.R;
import www.atomato.com.tomato.constants.Constants;
import www.atomato.com.tomato.service.CountProgressService;
import www.atomato.com.tomato.sqlite.ViewSQLite;
import www.atomato.com.tomato.utils.LogUtils;
import www.atomato.com.tomato.utils.SoundUtils;
import www.atomato.com.tomato.utils.ToastUtils;
import www.atomato.com.tomato.view.CountDownTimerView;

/**
 * Created by wangj on 2016-11-25.
 */

public class CountProgressActivity extends Activity implements www.atomato.com.tomato.view.CountDownTimerView.OnCountdownFinishListener {
    @BindView(R.id.activity_count_timer_show)
    TextView activityCountTimerShow;
    @BindView(R.id.CountDownTimerView)
    www.atomato.com.tomato.view.CountDownTimerView CountDownTimerView;
    @BindView(R.id.activity_count_timer_image_button_computer)
    ImageButton activityCountTimerImageButtonComputer;
    @BindView(R.id.activity_count_timer_image_button_restart)
    ImageButton activityCountTimerImageButtonRestart;
    @BindView(R.id.activity_count_timer_image_button_stop)
    ImageButton activityCountTimerImageButtonStop;
    @BindView(R.id.activity_count_timer_linear)
    LinearLayout activityCountTimerLinear;
    private String tag = getClass().getSimpleName();


    private boolean mIsNext = false;
    private boolean quit = false;
    long countdownTime;
    long lastTime;
    private String todoTitle;
    private int todoTime;
    private ViewSQLite viewSQLite;
    private CountProgressService countProgressService;
    private ServiceConnection connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_timer);
        ButterKnife.bind(this);
        todoTitle = getIntent().getStringExtra("todo_title");
        todoTime = getIntent().getIntExtra("todo_time", 35);
        CountDownTimerView.setCountdownTime(todoTime * 1000 * 60);
        activityCountTimerImageButtonRestart.setSelected(true);
        activityCountTimerImageButtonComputer.setSelected(true);
        activityCountTimerImageButtonStop.setSelected(true);
        Intent intent = new Intent(this, CountProgressService.class);
        Bundle bundle = new Bundle();//数据
        bundle.putInt("todo_time",todoTime);
        intent.putExtras(bundle);
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                LogUtils.e(tag,"onServiceConnected success");
                countProgressService = ((CountProgressService.MyBinder) service).getService();
                countProgressService.setActivity(CountProgressActivity.this);
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {
                LogUtils.e(tag,"onServiceConnected error");
            }
        };
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        //设置背景
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            activityCountTimerLinear.setBackground(getDrawable(R.drawable.activity_count_timer_background));
//        } else {
//            activityCountTimerLinear.setBackground(getResources().getDrawable(R.drawable.activity_count_timer_background));
//        }
        //开始计时器
//        CountDownTimerView.startCountDownTime(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (!quit) {
            ToastUtils.show(this, "退出请再按一次back！");
            lastTime = System.currentTimeMillis();
            quit = true;
        } else {
            if (SystemClock.currentThreadTimeMillis() - lastTime < 1000) {
                super.onBackPressed();
            } else {
                lastTime = System.currentTimeMillis();
                SoundUtils.stopSound();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.activity_count_timer_show, R.id.CountDownTimerView, R.id.activity_count_timer_image_button_restart, R.id.activity_count_timer_image_button_stop, R.id.activity_count_timer_image_button_computer})
    public void onClick(View view) {
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        switch (view.getId()) {
            case R.id.CountDownTimerView:
                break;
            case R.id.activity_count_timer_image_button_restart:
                if (view.isSelected()) {
                    LogUtils.e(tag, view.isSelected() + "");
                    mIsNext = true;
                    view.setSelected(false);
                    ToastUtils.show(this, "开启自动循环！");
                } else {
                    mIsNext = false;
                    view.setSelected(true);
                    ToastUtils.show(this, "关闭自动循环！");
                }
                break;
            case R.id.activity_count_timer_image_button_stop:
                if (view.isSelected()) {
                    CountDownTimerView.getCountDownTimer().cancel();
                    countdownTime=CountDownTimerView.getCountdownTime();
                    CountDownTimerView.stopTime();
                    SoundUtils.pauseSound();
                    view.setSelected(false);
                } else {
                    CountDownTimerView.resumeTime();
                    SoundUtils.restartSound();
                    view.setSelected(true);
                }
                break;
            case R.id.activity_count_timer_image_button_computer:
                /**
                 *01.PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
                 *02.PowerManager.WakeLock mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
                 *03.// in onResume() call
                 *04.
                 *05.mWakeLock.acquire();
                 *06.// in onPause() call
                 *07.mWakeLock.release();
                 **/
                if (view.isSelected()) {
                    view.setSelected(false);
                    mWakeLock.setReferenceCounted(false);
                    mWakeLock.acquire();
                    ToastUtils.show(this, "开启屏幕常亮！");
                } else {
                    if (mWakeLock.isHeld()) {
                        mWakeLock.release(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK);
                        ToastUtils.show(this, "关闭屏幕常亮！");
                    }
                    view.setSelected(true);
                }
                break;
        }

    }

    //判断是不是需要继续执行
    private boolean isDesotry() {
        try (Cursor cursor = viewSQLite.query(Constants.TABLE_NAME, null, "todo_title=?", new String[]{todoTitle}, null, null, null)) {
            cursor.moveToNext();
//            if(cursor.moveToFirst()){
//                int todo_destory = cursor.getInt(cursor.getColumnIndex("todo_destory"));
//                int todo_plan_time = cursor.getInt(cursor.getColumnIndex("todo_plan_time"));
//                int todo_total_time = cursor.getInt(cursor.getColumnIndex("todo_total_time"));
//                return (todo_destory == 0) && (todo_plan_time == todo_total_time);//0是不销毁 执行个数等于总个数就销毁
//            }
            return false;
        }
    }
    @Override
    public void performFinished() {
        SoundUtils.stopSound();
        LogUtils.e(tag, "performFinished");
        viewSQLite = new ViewSQLite(this);
        if (mIsNext) {
            LogUtils.e(tag, isDesotry() + "");
            if (!isDesotry()) {
                SoundUtils.playSounds(this, R.raw.minute, 1, -1);
                CountDownTimerView.onRestartAni(this, todoTime * 1000 * 60);
                CountDownTimerView.setNext(true);
                LogUtils.e(tag, "继续执行" + "");
            } else {
                CountDownTimerView.setNext(false);
                LogUtils.e(getClass().getName(), "停止执行" + "");
                viewSQLite.delete(todoTitle);
            }
        }
        ContentValues contentValues = new ContentValues();
        try (Cursor cursor = viewSQLite.query(Constants.TABLE_NAME, null, "todo_title=?", new String[]{todoTitle}, null, null, null)) {
            cursor.moveToNext();
            int totalTime = cursor.getInt(cursor.getColumnIndex("todo_total_time"));
            int totalNum = cursor.getInt(cursor.getColumnIndex("todo_total_num"));
            contentValues.put("todo_total_time", totalTime + todoTime);//总时间 之前时间+当前完成时间
            contentValues.put("todo_total_num", totalNum + 1);//总时间 之前个数+1
            viewSQLite.update(Constants.TABLE_NAME, contentValues, "todo_title=?", new String[]{todoTitle});
//            if (isDesotry()) {
//                viewSQLite.delete(todoTitle);
//            }
        } finally {
            viewSQLite.closedb();
        }
    }

    public www.atomato.com.tomato.view.CountDownTimerView getCountDownTimerView(){
        return CountDownTimerView;
    }
}
