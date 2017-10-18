package www.atomato.com.tomato.data;

/**
 * Created by wangjie on 12/3/2016.
 * 控制整体广告的显示与隐藏
 * 控制部分广告的显示与隐藏
 */

public class AdBehaior {
    private static boolean OVERALL_BEHAIOR = true;//全部
    private static boolean ABOUT_BEHAIOR = true;
    private static boolean LAUNCH_BEHAIOR = false;
    public static void setOverallBehaior(boolean behaior){
        OVERALL_BEHAIOR = behaior;
    }
    public static void setAboutBehaior(boolean behaior){
        OVERALL_BEHAIOR = behaior;
    }
    public static boolean LaunchAd(){
        if(OVERALL_BEHAIOR){
            if (LAUNCH_BEHAIOR){
                return true;
            }else{
                return false;
            }
        }else {
            return false;
        }
    }
    public static boolean aboutAd(){
        if(OVERALL_BEHAIOR){
            if (ABOUT_BEHAIOR){
                return true;
            }else{
                return false;
            }
        }else {
            return false;
        }
    }
}
