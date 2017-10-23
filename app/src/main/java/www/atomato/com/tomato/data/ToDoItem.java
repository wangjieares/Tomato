package www.atomato.com.tomato.data;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Calendar;

import www.atomato.com.tomato.constants.Constants;
import www.atomato.com.tomato.sqlite.ViewSQLite;

/**
 * Created by wangjie on 16-11-17.
 */
public class ToDoItem implements Serializable, Comparable {
    private String mTitle;//标题
    private int mTime;//时间
    private int mDrawBackColor;//绘制北京
    private float mProgress = 0f;//进度
    private int mState = 0;//完成状态
    private int mStickState = 0;//是否置顶
    private long mCreate;//创建时间
    private long mStickStateTime;//置顶时间
    private int mTotalTime = 0;//总时间
    private int mPlanTime = 350;//计划完成时间
    private int mDestory = 0;//销毁时间

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

    public ToDoItem(String title, int time, int mState, float mProgress, int drawBackColor) {
        this.mTitle = title;
        this.mTime = time;
        this.mDrawBackColor = drawBackColor;
        this.mProgress = mProgress;
        this.mState = mState;
    }

    public ToDoItem(Context context, String title, int time, int state, float progress, int drawColor, int longPlan) {
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

    public void setTime(int mTime) {
        this.mTime = mTime;
    }

    public int getTime() {
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

//    public void setPlanTime(int planTime) {
//        mPlanTime = planTime;
//    }
//
//    public void setStickStateTime(long stickStateTime) {
//        mStickStateTime = stickStateTime;
//    }
//
//    public void setDrawBackColor(int drawBackColor) {
//        this.mDrawBackColor = drawBackColor;
//    }
//
//    public void setState(int mState) {
//        this.mState = mState;
//    }
//
//    public void setProgress(float mProgress) {
//        this.mProgress = mProgress;
//    }
//
//    public void setDestory(int destory) {
//        mDestory = destory;
//    }

    @Override
    public int compareTo(@NonNull Object another) {
        if (!(another instanceof ToDoItem)) {
            return -1;
        }
//        ToDoData toDoData = (ToDoData) another;
        /**置顶判断 ArrayAdapter是按照升序从上到下排序的，就是默认的自然排序  、、、、排序是降序,因为布局反转,所以看起来是升序
         * 如果是相等的情况下返回0，包括都置顶或者都不置顶，返回0的情况下要
         * 再做判断，拿它们置顶时间进行判断
         * 如果是不相等的情况下，当前是置顶的，则当前toDoData是非置顶的，应该在toDoData下面，所以返回1
         *  同样，当前是置顶的，则当前toDoData是非置顶的，应该在toDoData上面，所以返回-1
         * */
        ViewSQLite viewSQLite = new ViewSQLite(mContext);
        Cursor cursor1 = viewSQLite.query(Constants.TABLE_NAME, new String[]{"todo_stick_time"}, "todo_title=?", new String[]{((ToDoItem) another).getTitle()}, null, null, null);
        Cursor cursor2 = viewSQLite.query(Constants.TABLE_NAME, new String[]{"todo_stick_time"}, "todo_title=?", new String[]{mTitle}, null, null, null);
        cursor1.moveToNext();
        cursor2.moveToNext();
        int num1 = cursor1.getInt(cursor1.getColumnIndex("todo_stick_time"));
        int num2 = cursor2.getInt(cursor2.getColumnIndex("todo_stick_time"));
//        LogUtils.e("TodoData", "oldTitle ===" + ((ToDoData) another).getTitle() + "----" + "currentTitle===" + mTitle);
//        LogUtils.e("TodoData", "oldTitleNum1 ===" + num1 + "----" + "currentTitleNum2===" + num2);
//        LogUtils.e("TodoData", "oldTitleState ===" + toDoData.getStickState() + "----" + "currentTitleState===" + mStickState);
//        LogUtils.e("ToDoDate", "===num1===" + num1 + "===num2===" + num2);
        int result = 0 - (((ToDoItem) another).getStickState() - mStickState);
        if (result == 0) {
            result = compareToTime(num1, num2);
        }
        return result;
    }

    /**
     * 根据时间对比
     */
    private int compareToTime(long lhs, long rhs) {
        Calendar Lhs = Calendar.getInstance();
        Calendar Rhs = Calendar.getInstance();
        Lhs.setTimeInMillis(lhs);
        Rhs.setTimeInMillis(rhs);
//        LogUtils.e("ToDoDate", "===Lhs.compareTo(Rhs)===" + Lhs.compareTo(Rhs));
        return Lhs.compareTo(Rhs);
    }
}
