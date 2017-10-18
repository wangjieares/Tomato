package www.atomato.com.tomato.utils;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by wangjie on 16-11-16.
 */

public class BaseActivity extends AppCompatActivity {
    public String tag = getClass().getSimpleName();

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.d(tag, tag + "===onStart");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d(tag, tag + "===onCreate");

    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.d(tag, tag + "===onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.d(tag, tag + "===onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.d(tag, tag + "===onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.d(tag, tag + "===onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.d(tag, tag + "===onDestroy");
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
