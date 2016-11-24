package www.atomato.com.tomato.constants;

public class Constants {
    public static final int REQUEST_CODE_ADD = 0;
    //返回用户选择todo状态码---------------------
    public static final int LONG_RADIO = 106;
    public static final int SHORT_RADIO = 105;
    public static final int CUSTOM_RADIO = 104;
    public static final int TIME_RADIO = 103;
    public static final int DEFAULT_RADIO = 102;
    public static final int ONE_DAY_RADIO = 101;
    public static final int EVERY_DAY_RADIO = 100;
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
    //handler
    public static final int CREATE_TODO = 110;
    public static final int DELETE_TODO = 111;
    public static final int UPDATE_TODO = 112;


}
