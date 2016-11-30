package www.atomato.com.tomato.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.atomato.com.tomato.R;

/**
 * Created by wangj on 2016-11-28.
 */

public class DetailActivity extends Activity {
    @BindView(R.id.activity_detail_title)
    TextView activityDetailTitle;
    @BindView(R.id.activity_detail_num)
    TextView activityDetailNum;
    @BindView(R.id.activity_detail_time)
    TextView activityDetailTime;
    @BindView(R.id.activity_detail_minute)
    TextView activityDetailMinute;
    @BindView(R.id.activity_detail_percent)
    TextView activityDetailPercent;
    @BindView(R.id.activity_detail_button)
    Button activityDetailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        String todo_title = getIntent().getStringExtra("todo_title");
        activityDetailTitle.setText("读书");
        activityDetailNum.setText("目前已完成0次");
        activityDetailTime.setText("计划内累计完成时间0分钟");
        activityDetailMinute.setText("计划总时间 6000分钟");
        activityDetailPercent.setText("计划完成百分比0.000% ");

    }

    @OnClick(R.id.activity_detail_button)
    public void onClick() {
        finish();
    }
}
