package www.atomato.com.tomato.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.atomato.com.tomato.R;
import www.atomato.com.tomato.constants.Constants;
import www.atomato.com.tomato.data.ToDoData;
import www.atomato.com.tomato.sqlite.ViewSQLite;
import www.atomato.com.tomato.utils.LogUtils;
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
    long lastTime;
    private String todoTitle;
    private int todoTime;
    private ViewSQLite viewSQLite;

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
        //设置背景
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            activityCountTimerLinear.setBackground(getDrawable(R.drawable.activity_count_timer_background));
//        } else {
//            activityCountTimerLinear.setBackground(getResources().getDrawable(R.drawable.activity_count_timer_background));
//        }
        //开始计时器
        CountDownTimerView.startCountDownTime(this);
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
                ToastUtils.show(this, "退出请再按一次back！");
                lastTime = System.currentTimeMillis();
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
                if (!quit) {
                    ToastUtils.show(this, "退出请再按一次！");
                    lastTime = System.currentTimeMillis();
                    quit = true;
                } else {
                    if (SystemClock.currentThreadTimeMillis() - lastTime < 1000) {
                        finish();
                    } else {
                        ToastUtils.show(this, "退出请再按一次！");
                        lastTime = System.currentTimeMillis();
                    }
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

    private boolean isDesotry() {
        try (Cursor cursor = viewSQLite.query(Constants.TABLE_NAME, null, "todo_title=?", new String[]{todoTitle}, null, null, null)) {
            cursor.moveToNext();
            int todo_destory = cursor.getInt(cursor.getColumnIndex("todo_destory"));
            int todo_plan_time = cursor.getInt(cursor.getColumnIndex("todo_plan_time"));
            int todo_total_time = cursor.getInt(cursor.getColumnIndex("todo_total_time"));
            if (todo_destory == 1 && todo_plan_time == todo_total_time) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void performFinished() {
        viewSQLite = new ViewSQLite(this);
        if (mIsNext) {
            if (!isDesotry()) {
                CountDownTimerView.setCountdownTime(getIntent().getIntExtra("todo_time", 35) * 1000 * 60);
            } else {
                viewSQLite.delete(todoTitle);
            }
        }
        ContentValues contentValues = new ContentValues();
        try (Cursor cursor = viewSQLite.query(Constants.TABLE_NAME, null, "todo_title=?", new String[]{todoTitle}, null, null, null)) {
            cursor.moveToNext();
            int totalTIme = cursor.getInt(cursor.getColumnIndex("todo_total_time"));
            contentValues.put("todo_total_time", totalTIme + todoTime);//总时间 之前时间+当前完成时间
            viewSQLite.update(Constants.TABLE_NAME, contentValues, "todo_title=?", new String[]{todoTitle});
            if (isDesotry()) {
                viewSQLite.delete(todoTitle);
            }
        } finally {
            viewSQLite.closedb();
        }
    }
}
