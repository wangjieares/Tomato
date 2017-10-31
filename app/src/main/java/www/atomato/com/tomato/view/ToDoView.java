package www.atomato.com.tomato.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import www.atomato.com.tomato.R;
import www.atomato.com.tomato.utils.ImageUtils;
import www.atomato.com.tomato.utils.ScreenUtils;

/**
 * Created by wangjie on 16-11-17.
 */

public class ToDoView extends View {
    private String tag = getClass().getSimpleName();
    // 移动的阈值
    public int TOUCH_SLOP = 100;
    //有背景
    private boolean ITEM_BACKGROUND = false;
    //item状态 完成未完成
    private int ITEM_STATUS = 1;
    //是否提醒
    private int ISREMIND = 0;
    //有边按键被按下 出现点击效果
    private boolean mKeyPress = false;
    //画笔
    private Paint mPaint;
    //尺寸
    private float mScreenHeight;
    private float mScreenWidth;
    //todo大标题
    private String mTodoTitle = "default";
    private int mDrawWidth;
    private String mTodoStart = "开始";
    //todo time
    private int mTodoTime;
    //绘制背景颜色
    private int mDrawColor;
    //x，y上个坐标
    private float mLastMotionX, mLastMotionY;
    //是否开始移动
    private boolean isMoved;
    //是否被释放
    private boolean isReleased;
    //Item背景图片
    private Bitmap bitmap;
    private int mItemClickColor;
    //绘制图片背景范围
    private Rect dst;
    //Progress绘制参数
    private int mProgress = 1;//当前的进度
    int mProgressBarFrameHeight = this.dp2px(1);
    private int mProgressBarHeight = this.dp2px(4);//进度条总高度
    int mRectWidth = 2;
    private int mProgressColor;//进度条颜色 默认白色
    private float mTouchXPostion;
    private boolean mStickState = false;
    private String mRemindTime = "20:00";


    public ToDoView(Context context) {
        super(context);
        initPaint(context);
    }

    public ToDoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint(context);
        init(attrs);
    }

    public void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ToDo);
        mTodoTitle = typedArray.getString(R.styleable.ToDo_todo_title);
        mTodoStart = typedArray.getString(R.styleable.ToDo_todo_start);
        mTodoTime = typedArray.getInteger(R.styleable.ToDo_todo_time, 35);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //设置宽度
        int mViewHeith = measureHeight(heightMeasureSpec);
        int mViewWidth = measureWidth(widthMeasureSpec);
        setMeasuredDimension(mViewWidth, mViewHeith);
