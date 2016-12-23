package www.atomato.com.tomato.activity;

import android.app.Activity;
import android.content.Intent;
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
    double percent;
    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        int total_time = intent.getIntExtra("total_time", 1);//总完成时间
        int todo_plan_time = intent.getIntExtra("todo_plan_time", 350);//计划时间
        int todo_current_time = intent.getIntExtra("todo_current_time", 35);//当前时间
        try {
            if (total_time != 0) {
                percent = todo_plan_time / total_time;
            }
        } catch (ArithmeticException e) {
            percent = 0f;
        }
        if (todo_current_time != 0) {
            num = total_time / todo_current_time;
        }
        activityDetailTitle.setText(title);
        activityDetailNum.setText("目前已完成" + num + "次");
        activityDetailTime.setText("计划内累计完成时间" + total_time + "分钟");
        activityDetailMinute.setText("计划总时间 " + todo_plan_time + "分钟");
        activityDetailPercent.setText("计划完成百分比" + percent + "% ");

    }

    @OnClick(R.id.activity_detail_button)
    public void onClick() {
        finish();
    }
}
