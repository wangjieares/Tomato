package www.atomato.com.tomato.activity;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import www.atomato.com.tomato.R;
import www.atomato.com.tomato.utils.BaseActivity;
import www.atomato.com.tomato.view.CountDownTimerView;

/**
 * Created by wangj on 2016-11-25.
 */

public class CountProgressActivity extends Activity {
    private CountDownTimerView mCountDownTimerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_timer);
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.activity_count_timer_linear) ;
        mCountDownTimerView = (CountDownTimerView) findViewById(R.id.CountDownTimerView);
        mCountDownTimerView.setCountdownTime(1000*60*2);
        mCountDownTimerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCountDownTimerView.startCountDownTime(new CountDownTimerView.OnCountdownFinishListener() {
                    @Override
                    public void countdownFinished() {
//                        Toast.makeText(CountProgressActivity.this, "倒计时结束了--->该UI处理界面逻辑了", Toast.LENGTH_LONG).show();
                    }
                });
                /*Message message = Message.obtain();
                message.what = HANDLER_MESSAGE;
                handler.sendMessage(message);*/
            }
        });

//        BitmapFactory.decodeResource(getResources(),R.drawable.activity_count_timer_background)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            linearLayout.setBackground(getDrawable(R.drawable.activity_count_timer_background));
        }else {
            linearLayout.setBackground(getResources().getDrawable(R.drawable.activity_count_timer_background));
        }
    }
}
