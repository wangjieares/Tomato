package www.atomato.com.tomato.data;

import android.content.Context;

import www.atomato.com.tomato.sqlite.ViewSQLite;

/**
 * Created by wangjie on 16-11-17.
 */
public class GroupItem {
    private String mGroupId="0"; //不能为默认0
    private String mTitle;//标题
    private long mTime;//时间
    private int mDrawBackColor;//绘制背景
    private float mProgress = 0f;//进度
    private int mState = 0;//完成状态
    private int mStickState = 0;//是否置顶
    private long mCreate;//创建时间
    private long mStickStateTime;//置顶时间
    private int mTotalTime = 0;//总时间
    private int mPlanTime = 350;//计划完成时间
    private int mDestory = 0;//销毁时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public int getmPlanNum() {
        return mPlanNum;
    }

    public void setmPlanNum(int mPlanNum) {
        this.mPlanNum = mPlanNum;
    }

    public int getmTotalNum() {
        return mTotalNum;
    }

    public void setmTotalNum(int mTotalNum) {
        this.mTotalNum = mTotalNum;
    }

    private int mPlanNum = 0;//计划完成个数
    private int mTotalNum = 0;//总完成个数
    private Context mContext;
    private ViewSQLite viewSQLite;

    public GroupItem(String title, long time, int mState, float mProgress, int drawBackColor) {
        this.mTitle = title;
        this.mTime = time;
        this.mDrawBackColor = drawBackColor;
        this.mProgress = mProgress;
        this.mState = mState;
        mDrawBackColor = ColorConstants.randomBackground();
        mCreate = System.currentTimeMillis();
        mStickStateTime = 0;
    }

    public GroupItem(Context context, String title, int time, int state, float progress, int drawColor, int longPlan) {
        mContext = context;
        this.mTitle = title;
        this.mTime = time;
        this.mDrawBackColor = drawColor;
        this.mProgress = progress;
        this.mState = state;
        this.mPlanTime = longPlan;
        mDrawBackColor = ColorConstants.randomBackground();
        mCreate = System.currentTimeMillis();
        mStickStateTime = 0;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getTitle() {
        return mTitle;
    }

    public long getStickStateTime() {
        return mStickStateTime;
    }

    public int getPlanTime() {
        return mPlanTime;
    }

    public void setTime(long mTime) {
        this.mTime = mTime;
    }

    public void setProgress(float progress) {
        this.mProgress = progress;
    }

    public long getTime() {
        return mTime;
    }

    public int getDrawBackColor() {
        return mDrawBackColor;
    }

    public long getCreate() {
        return mCreate;
    }

    public int getTotalTime() {
        return mTotalTime;
    }

    public int getState() {
        return mState;
    }

    public float getProgress() {
        return mProgress;
    }

    public int getDestory() {
        return mDestory;
    }

    public int getStickState() {
        return mStickState;
    }

    public void setStickState(int stickState) {
        mStickState = stickState;
    }

    public String getmGroupName() {
        return mGroupId;
    }

    public void setmGroupName(String mGroupName) {
        this.mGroupId = mGroupName;
    }

    @Override
    public String toString() {
        return "GroupItem{" +
                "mTitle='" + mTitle + '\'' +
                ", mTime=" + mTime +
                ", mDrawBackColor=" + mDrawBackColor +
                ", mProgress=" + mProgress +
                ", mState=" + mState +
                ", mStickState=" + mStickState +
                ", mCreate=" + mCreate +
                ", mStickStateTime=" + mStickStateTime +
                ", mTotalTime=" + mTotalTime +
                ", mPlanTime=" + mPlanTime +
                ", mDestory=" + mDestory +
                ", mPlanNum=" + mPlanNum +
                ", mTotalNum=" + mTotalNum +
                ", mContext=" + mContext +
                ", viewSQLite=" + viewSQLite +
                '}';
    }
}
