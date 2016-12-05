package www.atomato.com.tomato.data;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import net.youmi.android.AdManager;
import net.youmi.android.normal.banner.BannerManager;
import net.youmi.android.normal.banner.BannerViewListener;

/**
 * Created by wangj on 12/5/2016.
 */

public class YouMiSdk implements BannerViewListener {
    private Context mContext;

    public YouMiSdk(Context context) {
        mContext = context;
    }

    public void init(boolean isTestModel) {
        AdManager.getInstance(mContext).init("e7dab8269b821c68", "fbe411819b64fab2", isTestModel, false);
    }

    public void setBanner(LinearLayout linearLayout) {
        // 获取广告条
        View bannerView = BannerManager.getInstance(mContext)
                .getBannerView(this);

// 获取要嵌入广告条的布局
        LinearLayout bannerLayout = linearLayout;

// 将广告条加入到布局中
        bannerLayout.addView(bannerView);
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
}
