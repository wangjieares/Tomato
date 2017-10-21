package www.atomato.com.tomato.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import www.atomato.com.tomato.R;
import www.atomato.com.tomato.data.AdBehaior;
import www.atomato.com.tomato.data.YouMiSdk;
import www.atomato.com.tomato.utils.BaseActivity;
import www.atomato.com.tomato.utils.ScreenUtils;

/**
 * Created by wangjie on 12/3/2016.
 */
public class AboutActivity extends BaseActivity implements View.OnClickListener{
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ScreenUtils.setMainColor(this,getResources().getColor(R.color.colorPrimaryDark));
        initView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        initAd();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.activity_about_toolbar);
        toolbar.setNavigationIcon(R.mipmap.statistics_activity_menu_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.activity_about_collapsing_toolbar_layout);
        collapsingToolbarLayout.setTitle("关于番茄钟");
    }

    private void initAd() {
        if(AdBehaior.aboutAd()){
            YouMiSdk youMiSdk = new YouMiSdk(this);
            youMiSdk.setBanner((LinearLayout)findViewById(R.id.activity_about_linear));
        }
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
