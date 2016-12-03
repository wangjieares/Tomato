package www.atomato.com.tomato;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import www.atomato.com.tomato.activity.MainActivity;
import www.atomato.com.tomato.utils.LogUtils;
import www.atomato.com.tomato.utils.RoundImageView;


public class LaunchActivity extends Activity {
    public RoundImageView roundImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
//        roundImageView = (RoundImageView) findViewById(R.id.activity_launch_image);
//        startAnimation();

//        ImageView roundImageView = (ImageView) findViewById(R.id.activity_launch_image);
//        int[] location = new int[2];
//        roundImageView.getLocationOnScreen(location);
//        float formX = location[0];
//        float formY = location[1];
//        float toX = location[0];
//        float toY = location[1] - 500;
//        Animation animation = new TranslateAnimation(formX, formY, toX, toY);
////        Animation animation = new TranslateAnimation(10, 20, 100, 200);
//        animation.setInterpolator(new LinearInterpolator());
//        animation.setDuration(1000);
//        animation.setFillAfter(true);
//        roundImageView.setAnimation(animation);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        timer.schedule(timerTask, 1000);
    }

//    public float formX;
//    public float formY;
//    private void startAnimation() {
//        int[] location = new int[2];
//        roundImageView.getLocationOnScreen(location);
//        formX = location[0];
//        formY = location[1];
//        ValueAnimator valueAnimator = new ValueAnimator();
//        valueAnimator.setDuration(1000);
//        valueAnimator.setFloatValues(0, 1.0f);
//        valueAnimator.setInterpolator(new LinearInterpolator());//匀速
//        valueAnimator.setRepeatCount(0);//表示不循环，-1表示无限循环
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                float seed = (float) valueAnimator.getAnimatedValue();
//                roundImageView.setTranslationX(formX);
//                roundImageView.setTranslationY(formY-seed*300);
//            }
//        });
//        valueAnimator.start();
//    }
}