//        LogUtils.e(tag, "widthMeasureSpec=>" + measureWidth(widthMeasureSpec) + "---heightMeasureSpec=>" + measureHeight(heightMeasureSpec));
    }

    private void initPaint(Context context) {
        mPaint = new Paint();
        mPaint.setTypeface(Typeface.DEFAULT);
        mPaint.setAntiAlias(true);
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/zaozi.otf");
        mPaint.setTypeface(font);
        //获取屏幕宽度高度
        mScreenWidth = ScreenUtils.getScreenWidth(context);
        mScreenHeight = ScreenUtils.getScreenHeight(context);
        mTouchXPostion = mScreenWidth - mScreenWidth / 3;//点击效果范围
        mProgressColor = Color.parseColor("#FFFFFF");//进度条颜色
        mDrawWidth = (int) mScreenWidth;//绘制控件长度
        //初始化item绘制place
//        src = new Rect(0, 0, (int) mScreenWidth, (int) mScreenHeight);
        dst = new Rect(0, 0, (int) mScreenWidth - 20, 200);
//        LogUtils.e(tag, "mScreenWidth" + mScreenWidth + "mScreenHeight" + mScreenHeight);
        //Item Color
        mItemClickColor = getResources().getColor(R.color.itemClick);
        //Item背景图片
        bitmap = ImageUtils.drawableToBitamp(getResources(), R.drawable.item_lans);
//        bitmap = ImageUtils.drawableToBitamp(getResources(), R.mipmap.card1);
    }

    public void setBackground(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (ITEM_BACKGROUND) {
            canvas.drawBitmap(bitmap, null, dst, mPaint);
//            canvas.drawColor(mDrawColor);
//            canvas.drawBitmap(bitmap, 0, 0, mPaint);
            // 该方法图片绘制受本身限制
//            canvas.drawColor(mDrawColor);
//            Bitmap bitmap = ImageUtils.drawableToBitamp(getResources(), R.drawable.item_lans);
//            canvas.drawBitmap(bitmap, 0, 0, mPaint);
        } else {
            canvas.drawColor(mDrawColor);
        }
        mPaint.setColor(Color.rgb(255, 255, 255));
        mPaint.setTextSize(sp2px(22));
//        LogUtils.e(tag, "mScreenHeight=>" + mScreenHeight + "---mScreenWidth=>" + mScreenWidth);
        canvas.drawText(mTodoTitle, (float) (mScreenWidth / 14.4), (float) (mScreenHeight / 18.2), mPaint);
        mPaint.setTextSize(sp2px(16));
        canvas.drawText(mTodoTime + "分钟", mScreenWidth / 13, mScreenHeight / 10, mPaint);
        //点击效果
        if (mKeyPress) {
            mPaint.setColor(mItemClickColor);
            //触摸点宽度 左上角开始,右下角结束
            canvas.drawRect(mTouchXPostion, 0, mDrawWidth, mDrawWidth, mPaint);
        }
        //绘制文字状态
        drawTextState(canvas);
        //初始化进度 并执行绘制
        drawProgress(canvas);
        //绘制提醒时间
        drawRemindTime(canvas);
        //边框背景
    }

    private void drawRemindTime(Canvas canvas) {
        //如果提醒，绘制时间
        if (ISREMIND == 1) {
            mPaint.setColor(Color.rgb(255, 255, 255));
            mPaint.setTextSize(sp2px(16));
//            canvas.drawText(mTodoStart, 480, 96, mPaint);
            String mTodoEnd = mRemindTime;
            canvas.drawText(mTodoEnd, mScreenWidth / 2, mScreenHeight / 10, mPaint);
        }
    }

    private void drawTextState(Canvas canvas) {
        //如果状态为假，说明该代办未完成
        if (ITEM_STATUS == 1) {
            mPaint.setColor(Color.rgb(255, 255, 255));
            mPaint.setTextSize(sp2px(16));
//            canvas.drawText(mTodoStart, 480, 96, mPaint);
            String mTodoEnd = "完成";
            canvas.drawText(mTodoEnd, (float) (mScreenWidth / 1.32), (float) (mScreenHeight / 14.8), mPaint);
        } else {
            //默认该代办未完成
            mPaint.setColor(Color.rgb(255, 255, 255));
            mPaint.setTextSize(sp2px(16));
            canvas.drawText(mTodoStart, (float) (mScreenWidth / 1.32), (float) (mScreenHeight / 14.8), mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        LogUtils.d(tag,getLeft()+"==="+getRight()+"==="+getTop()+"==="+getBottom());
        final float x = event.getX();
        final float y = event.getY();
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (x > mScreenWidth / 1.5 && x < mScreenWidth) {
                    mKeyPress = true;
                    invalidate();
                }
                mLastMotionX = x;
                mLastMotionY = y;
                isReleased = false;
                isMoved = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (x > mTouchXPostion && x < mScreenWidth) {
                    if (isMoved) {
                        break;
                    } else {
                        if (Math.abs(mLastMotionX - x) > TOUCH_SLOP
                                || Math.abs(mLastMotionY - y) > TOUCH_SLOP) {
                            // 移动超过阈值，则表示移动了
                            isMoved = true;
                            //如果长按时间执行，应该将按钮效果更新
                            mKeyPress = false;
                            invalidate();
//                            LogUtils.e(tag, tag + "===移动了");
                        }
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                if (x > mTouchXPostion && x < mScreenWidth) {
                    mKeyPress = false;
                    invalidate();
                } else {
                    mKeyPress = false;
                    invalidate();
                }
                // 释放了
                isReleased = true;
                break;
        }
//        return super.onTouchEvent(event);
        return true;
    }

    private int measureWidth(int widthMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = (int) mScreenWidth;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = (int) (mScreenHeight / 8.5);
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    public void setTodoTitle(String mTodoTitle) {
        this.mTodoTitle = mTodoTitle;
    }

    public void setTodoTime(int mTodoTime) {
        this.mTodoTime = mTodoTime;
    }

    public void setDrawColor(int mDrawColor) {
        this.mDrawColor = mDrawColor;
    }

    private void drawProgress(Canvas canvas) {
        canvas.save();
        mPaint.setStrokeWidth(mProgressBarFrameHeight);
        //移动到第一个半圆圆心
        float mRadius = 5;
        canvas.translate(mRadius + mProgressBarFrameHeight, mProgressBarHeight / 2);
        //进度条实心
        mPaint.setStyle(Paint.Style.FILL);
        float cy = (float) (mScreenHeight / 11.5);
        float cx = (float) (mScreenWidth/3.5);
        canvas.drawCircle(cx, cy, mRadius, mPaint);
        RectF rectF_Center = new RectF(cx, cy -mRadius, mProgress+cx, cy +mRadius);
        canvas.drawRect(rectF_Center, mPaint);
        canvas.drawCircle(mProgress+cx, cy, mRadius, mPaint);
        canvas.restore();
    }

    public void setProgress(int progress) {
        mProgress = progress;
        invalidate();
    }

    public int dp2px(int dpValue) {
        //转化标准尺寸
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }

    public int sp2px(int spValue) {
        //转化标准尺寸
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, getResources().getDisplayMetrics());
    }

    public String getTodoTitle() {
        return mTodoTitle;
    }

    public void setItemState(int itemState) {
        this.ITEM_STATUS = itemState;
    }

    //设置背景色
    public void setProgressColor(int progressColor) {
        mProgressColor = progressColor;
        invalidate();
    }

    public void setTodoStart(String mTodoStart) {
        this.mTodoStart = mTodoStart;
    }

    public int getDrawColor() {
        return mDrawColor;
    }

    public void setStickState(boolean stickState) {
        mStickState = stickState;
    }

    public void setmRemindTime(String mRemindTime) {
        this.mRemindTime = mRemindTime;
    }

    public void setItemBackground(boolean ITEM_BACKGROUND) {
        this.ITEM_BACKGROUND = ITEM_BACKGROUND;
    }
}
