package www.atomato.com.tomato;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;

import www.atomato.com.tomato.activity.MainActivity;
import www.atomato.com.tomato.data.AdBehaior;
import www.atomato.com.tomato.data.YouMiSdk;


public class LaunchActivity extends Activity {
    LinearLayout mLinearLayout;
    private Handler mHandler = new Handler();
    private Intent mIntent;

    //延时执行

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        mLinearLayout = (LinearLayout)findViewById(R.id.activity_launch_linear) ;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(AdBehaior.LaunchAd()){
                    YouMiSdk youMiSdk = new YouMiSdk(LaunchActivity.this);
                    youMiSdk.initSplash(mLinearLayout);
                }else {
                    Intent intent = new Intent(LaunchActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
    }, 1000);//3000微妙后跳转

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
