package www.atomato.com.tomato.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import www.atomato.com.tomato.R;
import www.atomato.com.tomato.utils.BaseActivity;
import www.atomato.com.tomato.utils.ScreenUtils;

/**
 * Created by Administrator on 2017/10/30.
 */

public class CardViewActivirty extends BaseActivity implements  View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_main);
        ScreenUtils.setMainColor(this, getResources().getColor(R.color.toolBar));
        initView();
    }

    private void initView() {
        initAnimation();

        /// /初始化Toolbar 和 Drawer
        Toolbar toolbar = (Toolbar) findViewById(R.id.card_toolbar);
        toolbar.setTitle("季度卡");
        toolbar.setNavigationIcon(R.mipmap.statistics_activity_menu_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);
        //初始化RecyclerView
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.activity_card_content_recycleList);

    }

    private void initAnimation() {
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
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
