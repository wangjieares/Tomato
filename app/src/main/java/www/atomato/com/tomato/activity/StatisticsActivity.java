package www.atomato.com.tomato.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;
import www.atomato.com.tomato.R;
import www.atomato.com.tomato.utils.BaseActivity;
import www.atomato.com.tomato.utils.HelloChartsUtils;

/**
 * Created by wangjie on 2016-11-20.
 */

public class StatisticsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staticstics);

        Toolbar toolbar = (Toolbar) findViewById(R.id.statistics_toolbar);
        setSupportActionBar(toolbar);
        LineChartView chart = (LineChartView)findViewById(R.id.statistics_lineChartView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.statistics, menu);
        return true;
    }
}
