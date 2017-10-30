package www.atomato.com.tomato.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
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

public class CardViewActivirty extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_main);
        ScreenUtils.setMainColor(this, getResources().getColor(R.color.toolBar));
        initView();
    }

    private void initView() {
        //动画初始化
        initAnimation();
        /// /初始化Toolbar 和 Drawer
        initDrawer();
        //初始化RecyclerView
        initRecyclerView();
    }

    private void initDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.card_toolbar);
        toolbar.setTitle("季度卡");
        toolbar.setNavigationIcon(R.mipmap.statistics_activity_menu_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_card_content_recycleList);
        //模拟数据
        CardViewData data1 = new CardViewData("想要不拖延？看看这里总会有想看的", "RecyclerView出现已经有一段时间了，相信大家肯定不陌生了，大家可以通过导入support-v7对其进行使用。 \n" +
                "据官方的介绍，该控件用于在有限的窗口中展示大量数据集，其实这样功能的控件我们并不陌生，例如：ListView、GridView。\n" +
                "\n" +
                "那么有了ListView、GridView为什么还需要RecyclerView这样的控件呢？整体上看RecyclerView架构，提供了一种插拔式的体验，高度的解耦，异常的灵活，通过设置它提供的不同LayoutManager，ItemDecoration , ItemAnimator实现令人瞠目的效果。");
        CardViewData data2 = new CardViewData("想要不拖延？看看这里总会有想看的", "RecyclerView出现已经有一段时间了，相信大家肯定不陌生了，大家可以通过导入support-v7对其进行使用。 \n" +
                "据官方的介绍，该控件用于在有限的窗口中展示大量数据集，其实这样功能的控件我们并不陌生，例如：ListView、GridView。\n" +
                "\n" +
                "那么有了ListView、GridView为什么还需要RecyclerView这样的控件呢？整体上看RecyclerView架构，提供了一种插拔式的体验，高度的解耦，异常的灵活，通过设置它提供的不同LayoutManager，ItemDecoration , ItemAnimator实现令人瞠目的效果。");
        ArrayList<CardViewData> arrayList = new ArrayList<>();
        arrayList.add(data1);
        arrayList.add(data2);
        RecyclerView.Adapter adapter = new CardViewAdapter(arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
//创建并设置Adapter
        recyclerView.setAdapter(adapter);
    }

    private void initAnimation() {
        final CardView cardView = (CardView) findViewById(R.id.card_view);
        ScaleAnimation enterAnim = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        enterAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

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
