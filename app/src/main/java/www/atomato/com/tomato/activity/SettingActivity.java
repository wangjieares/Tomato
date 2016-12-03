package www.atomato.com.tomato.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import www.atomato.com.tomato.R;
import www.atomato.com.tomato.utils.BaseActivity;
import www.atomato.com.tomato.utils.ScreenUtils;

/**
 * Created by wangjie on 12/3/2016.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_bar);
        ScreenUtils.setColor(this,getResources().getColor(R.color.toolBar));
        Toolbar toolbar = (Toolbar) findViewById(R.id.setting_toolbar);
        toolbar.setTitle(R.string.setting_title);
        toolbar.setNavigationIcon(R.mipmap.statistics_activity_menu_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
