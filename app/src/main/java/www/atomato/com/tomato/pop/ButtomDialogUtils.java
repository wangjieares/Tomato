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
import www.atomato.com.tomato.recall.DialogListener;
import www.atomato.com.tomato.utils.LogUtils;
import www.atomato.com.tomato.utils.StringUtil;

/**
 * Created by wangj on 2016-11-24.
 * 这里本来可以合起来一个精简代码，但是这里功能不确定，方便以后衍生功能，我写复杂点
 */

public class ButtomDialogUtils {

    public static void showCustromDialog(Context context, final DialogListener dialogListener) {
        final Dialog mDialog = new Dialog(context, R.style.CustomDialog);
        Window window = mDialog.getWindow();
//        window.setGravity(Gravity.CENTER);
        if (window != null) {
            //这段代码是键盘遮挡住diglog
//            mDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
//            mDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
//                    WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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
                                                              dialogListener.setCustromValue(Integer.parseInt(custrom));
                                                              LogUtils.e("custrom", custrom);
                                                          }
                                                      } else {
                                                          dialogListener.setCustromValue(55);
                                                      }
                                                  } finally {
                                                      mDialog.dismiss();
                                                  }
                                              }
                                          }
                                      }

            );
        }
    }

    public static void showLongDialog(Context context, final DialogListener dialogListener) {
        final Dialog mDialog = new Dialog(context, R.style.CustomDialog);
        Window window = mDialog.getWindow();
//        window.setGravity(Gravity.CENTER);
        if (window != null) {
            window.setWindowAnimations(R.style.pop_window_anim);
            View view = View.inflate(context, R.layout.activity_add_long_plan, null);
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
                                                              dialogListener.setLongValue(Integer.parseInt(custrom));
                                                          }
                                                      } else {
                                                          dialogListener.setLongValue(10000);
                                                      }
                                                  } finally {
                                                      mDialog.dismiss();
                                                  }
                                              }
                                          }
                                      }

            );
        }
    }
}
