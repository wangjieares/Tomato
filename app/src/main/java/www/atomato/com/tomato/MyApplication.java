package www.atomato.com.tomato;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;


/**
 * Created by wangjie on 12/3/2016.
 */

public class MyApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
