package www.atomato.com.tomato.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_timer);
        ButterKnife.bind(this);
        CountDownTimerView.setCountdownTime(1000 * 60 * 2);

//        BitmapFactory.decodeResource(getResources(),R.drawable.activity_count_timer_background)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activityCountTimerLinear.setBackground(getDrawable(R.drawable.activity_count_timer_background));
        } else {
            activityCountTimerLinear.setBackground(getResources().getDrawable(R.drawable.activity_count_timer_background));
        }
    }

    @OnClick({R.id.activity_count_timer_show, R.id.CountDownTimerView, R.id.activity_count_timer_image_button_restart, R.id.activity_count_timer_image_button_stop, R.id.activity_count_timer_image_button_computer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.CountDownTimerView:
                CountDownTimerView.startCountDownTime(new CountDownTimerView.OnCountdownFinishListener() {
                    @Override
                    public void countdownFinished() {
                        LogUtils.e(tag, "countdownFinished === done");

                    }
                });
                break;
            case R.id.activity_count_timer_image_button_restart:
                if (view.isSelected()) {
                    LogUtils.e(tag,view.isSelected()+"");
                    view.setSelected(false);
                } else {
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
                if (view.isSelected()) {
                    view.setSelected(false);
                } else {
                    view.setSelected(true);
                }
                break;
        }
    }
}
