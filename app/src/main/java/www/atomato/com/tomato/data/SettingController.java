package www.atomato.com.tomato.data;

/**
 * Created by wangj on 12/5/2016.
 */

public class SettingController {
    private static SettingController mInstance;
    private boolean mRandomColor = false;
    public static SettingController getInstance(){
        if(mInstance ==null){
            mInstance = new SettingController();
        }
        return mInstance;
    }

    public boolean isRandomColor() {
        return mRandomColor;
    }

    public void setRandomColor(boolean randomColor) {
        mRandomColor = randomColor;
    }
}
