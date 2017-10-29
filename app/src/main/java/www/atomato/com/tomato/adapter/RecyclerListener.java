package www.atomato.com.tomato.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import www.atomato.com.tomato.utils.LogUtils;

/**
 * Created by wangjie on 16-11-17.
 */

public class RecyclerListener implements RecyclerView.OnItemTouchListener {
    private String tag = getClass().getSimpleName();
    private int mLastDownX, mLastDownY;
    private OnItemClickListener mListener;
    //是否是单击事件
    private boolean isSingleTapUp = false;
    // 计数器，防止多次点击导致最后一次形成longpress的时间变短
    private int mCounter = 0;
    private boolean mReleased;
    private boolean mMoved;
    private Handler mHandler;
    //是否是长按事件
//    private boolean isLongPressUp = false;
    //    private boolean isLongPress;//一次Down只能一次长按执行
    private long mDownTime;

    //内部接口，定义点击方法以及长按方法
    public interface OnItemClickListener {
        void onLongLeftItemClick(View view, int position);

        void onLeftItemClick(View view, int position);

        void onRightItemClick(View view, int position);
    }

    public RecyclerListener(Context context, OnItemClickListener listener) {
//        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mHandler = new Handler();
        mListener = listener;
    }

    private void setLong(final View view, final int position) {
        Runnable mLongPressRunnable = new Runnable() {
            @Override
            public void run() {
                LogUtils.e(tag, "setLong Runnable running");
                mCounter--;
                // 计数器大于0，说明当前执行的Runnable不是最后一次down产生的。
                if (mCounter > 0 || mReleased || mMoved) {
                    return;
                }
                mListener.onLongLeftItemClick(view, position);
            }
        };
        mHandler.postDelayed(mLongPressRunnable, 1000);
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
                View childView = rv.findChildViewUnder(e.getX(), e.getY());
                if (childView != null) {
                    setLong(childView, rv.getChildAdapterPosition(childView));
                }
                break;

            case MotionEvent.ACTION_MOVE:
//                LogUtils.e(tag,"x=="+Math.abs(x - mLastDownX)+"\ny=="+Math.abs(y - mLastDownY));
                if (mMoved)
                    break;
                int touchSlop = 20;//最小滑动举例
                if (Math.abs(x - mLastDownX) > touchSlop || Math.abs(y - mLastDownY) > touchSlop) {
                    mMoved = true;
                }
                break;

            case MotionEvent.ACTION_UP:
                if (mMoved) {
                    break;
                }
                mReleased = true;
                long mLongDownTime = 1000;
                //是否为长按事件
                if (System.currentTimeMillis() - mDownTime < mLongDownTime) {
                    isSingleTapUp = true;
                }//else {
//                    isLongPressUp = true;
//                }
                break;
        }
        if (isSingleTapUp) {
            //根据触摸坐标来获取childView
//            View childView = rv.findChildViewUnder(e.getX(), e.getY());
            View childView = rv.findChildViewUnder(e.getX(), e.getY());
            isSingleTapUp = false;
            if (childView != null) {
                if (x > rv.getWidth() / 1.56 && x < rv.getWidth()) {
                    //回调mListener#onItemClick方法
                    mListener.onRightItemClick(childView, rv.getChildLayoutPosition(childView));
                } else {
                    mListener.onLeftItemClick(childView, rv.getChildLayoutPosition(childView));
                }
            }
        }
//        if (isLongPressUp) {
//            //长按的区域
//            if (x > 0 && x < rv.getWidth() / 1.56) {
////                LogUtils.e(tag,tag+rv.getWidth()/1.55);
//                isLongPressUp = false;
//                View childView = rv.findChildViewUnder(e.getX(), e.getY());
//                if (childView != null) {
//                    setLong(childView, rv.getChildAdapterPosition(childView));
//                }
//            }
//        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}