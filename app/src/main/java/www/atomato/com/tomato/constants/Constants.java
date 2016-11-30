package www.atomato.com.tomato.constants;

import android.graphics.Color;

public class Constants {
    public static final int REQUEST_CODE_ADD = 0;
    public static final int REQUEST_CODE_PROGRESS = 1;
    //返回用户选择todo状态码---------------------
    public static final int EVERY_DAY_RADIO = 100;//每天
    public static final int ONE_DAY_RADIO = 101;//一天
    public static final int DEFAULT_RADIO = 102;//默认 35
    public static final int TIME_RADIO = 103;//45
    public static final int CUSTOM_RADIO = 104;//自定义
    public static final int SHORT_RADIO = 105;//短期
    public static final int LONG_RADIO = 106;//长期
    //进度条选项-------------------------------------
    public static final int TODO_SOLID = 107;
    public static final int TODO_SOLID_AND_FRAME = 108;
    public static final int TODO_HOLLOW = 109;
    //进度条宽度
    public static final int PROGRESSBAR_WIDTH = 200;
    //数据库类------------------------------------------------
    public static final int DATABASE_VERSION = 1;  //数据库版本号
    public static final String DATABASE_NAME = "todo";  //数据库名称
    public static final String TABLE_NAME = "item_info";
    public static final String TOTAL_NAME = "total_info";
    //handler
    public static final int CREATE_TODO = 110;
    public static final int DELETE_TODO = 111;
    public static final int UPDATE_TODO = 112;

    //定义一些常量(大小写字母切换快捷键 Ctrl + Shift + U)
    public static final int DEFAULT_CIRCLE_SOLIDE_COLOR = Color.parseColor("#FFFFFF");
    public static final int DEFAULT_CIRCLE_STROKE_COLOR = Color.parseColor("#D1D1D1");
    public static final int DEFAULT_CIRCLE_STROKE_WIDTH = 5;
    public static int DEFAULT_CIRCLE_RADIUS = 100;

    public static final int PROGRESS_COLOR = Color.parseColor("#F76E6B");
    public static final int PROGRESS_WIDTH = 5;

    public static final int SMALL_CIRCLE_SOLIDE_COLOR = Color.parseColor("#FFFFFF");
    public static final int SMALL_CIRCLE_STROKE_COLOR = Color.parseColor("#F76E6B");
    public static final int SMALL_CIRCLE_STROKE_WIDTH = 2;
    public static final int SMALL_CIRCLE_RADIUS = 6;

    public static final int TEXT_COLOR = Color.parseColor("#F76E6B");
    public static final int TEXT_SIZE = 40;

}
