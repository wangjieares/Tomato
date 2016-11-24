package www.atomato.com.tomato.data;

import android.content.Context;
import android.graphics.Color;

import www.atomato.com.tomato.constants.Constants;
import www.atomato.com.tomato.sqlite.ViewSQLite;

/**
 * Created by wangjie on 16-11-17.
 */
public class ToDoData {
    private String mTitle;
    private int mTime;
    private int mDrawBackColor = Color.RED;
    private float mProgress = 0f;
    private int mState = 0;
    private int mDay = Constants.EVERY_DAY_RADIO;
    private int mPlan = Constants.TIME_RADIO;
    private int mType = Constants.LONG_RADIO;
    private ViewSQLite viewSQLite;

    public ToDoData(Context context, String title, int time, int state, float progress, int drawColor, int day, int plan, int type) {
        this.mTitle = title;
        this.mTime = time;
        this.mDrawBackColor = drawColor;
        this.mProgress = progress;
        this.mState = state;
        this.mDay = day;
        this.mPlan = plan;
        this.mType = type;
        try {
            viewSQLite = new ViewSQLite(context);
            viewSQLite.insert(this);
        }finally {
            viewSQLite.closedb();
        }
    }

    public ToDoData(String title, int time, int mState, float mProgress, int drawBackColor) {
        this.mTitle = title;
        this.mTime = time;
        this.mDrawBackColor = drawBackColor;
        this.mProgress = mProgress;
        this.mState = mState;
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

    public int getDay() {
        return mDay;
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
}
