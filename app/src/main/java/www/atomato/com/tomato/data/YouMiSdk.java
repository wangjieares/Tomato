package www.atomato.com.tomato.data;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import net.youmi.android.AdManager;
import net.youmi.android.normal.banner.BannerManager;
import net.youmi.android.normal.banner.BannerViewListener;
import net.youmi.android.normal.spot.SplashViewSettings;
import net.youmi.android.normal.spot.SpotListener;
import net.youmi.android.normal.spot.SpotManager;

import www.atomato.com.tomato.activity.MainActivity;

/**
 * Created by wangj on 12/5/2016.
 */

public class YouMiSdk implements BannerViewListener, SpotListener {
    private Context mContext;
    private boolean isTest = true;
    public YouMiSdk(Context context) {
        mContext = context;
        init(isTest);
    }

    void init(boolean isTestModel) {
        AdManager.getInstance(mContext).init("e7dab8269b821c68", "fbe411819b64fab2", isTestModel, false);
    }

    public void setBanner(LinearLayout linearLayout) {
        // 获取广告条
        View bannerView = BannerManager.getInstance(mContext)
                .getBannerView(this);

// 将广告条加入到布局中
        linearLayout.addView(bannerView);
    }

    public void initSplash(LinearLayout adLinearLayout) {
        SplashViewSettings splashViewSettings = new SplashViewSettings();
        splashViewSettings.setTargetClass(MainActivity.class);
        // 使用默认布局参数
        splashViewSettings.setSplashViewContainer(adLinearLayout);

        SpotManager.getInstance(mContext).showSplash(mContext,
                splashViewSettings, this);
    }
    @Override
    public void onRequestSuccess() {

    }

    @Override
    public void onSwitchBanner() {

    }

    @Override
    public void onRequestFailed() {

    }

    @Override
    public void onShowSuccess() {

    }

    @Override
    public void onShowFailed(int i) {

    }

    @Override
    public void onSpotClosed() {

    }

    @Override
    public void onSpotClicked(boolean b) {

    }
}
