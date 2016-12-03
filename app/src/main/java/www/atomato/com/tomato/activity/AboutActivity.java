package www.atomato.com.tomato.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import www.atomato.com.tomato.R;
import www.atomato.com.tomato.data.GoogleAdBehaior;
import www.atomato.com.tomato.utils.BaseActivity;

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
        initView();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.activity_about_toolbar);
        toolbar.setNavigationIcon(R.mipmap.statistics_activity_menu_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.activity_about_collapsing_toolbar_layout);
        collapsingToolbarLayout.setTitle("关于番茄钟");

        initAd();
    }

    private void initAd() {
        if(GoogleAdBehaior.aboutAd()){
            AdView mAdView = (AdView)findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
