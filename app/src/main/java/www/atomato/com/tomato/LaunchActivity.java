package www.atomato.com.tomato;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;

import java.util.Timer;
import java.util.TimerTask;

import www.atomato.com.tomato.activity.MainActivity;
import www.atomato.com.tomato.utils.RoundImageView;


public class LaunchActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        RoundImageView roundImageView = (RoundImageView) findViewById(R.id.activity_launch_image);
        int[] location = new int[2];
        roundImageView.getLocationOnScreen(location);
        float formX = location[0];
        float formY = location[1];
        float toX = location[0];
        float toY = location[1]-300;
        Animation animation = new TranslateAnimation(formX, formY, toX, toY);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(1000);
        animation.setFillAfter(true);
        roundImageView.setAnimation(animation);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        timer.schedule(timerTask, 1200);
    }
}
