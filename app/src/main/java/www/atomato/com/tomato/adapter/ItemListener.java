package www.atomato.com.tomato.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import www.atomato.com.tomato.utils.LogUtils;

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
    private long mDownTime;
    // 计数器，防止多次点击导致最后一次形成longpress的时间变短
    private int mCounter;
    private boolean mReleased;
    private boolean mMoved;
    private Handler mHandler;
    private final long mLongDownTime = 500;
    private boolean isLongPress;//一次Down只能一次长按执行
    private Runnable mLongPressRunnable; //

    //内部接口，定义点击方法以及长按方法
    public interface OnItemClickListener {
        void onLongRightItenClick(View view, int position);

        void onLeftItemClick(View view, int position);

        void onRightItemClick(View view, int position);
    }

    public ItemListener(Context context, OnItemClickListener listener) {
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mListener = listener;
        mHandler = new Handler();
        mLongPressRunnable = new Runnable() {
            @Override
            public void run() {
                LogUtils.i(tag, "thread");
                LogUtils.i(tag, "mCounter--->>>" + mCounter);
                LogUtils.i(tag, "isReleased--->>>" + mReleased);
                LogUtils.i(tag, "isMoved--->>>" + mMoved);
                mCounter--;
                // 计数器大于0，说明当前执行的Runnable不是最后一次down产生的。
                if (mCounter > 0 || mReleased || mMoved)
                    return;
//                mListener.onLongRightItenClick();
            }
        };
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
                mCounter++;
                mMoved = false;
                mReleased = false;
                mHandler.postDelayed(mLongPressRunnable, 3000);
                break;

            case MotionEvent.ACTION_MOVE:
                if (mMoved)
                    break;
                if (Math.abs(x - mLastDownX) > touchSlop || Math.abs(y - mLastDownY) > touchSlop) {
                    mMoved = true;
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
                if (mMoved) {
                    break;
                }
                if (System.currentTimeMillis() - mDownTime < mLongDownTime) {
                    isSingleTapUp = true;
                }
//                    isLongPressUp = true;
//                } else {
//                    isSingleTapUp = true;
//                }
                mReleased = false;
                break;
        }
        if (isSingleTapUp) {
            //根据触摸坐标来获取childView
            View childView = rv.findChildViewUnder(e.getX(), e.getY());
            isSingleTapUp = false;
            if (childView != null) {
                if (x > rv.getWidth() / 1.56 && x < rv.getWidth()) {
                    //回调mListener#onItemClick方法
                    mListener.onLeftItemClick(childView, rv.getChildLayoutPosition(childView));
                } else {
                    mListener.onRightItemClick(childView, rv.getChildLayoutPosition(childView));
                }
            }
        }
        if (isLongPressUp) {
            //长按的区域
            if (x > 0 && x < rv.getWidth() / 1.56) {
//                LogUtils.e(tag,tag+rv.getWidth()/1.55);
                View childView = rv.findChildViewUnder(e.getX(), e.getY());
                isLongPressUp = false;
                if (childView != null) {
                    mListener.onLongRightItenClick(childView, rv.getChildLayoutPosition(childView));
                }
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