package www.atomato.com.tomato.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by wangjie on 16-11-17.
 */

public class ItemListener implements RecyclerView.OnItemTouchListener {
    private String tag = getClass().getSimpleName();
    private int mLastDownX, mLastDownY;
    //该值记录了最小滑动距离
    private int touchSlop;
    private OnItemClickListener mListener;
    //是否是单击事件
    private boolean isSingleTapUp = false;
    //是否是长按事件
    private boolean isLongPressUp = false;
    private boolean isMove = false;
    private long mDownTime;
    private final long mLongDownTime = 500;
    private boolean isLongPress;//一次Down只能一次长按执行

    //内部接口，定义点击方法以及长按方法
    public interface OnItemClickListener {
        void onLongRightItenClick(View view, int position);

        void onLeftItemClick(View view, int position);

        void onRightItemClick(View view, int position);
    }

    public ItemListener(Context context, OnItemClickListener listener) {
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mListener = listener;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        switch (e.getAction()) {

            case MotionEvent.ACTION_DOWN:
                mLastDownX = x;
                mLastDownY = y;
                mDownTime = System.currentTimeMillis();
                isMove = false;
                break;

            case MotionEvent.ACTION_MOVE:
                if (Math.abs(x - mLastDownX) > touchSlop || Math.abs(y - mLastDownY) > touchSlop) {
                    isMove = true;
                }
                if (isLongPress) {
                    if (System.currentTimeMillis() - mDownTime > mLongDownTime) {
                        isLongPressUp = true;
                        isLongPress = false;
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                isLongPress = true;
                if (isMove) {
                    break;
                }
                if (System.currentTimeMillis() - mDownTime < mLongDownTime) {
                    isSingleTapUp = true;
                }
//                    isLongPressUp = true;
//                } else {
//                    isSingleTapUp = true;
//                }
                break;
        }
        if (isSingleTapUp) {
            //根据触摸坐标来获取childView
            View childView = rv.findChildViewUnder(e.getX(), e.getY());
            isSingleTapUp = false;
            if (childView != null) {
                if (x > 460 && x < rv.getWidth()) {
                    //回调mListener#onItemClick方法
                    mListener.onLeftItemClick(childView, rv.getChildLayoutPosition(childView));
                } else {
                    mListener.onRightItemClick(childView, rv.getChildLayoutPosition(childView));
                }
            }
        }
        if (isLongPressUp) {
            View childView = rv.findChildViewUnder(e.getX(), e.getY());
            isLongPressUp = false;
            if (childView != null) {
                mListener.onLongRightItenClick(childView, rv.getChildLayoutPosition(childView));
            }
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}