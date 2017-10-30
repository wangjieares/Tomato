package www.atomato.com.tomato.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import www.atomato.com.tomato.R;
import www.atomato.com.tomato.utils.BaseActivity;
import www.atomato.com.tomato.utils.ScreenUtils;

/**
 * Created by Administrator on 2017/10/30.
 */

public class CardViewActivirty extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_main);
        ScreenUtils.setMainColor(this, getResources().getColor(R.color.toolBar));
        initView();
    }

    private void initView() {
        //动画初始化
        final CardView cardView = (CardView)findViewById(R.id.card_view);
        ScaleAnimation enterAnim = new ScaleAnimation(0f, 1.1f, 0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        enterAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }
            @Override
            public void onAnimationRepeat(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                ScaleAnimation anim = new ScaleAnimation(1f, 0.95f, 1f, 0.95f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                anim.setDuration(2000);
                anim.setRepeatMode(Animation.REVERSE); // 放大并缩小，时间为750*2
                anim.setRepeatCount(Animation.INFINITE); // 无限循环
                cardView.setAnimation(anim);
                cardView.startAnimation(cardView.getAnimation());
//                mImageView.setAnimation(anim);
//                mImageView.startAnimation(mImageView.getAnimation());
            }
        });
        cardView.startAnimation(enterAnim);
        /// /初始化Toolbar 和 Drawer
        Toolbar toolbar = (Toolbar) findViewById(R.id.card_toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.card_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.card_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemTextAppearance(R.style.MenuTextStyle);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.statistics) {
//            item.setCheckable(true);
//            item.setChecked(true);
            Intent intent = new Intent(this, StatisticsActivity.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.relax) {
            Intent intent = new Intent(this, RelaxActivity.class);
            startActivity(intent);
//            item.setCheckable(true);
//            item.setChecked(true);
        } else if (id == R.id.future_plan) {
            Intent intent = new Intent(this, FetureActivity.class);
            startActivity(intent);
//            item.setCheckable(true);
//            item.setChecked(true);
        } else if (id == R.id.setting) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
//            item.setCheckable(true);
//            item.setChecked(true);
        } else if (id == R.id.card) {
            Intent intent = new Intent(this, CardViewActivirty.class);
            startActivity(intent);
        } else if (id == R.id.about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
//            item.setCheckable(true);
//            item.setChecked(true);
        }else if(id==R.id.feedback){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
