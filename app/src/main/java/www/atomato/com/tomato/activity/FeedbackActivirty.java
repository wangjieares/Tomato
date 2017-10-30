package www.atomato.com.tomato.activity;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import java.util.ArrayList;

import www.atomato.com.tomato.R;
import www.atomato.com.tomato.adapter.CardViewAdapter;
import www.atomato.com.tomato.data.CardViewData;
import www.atomato.com.tomato.utils.BaseActivity;
import www.atomato.com.tomato.utils.ScreenUtils;

/**
 * Created by Administrator on 2017/10/30.
 */

public class FeedbackActivirty extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_main);
        ScreenUtils.setMainColor(this, getResources().getColor(R.color.toolBar));
        initView();
    }

    private void initView() {
        initDrawer();
    }

    private void initDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.card_toolbar);
        toolbar.setTitle("季度卡");
        toolbar.setNavigationIcon(R.mipmap.statistics_activity_menu_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
