package www.atomato.com.tomato.data;

import android.content.Context;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Random;

import www.atomato.com.tomato.constants.ColorConstants;
import www.atomato.com.tomato.constants.Constants;
import www.atomato.com.tomato.sqlite.ViewSQLite;
import www.atomato.com.tomato.utils.LogUtils;

/**
 * Created by wangjie on 16-11-17.
 */
public class ToDoData implements Serializable, Comparable {
    private String mTitle;
    private int mTime;
    private int mDrawBackColor;
    private float mProgress = 0f;
    private int mState = 0;
    private int mDay = Constants.EVERY_DAY_RADIO;
    private int mPlan = Constants.TIME_RADIO;
    private int mType = Constants.LONG_RADIO;
    private long mCreate;
    private int mDestory;
    private Context mContext;
    private ViewSQLite viewSQLite;
    private int mIndexNum = 0;//总个数
    private int mTotalTime = 0;//总时间
    private int mStickState = 0;//是否置顶
    private long mStickStateTime;//置顶时间

    public long getStickStateTime() {
        return mStickStateTime;
    }

    public void setStickStateTime(long stickStateTime) {
        mStickStateTime = stickStateTime;
    }

    public ToDoData(Context context, String title, int time, int state, float progress, int drawColor, int day, int plan, int type) {
        mContext = context;
        this.mTitle = title;
        this.mTime = time;
        this.mDrawBackColor = drawColor;
        this.mProgress = progress;
        this.mState = state;
        this.mDay = day;
        this.mPlan = plan;
        this.mType = type;
        mDrawBackColor = ColorConstants.randomBackground();
        mCreate = System.currentTimeMillis();
        mStickStateTime = System.currentTimeMillis();
        insertSQL();
    }

    private void insertSQL() {
        try {
            viewSQLite = new ViewSQLite(mContext);
            viewSQLite.insert(this);
        } finally {
            viewSQLite.closedb();
        }
    }


    public ToDoData(String title, int time, int mState, float mProgress, int drawBackColor) {
        this.mTitle = title;
        this.mTime = time;
        this.mDrawBackColor = drawBackColor;
        this.mProgress = mProgress;
        this.mState = mState;
        mCreate = System.currentTimeMillis();
        mStickStateTime = System.currentTimeMillis();
    }

    public int getType() {
        return mType;
    }

    public void setType(int mType) {
        this.mType = mType;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTime(int mTime) {
        this.mTime = mTime;
    }

    public int getTime() {
        return mTime;
    }

    public void setDrawBackColor(int drawBackColor) {
        this.mDrawBackColor = drawBackColor;
    }

    public int getDrawBackColor() {
        return mDrawBackColor;
    }

    public long getCreate() {
        return mCreate;
    }

    public int getDay() {
        return mDay;
    }

    public int getIndexNum() {
        return mIndexNum;
    }

    public int getTotalTime() {
        return mTotalTime;
    }

    public void setDay(int mDay) {
        this.mDay = mDay;
    }

    public int getPlan() {
        return mPlan;
    }

    public void setPlan(int mPlan) {
        this.mPlan = mPlan;
    }

    public int getState() {
        return mState;
    }

    public void setState(int mState) {
        this.mState = mState;
    }

    public float getProgress() {
        return mProgress;
    }

    public void setProgress(float mProgress) {
        this.mProgress = mProgress;
    }

    public int getDestory() {
        return mDestory;
    }

    public void setDestory(int destory) {
        mDestory = destory;
    }

    public int getStickState() {
        return mStickState;
    }

    public void setStickState(int stickState) {
        mStickState = stickState;
    }

    @Override
    public int compareTo(@NonNull Object another) {
        if (!(another instanceof ToDoData)) {
            return -1;
        }
        ToDoData toDoData = (ToDoData) another;
        /**置顶判断 ArrayAdapter是按照升序从上到下排序的，就是默认的自然排序  、、、、排序是降序,因为布局反转,所以看起来是升序
         * 如果是相等的情况下返回0，包括都置顶或者都不置顶，返回0的情况下要
         * 再做判断，拿它们置顶时间进行判断
         * 如果是不相等的情况下，当前是置顶的，则当前toDoData是非置顶的，应该在toDoData下面，所以返回1
         *  同样，当前是置顶的，则当前toDoData是非置顶的，应该在toDoData上面，所以返回-1
         * */
        LogUtils.e("TodoData", "mStickState ===" + mStickStateTime + "----" + "toDoData.getStickStateTime() ===" + toDoData.getStickStateTime());
        int result = 0 - (toDoData.getStickState() - mStickState);
        if (result == 0) {
            result = 0 - compareToTime(toDoData.getStickStateTime(), mStickStateTime);
        }
        return result;
    }

    /**
     * 根据时间对比
     */
    private int compareToTime(long lhs, long rhs) {
        Calendar cLhs = Calendar.getInstance();
        Calendar cRhs = Calendar.getInstance();
        cLhs.setTimeInMillis(lhs);
        cRhs.setTimeInMillis(rhs);
        return cLhs.compareTo(cRhs);
        //早,返回-1,
        //相同,返回0
        //晚,返回1
    }
}
