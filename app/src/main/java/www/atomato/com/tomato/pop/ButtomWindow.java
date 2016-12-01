package www.atomato.com.tomato.pop;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
import www.atomato.com.tomato.constants.Constants;
import www.atomato.com.tomato.recall.BottomWindowListener;
import www.atomato.com.tomato.sqlite.ViewSQLite;

/**
 * 2016.11.27 countTImer计时器界面
 * 2016.11.29 替换Thread为RxAndroid
 * 2016.11.30 奇葩的开发 由于之前没有任何构思，这个app非常失败 ，RecyclerView的布局是反转的 ，我也真是服了我  ... 终于完成了排序 满脑子浆糊一样的晕
 */

public class ButtomWindow extends PopupWindow implements View.OnTouchListener {
    private BottomWindowListener mBottomWindowListener;
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
    private View mItemView;
    private int mItemPosition;
    private String mTitle;

    public void setItemView(View itemView) {
        mItemView = itemView;
    }

    public void setItemPosition(int itemPosition) {
        mItemPosition = itemPosition;
    }

    public void setTitle(String title) {
        mTitle = title;
        ViewSQLite viewSQLite = new ViewSQLite(mContext);
        try {
            Cursor cursor = viewSQLite.query(Constants.TABLE_NAME, null, "todo_title=?", new String[]{title}, null, null, null);
            cursor.moveToNext();
            int stick = cursor.getInt(cursor.getColumnIndex("todo_stick"));
            if (stick == 1) {
                popStick.setText("取消置顶");
            }
            if (stick == 0) {
                popStick.setText("置顶");
            }
        } finally {
            viewSQLite.closedb();
        }
    }

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
        mView.setOnTouchListener(this);
    }

    public void setBottomWindowListener(BottomWindowListener bottomWindowListener) {
        this.mBottomWindowListener = bottomWindowListener;
    }

    @OnClick({R.id.pop_stick, R.id.pop_remind, R.id.pop_mark, R.id.pop_edit, R.id.pop_delete, R.id.pop_cancel, R.id.pop_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pop_stick:
                if (mBottomWindowListener != null && mItemView != null) {
                    mBottomWindowListener.stickClick(view, mItemPosition);
                    ViewSQLite viewSQLite = new ViewSQLite(mContext);
                    try {
                        Cursor cursor = viewSQLite.query(Constants.TABLE_NAME, null, "todo_title=?", new String[]{mTitle}, null, null, null);
                        cursor.moveToNext();
                        int stick = cursor.getInt(cursor.getColumnIndex("todo_stick"));
                        if (stick == 1) {
                            popStick.setText("取消置顶");
                            long time = System.currentTimeMillis() - cursor.getLong(cursor.getColumnIndex("todo_create"));
                            //置顶停留的时间 = 现在时间减创建时间加原本时间
                            ContentValues values = new ContentValues();
                            values.put("todo_stick", 0);
                            values.put("todo_stick_time", time);//用来排序
                            viewSQLite.update(Constants.TABLE_NAME, values, "todo_title=?", new String[]{mTitle});
                        }
                        if (stick == 0) {
                            popStick.setText("置顶");
                            ContentValues values = new ContentValues();
                            values.put("todo_stick", 1);
                            values.put("todo_stick_time", System.currentTimeMillis());
                            viewSQLite.update(Constants.TABLE_NAME, values, "todo_title=?", new String[]{mTitle});
                        }
                    } finally {
                        viewSQLite.closedb();
                    }
                }
                dismiss();
                break;
            case R.id.pop_remind:
                if (mBottomWindowListener != null && mItemView != null) {
                    mBottomWindowListener.remindClick(view, mItemPosition);
                }
                dismiss();
                break;
            case R.id.pop_mark:
                if (mBottomWindowListener != null && mItemView != null) {
                    mBottomWindowListener.markClick(view, mItemPosition);
                }
                dismiss();
                break;
            case R.id.pop_edit:
                if (mBottomWindowListener != null && mItemView != null) {
                    mBottomWindowListener.editClick(view, mItemPosition);
                }
                dismiss();
                break;
            case R.id.pop_delete:
                if (mBottomWindowListener != null && mItemView != null) {
                    mBottomWindowListener.deleteClick(view, mItemPosition);
                }
                dismiss();
                break;
            case R.id.pop_cancel:
                dismiss();
                break;
//            case R.id.pop_layout:
//                dismiss();
//                break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int height = mView.findViewById(R.id.pop_layout).getTop();

        int y = (int) motionEvent.getY();
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            if (y < height) {
                dismiss();
            }
        }
        return true;
    }
}
