package www.atomato.com.tomato.utils;

import android.util.Log;

/**
 * Created by wangjie on 2016-11-10.
 */

public class LogUtils {
    private static final int VERBOSE = 1;
    private static final int DEBUG   = 2;
    private static final int INFO    = 3;
    private static final int WARN    = 4;
    private static final int ERROR   = 5;
    private static final int NOTHING = 6;
    private static final int LEVEL = 0;
    public static void v(String tag,String msg){
        if(LEVEL<=VERBOSE){
            Log.v(tag,tag+"==="+msg);
        }
    }
    public static void d(String tag,String msg){
        if(LEVEL<=DEBUG){
            Log.d(tag,tag+"==="+msg);
        }
    }
    public static void w(String tag,String msg){
        if(LEVEL<=WARN){
            Log.w(tag,tag+"==="+msg);
        }
    }
    public static void e(String tag,String msg){
        if(LEVEL<=ERROR){
            Log.e(tag,tag+"==="+msg);
        }
    }
    public static void n(String tag,String msg){
        if(LEVEL<=NOTHING){
            Log.v(tag,tag+"==="+msg);
        }
    }
    public static void i(String tag,String msg){
        if(LEVEL<=INFO){
            Log.i(tag,tag+"==="+msg);
        }
    }
}
