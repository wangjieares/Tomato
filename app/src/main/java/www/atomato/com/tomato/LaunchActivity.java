package www.atomato.com.tomato;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import www.atomato.com.tomato.activity.MainActivity;
import www.atomato.com.tomato.data.AdBehaior;
import www.atomato.com.tomato.data.YouMiSdk;


public class LaunchActivity extends Activity {
    LinearLayout mLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        mLinearLayout = (LinearLayout)findViewById(R.id.activity_launch_linear) ;
        if(AdBehaior.LaunchAd()){
            YouMiSdk youMiSdk = new YouMiSdk(this);
            youMiSdk.initSplash(mLinearLayout);
        }else {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
