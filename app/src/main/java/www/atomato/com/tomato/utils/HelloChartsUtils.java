package www.atomato.com.tomato.utils;

import android.content.Context;

import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by wangjie on 2016-11-20.
 */

public class HelloChartsUtils {
    public static LineChartView getLineCharView(Context context){
        LineChartView chart = new LineChartView(context);
        return chart;
    }
}
