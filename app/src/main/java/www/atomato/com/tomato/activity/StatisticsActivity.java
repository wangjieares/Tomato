package www.atomato.com.tomato.activity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.db.chart.model.BarSet;
import com.db.chart.model.LineSet;
import com.db.chart.view.BarChartView;
import com.db.chart.view.LineChartView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import www.atomato.com.tomato.R;
import www.atomato.com.tomato.constants.Constants;
import www.atomato.com.tomato.data.ColorConstants;
import www.atomato.com.tomato.data.SettingController;
import www.atomato.com.tomato.sqlite.ViewSQLite;
import www.atomato.com.tomato.utils.BaseActivity;
import www.atomato.com.tomato.utils.ScreenUtils;

/**
 * Created by wangjie on 2016-11-20.
 */

public class StatisticsActivity extends BaseActivity implements View.OnClickListener {
    private final String[] mLabels = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
    private final float[][] mValues = {{3.5f, 4.7f, 4.3f, 8f, 6.5f, 9.9f, 7f},
            {4.5f, 2.5f, 2.5f, 9f, 4.5f, 12.5f, 5f}};
    @BindView(R.id.content_statistics_item_day_background)
    LinearLayout contentStatisticsItemDayBackground;
    @BindView(R.id.content_statistics_item_total_background)
    LinearLayout contentStatisticsItemTotalBackground;
    @BindView(R.id.content_statistics_item_day_num)//当天的番茄个数
    TextView contentStatisticsItemDayNum;
    @BindView(R.id.content_statistics_item_day_time)//当天的番茄时间
    TextView contentStatisticsItemDayTime;
    @BindView(R.id.content_statistics_item_total_num)//总共的番茄个数
    TextView contentStatisticsItemTotalNum;
    @BindView(R.id.content_statistics_item_total_time)//总共的番茄个数
    TextView contentStatisticsItemTotalTime;
    private int totalTime;//番茄钟总时间
    private int totalNum;//番茄钟总时间
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staticstics_bar);
        ButterKnife.bind(this);
        ScreenUtils.setColor(this, getResources().getColor(R.color.toolBar));
        Toolbar toolbar = (Toolbar) findViewById(R.id.statistics_toolbar);
        toolbar.setTitle("统计数据");
        toolbar.setNavigationIcon(R.mipmap.statistics_activity_menu_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);

        if (SettingController.getInstance().isRandomColor()) {
            contentStatisticsItemTotalBackground.setBackgroundColor(ColorConstants.randomBackground());
            contentStatisticsItemDayBackground.setBackgroundColor(ColorConstants.randomBackground());
        }
        //查找当天，总共番茄钟个数和时间
        ViewSQLite viewSQLite = new ViewSQLite(this);
        try {
            Cursor cursor = viewSQLite.query();
            if(cursor.moveToNext()){
                totalTime=totalTime+cursor.getInt(cursor.getColumnIndex("todo_total_time"));
                totalNum=totalNum+cursor.getInt(cursor.getColumnIndex("todo_total_num"));
            }
        }catch (Exception e){

        }
//        int totalNum = viewSQLite.sumColumn("total_time",Constants.TOTAL_NAME);
//        int totalTime = viewSQLite.sumColumn("total_time",Constants.TOTAL_NAME);
//        contentStatisticsItemDayNum.setText();
//        contentStatisticsItemDayTime.setText();
        contentStatisticsItemTotalNum.setText(String.valueOf(totalNum));
        contentStatisticsItemTotalTime.setText(String.valueOf(totalTime));

        BarChartView barChartView = (BarChartView) findViewById(R.id.activity_statistics_content_bar_chart_view);
        BarSet barSet = new BarSet(mLabels, mValues[0]);
        barChartView.addData(barSet);
        barChartView.show();


        LineChartView lineChartView = (LineChartView) findViewById(R.id.activity_statistics_content_line_chart_view);
        LineSet dataset = new LineSet(mLabels, mValues[1]);
        dataset.setColor(Color.parseColor("#758cbb"))
//                .setFill(Color.parseColor("#2d374c"))
                .setDotsColor(Color.parseColor("#758cbb"))
                .setThickness(1)
                .setDashed(new float[]{5f, 5f})
                .beginAt(0);
        lineChartView.addData(dataset);
        lineChartView.setXAxis(false);
        lineChartView.setYAxis(false);
        dataset = new LineSet(mLabels, mValues[0]);
        dataset.setColor(Color.parseColor("#b3b5bb"))
//                .setFill(Color.parseColor("#2d374c"))
                .setDotsColor(Color.parseColor("#ffc755"))
                .setThickness(2)
                .setSmooth(true)
                .endAt(7);
        lineChartView.addData(dataset);
        lineChartView.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.statistics, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.statistics_activity_share:
                showShare();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("着急");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }

    @Override
    public void onClick(View v) {
        finish();
//        LogUtils.e("onClick", "onClick");
    }
}
