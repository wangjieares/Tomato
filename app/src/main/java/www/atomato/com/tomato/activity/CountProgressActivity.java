package www.atomato.com.tomato.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.atomato.com.tomato.R;
import www.atomato.com.tomato.utils.LogUtils;
import www.atomato.com.tomato.view.CountDownTimerView;

/**
 * Created by wangj on 2016-11-25.
 */

public class CountProgressActivity extends Activity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_timer);
        ButterKnife.bind(this);
        CountDownTimerView.setCountdownTime(getIntent().getIntExtra("todo_time", 35) * 1000 * 60);
        activityCountTimerImageButtonRestart.setSelected(true);
        activityCountTimerImageButtonComputer.setSelected(true);
        //设置背景
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            activityCountTimerLinear.setBackground(getDrawable(R.drawable.activity_count_timer_background));
//        } else {
//            activityCountTimerLinear.setBackground(getResources().getDrawable(R.drawable.activity_count_timer_background));
//        }
        //开始计时器
        CountDownTimerView.startCountDownTime(new CountDownTimerView.OnCountdownFinishListener() {
            @Override
            public void performFinished() {
                LogUtils.e(tag, "countdownFinished === done");
                if (mIsNext) {
                    CountDownTimerView.setCountdownTime(getIntent().getIntExtra("todo_time", 35) * 1000 * 60);
                }
            }
        });
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
                } else {
                    mIsNext = false;
                    view.setSelected(true);
                }
                break;
            case R.id.activity_count_timer_image_button_stop:
                if (view.isSelected()) {
                    view.setSelected(false);
                } else {
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
                } else {
                    if (mWakeLock.isHeld()) {
                        mWakeLock.release(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK);
                    }
                    view.setSelected(true);
                }
                break;
        }

    }
}
