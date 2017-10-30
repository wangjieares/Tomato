package www.atomato.com.tomato.activity;

import android.app.Activity;
import android.os.Bundle;

import www.atomato.com.tomato.R;
import www.atomato.com.tomato.utils.ScreenUtils;

/**
 * Created by wangjie on 12/24/2016.
 */

public class RelaxActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relax);
        ScreenUtils.setMainColor(this, getResources().getColor(R.color.toolBar));
    }
}
