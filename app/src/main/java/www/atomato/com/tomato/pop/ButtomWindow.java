package www.atomato.com.tomato.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.atomato.com.tomato.R;

/**
 * Created by wangj on 2016-11-24.
 */

public class ButtomWindow extends PopupWindow {
    @BindView(R.id.pop_stick)
    Button popStick;
    @BindView(R.id.pop_remind)
    Button popRemind;
    @BindView(R.id.pop_mark)
    Button popMark;
    @BindView(R.id.pop_edit)
    Button popEdit;
    @BindView(R.id.pop_delete)
    Button popDelete;
    @BindView(R.id.pop_cancel)
    Button popCancel;
    @BindView(R.id.pop_layout)
    LinearLayout popLayout;
    private Context mContext;
    private View mView;

    public ButtomWindow(Activity context) {
        this.mContext = context;
        this.mView = LayoutInflater.from(mContext).inflate(R.layout.pop_window, null);
        // 设置外部可点击
        ButterKnife.bind(this, mView);
        this.setOutsideTouchable(true);
        /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.mView);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.pop_window_anim);
        //点击popwindows之外 自动销毁
        this.mView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mView.findViewById(R.id.pop_layout).getTop();

                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    @OnClick({R.id.pop_stick, R.id.pop_remind, R.id.pop_mark, R.id.pop_edit, R.id.pop_delete, R.id.pop_cancel, R.id.pop_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pop_stick:
                dismiss();
                break;
            case R.id.pop_remind:
                dismiss();
                break;
            case R.id.pop_mark:
                dismiss();
                break;
            case R.id.pop_edit:
                dismiss();
                break;
            case R.id.pop_delete:
                dismiss();
                break;
            case R.id.pop_cancel:
                dismiss();
                break;
            case R.id.pop_layout:
                dismiss();
                break;
        }
    }
}
