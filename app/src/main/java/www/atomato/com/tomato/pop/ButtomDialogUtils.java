package www.atomato.com.tomato.pop;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import www.atomato.com.tomato.R;

/**
 * Created by wangj on 2016-11-24.
 */

public class ButtomDialogUtils {

    public static void showDialog(Context context){
        Dialog mDialog = new Dialog(context,R.style.CustomDialog);
        Window window = mDialog.getWindow();
//        window.setGravity(Gravity.CENTER);
        if(window!=null){
            window.setWindowAnimations(R.style.pop_window_anim);
            View view = View.inflate(context, R.layout.activity_add_item_custrom_time, null);
            window.setContentView(view);
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
            window.setGravity(Gravity.CENTER);
            mDialog.setCanceledOnTouchOutside(true);
            mDialog.setCancelable(true);
            mDialog.show();
        }
    }
}
