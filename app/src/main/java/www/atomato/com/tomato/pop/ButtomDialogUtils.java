package www.atomato.com.tomato.pop;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import www.atomato.com.tomato.R;
import www.atomato.com.tomato.utils.LogUtils;
import www.atomato.com.tomato.utils.StringUtil;
import www.atomato.com.tomato.view.ToDoView;

/**
 * Created by wangj on 2016-11-24.
 */

public class ButtomDialogUtils {

    public static void showDialog(Context context, final DialogListener dialogListener) {
        final Dialog mDialog = new Dialog(context, R.style.CustomDialog);
        Window window = mDialog.getWindow();
//        window.setGravity(Gravity.CENTER);
        if (window != null) {
            window.setWindowAnimations(R.style.pop_window_anim);
            View view = View.inflate(context, R.layout.activity_add_item_custrom_time, null);
            window.setContentView(view);
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
            window.setGravity(Gravity.CENTER);
            mDialog.setCanceledOnTouchOutside(true);
            mDialog.setCancelable(true);
            mDialog.show();
            Button button = (Button) view.findViewById(R.id.custrom_button);
            final EditText editText = (EditText) view.findViewById(R.id.edit_custom_time);
            button.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                              if (dialogListener != null) {
                                                  try {
                                                      if (!editText.getText().toString().equals("")) {
                                                          String custrom = editText.getText().toString();
                                                          if (StringUtil.isNumeric1(custrom)) {//判断string是不是字符串
                                                              dialogListener.setValue(Integer.parseInt(custrom));
                                                              LogUtils.e("custrom", custrom);
                                                          }
                                                      }
                                                  } finally {
                                                      dialogListener.setValue(55);
                                                      mDialog.dismiss();
                                                  }
                                              }
                                          }
                                      }

            );
        }
    }
}
