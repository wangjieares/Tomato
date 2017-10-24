package www.atomato.com.tomato.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import www.atomato.com.tomato.R;
import www.atomato.com.tomato.constants.Constants;
import www.atomato.com.tomato.utils.ImageUtils;
import www.atomato.com.tomato.utils.ScreenUtils;

/**
 * Created by wangjie on 16-11-17.
 */

public class ToDoView extends View {
    private String tag = getClass().getSimpleName();
    // 移动的阈值
    public int TOUCH_SLOP = 20;
    //有背景
    private boolean ITEM_BACKGROUND = false;
    //item状态 完成未完成
    private int ITEM_STATUS = 1;
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
    private float mProgress = 0.01f;//当前的进度
    private float mProgressLoadingWidth;//当前进度条宽度
    private float mProgressMaxWidth;//进度最大宽度
    int mProgressBarFrameHeight = this.dp2px(0);
    private int mProgressBarHeight = this.dp2px(4);//进度条总高度
    int mRectWidth = 2;
    private float mRadius = 5;//进度条内左右两个半圆的最大半径
    private float mOneArcProgress;//半圆占用的最大的进度
    private int mProgressColor;//进度条颜色 默认白色
    private float mTouchXPostion;
    private boolean mStickState = false;

    public boolean getStickState() {
        return mStickState;
    }

    public void setStickState(boolean stickState) {
        mStickState = stickState;
    }

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
        canvas.drawText(mTodoTitle, (float) (mScreenWidth/14.4), (float) (mScreenHeight/18.2), mPaint);
        mPaint.setTextSize(sp2px(16));
        canvas.drawText(mTodoTime + "分钟", mScreenWidth/13, mScreenHeight/10, mPaint);
        //点击效果
        if (mKeyPress) {
            mPaint.setColor(mItemClickColor);
            //触摸点宽度 左上角开始,右下角结束
            canvas.drawRect(mTouchXPostion, 0, mDrawWidth, mDrawWidth, mPaint);
        }
        //绘制文字状态
        drawTextState(canvas);
        //默认进度
        drawProgress(canvas);
    }
    private void drawTextState(Canvas canvas) {
        //如果状态为假，说明该代办未完成
        if (ITEM_STATUS == 1) {
            mPaint.setColor(Color.rgb(255, 255, 255));
            mPaint.setTextSize(sp2px(16));
//            canvas.drawText(mTodoStart, 480, 96, mPaint);
            String mTodoEnd = "完成";
            canvas.drawText(mTodoEnd, (float) (mScreenWidth / 1.32), (float) (mScreenHeight/14.8), mPaint);
        } else {
            //默认该代办未完成
            mPaint.setColor(Color.rgb(255, 255, 255));
            mPaint.setTextSize(sp2px(16));
            canvas.drawText(mTodoStart, (float) (mScreenWidth / 1.32), (float) (mScreenHeight/14.8), mPaint);
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
            result = (int) (mScreenHeight/8.5);
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

    private void initProgress() {
        /**
         * 处理笔触的大小
         */
        int mProgressBarWidth = Constants.PROGRESSBAR_WIDTH;
        int mProgressBarWidthWithoutFrame = mProgressBarWidth - mProgressBarFrameHeight * 2;
        int mProgressBarHeightWithoutFrame = mProgressBarHeight - mProgressBarFrameHeight * 2;
        //
        mRadius = mProgressBarHeightWithoutFrame / 2;
        //
        mRectWidth = (int) (mProgressBarWidthWithoutFrame - 2 * mRadius);//矩形的宽度
        mProgressMaxWidth = mProgressBarWidthWithoutFrame;
        mOneArcProgress = mRadius / mProgressBarWidth;//半圆最大的 进度

        mProgressLoadingWidth = mProgressMaxWidth * mProgress;
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mProgressColor);
    }

    public void setProgress(float progress) {
        mProgress = progress / 100;
        invalidate();
    }

    private void drawLeftArc(Canvas canvas) {
        canvas.save();
        float mProgressXPositon = 170;
        float mProgressYPositon = 120;
        canvas.translate(mProgressXPositon, mProgressYPositon);
        float leftArcWidth = mProgressLoadingWidth < mRadius ? mProgressLoadingWidth : mRadius;//当前进度条不能超过左边圆的半径
        RectF rectF = new RectF(-mRadius, -mRadius, mRadius, mRadius);
        /**
         * ∠A 指的是  x轴和竖直切线的夹角  demo图见 https://code.aliyun.com/hi31588535/outside_chain/raw/master/blog_custom_view_show_pic.png
         */
        double LinBian = mRadius - leftArcWidth;//直角三角形∠A邻边
        double cosValue = LinBian / mRadius;//cosA=邻边/斜边

        double radian = Math.acos(cosValue);//反余弦   返回值单位是弧度
        // 用角度表示的角
        double angle = Math.toDegrees(radian);//转化角度

        int mStartAngle_LeftArc = 90;
        float startAngle = (float) (mStartAngle_LeftArc + (90 - angle));
        float sweepAngle = (float) angle * 2;

        // Log.d(TAG, "onDraw: angle" + angle);//直角三角形 锐角A （∠A的） sinA=对边/斜边  cosA=邻边/斜边  tanA=对边/邻边
        canvas.drawArc(rectF, startAngle, sweepAngle, false, mPaint);
    }

    private void drawCenterRect(Canvas canvas) {
        float rectAndLeftArcMaxWidth = mProgressMaxWidth - mRadius;//所有进度条减去右边 就是左边和矩形
        float progressBarWidthNowTemp = mProgressLoadingWidth < rectAndLeftArcMaxWidth ? mProgressLoadingWidth : rectAndLeftArcMaxWidth;
        float rectWidth = progressBarWidthNowTemp - mRadius;//当前进度条减去左边半圆
        rectWidth = rectWidth < rectAndLeftArcMaxWidth ? rectWidth : rectAndLeftArcMaxWidth;
        RectF rectFCenter = new RectF(0, -mRadius, rectWidth, mRadius);
        canvas.drawRect(rectFCenter, mPaint);
    }

    private void drawRightArc(Canvas canvas) {
        float rectAndLeftArcMaxWidth = mProgressMaxWidth - mRadius;//所有进度条减去右边 就是左边和矩形

        float progressBarWidthNowTemp = mProgressLoadingWidth < mProgressMaxWidth ? mProgressLoadingWidth : mProgressMaxWidth;

        float rightArcWidth = progressBarWidthNowTemp - rectAndLeftArcMaxWidth;//当前进度条减去左边半圆和矩形

        float rectWidth = rectAndLeftArcMaxWidth - mRadius;

        canvas.translate(rectWidth, 0);//

        RectF rectF = new RectF(-mRadius, -mRadius, mRadius, mRadius);

        double cosValue = (double) rightArcWidth / mRadius;//cosB=邻边/斜边

        double radian = Math.acos(cosValue);//反余弦   返回值单位是弧度
        // 用角度表示的角
        double angle = Math.toDegrees(radian);//转化角度

        float sweepAngle = (float) (90 - angle);

        int mStartAngle_RightArc_One = -90;
        float startAngleOne = (float) mStartAngle_RightArc_One;
        int mStartAngle_RightArc_Two = 0;
        float startAngleTwo = (float) (mStartAngle_RightArc_Two + angle);


        canvas.drawArc(rectF, startAngleOne, sweepAngle, true, mPaint);//绘制上面的圆弧
        canvas.drawArc(rectF, startAngleTwo, sweepAngle, true, mPaint);//绘制下面的圆弧

        //画三角形
        Path pathTriangle = new Path();
        double DuiBian = Math.sqrt((mRadius * mRadius - (double) rightArcWidth * (double) rightArcWidth));//开平方   邻边的平方加上对边的平方的斜边的平方
        pathTriangle.moveTo(0, 0);
        pathTriangle.lineTo((float) (double) rightArcWidth, (float) DuiBian);
        pathTriangle.lineTo((float) (double) rightArcWidth, -(float) DuiBian);
        pathTriangle.close();
        canvas.drawPath(pathTriangle, mPaint);

    }

    private void drawProgress(Canvas canvas) {
        if (mProgress <= 0) {
            return;
        }
        if (mProgress <= mOneArcProgress) {
            drawLeftArc(canvas);

        } else if (mProgress > mOneArcProgress && mProgress <= (1 - mOneArcProgress)) {
            drawLeftArc(canvas);
            drawCenterRect(canvas);

        } else {
            drawLeftArc(canvas);
            drawCenterRect(canvas);
            drawRightArc(canvas);
        }
        canvas.restore();
    }

    public void setProgressColor(int progressColor) {
        mProgressColor = progressColor;
        invalidate();
    }

    public int dp2px(int dpValue) {
        //转化标准尺寸
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
        return px;
    }
    public int sp2px(int spValue) {
        //转化标准尺寸
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, getResources().getDisplayMetrics());
        return px;
    }
    public int getTodoTime() {
        return mTodoTime;
    }

    public String getTodoStart() {
        return mTodoStart;
    }

    public void setTodoStart(String mTodoStart) {
        this.mTodoStart = mTodoStart;
    }

    public String getTodoTitle() {
        return mTodoTitle;
    }

    public int getDrawColor() {
        return mDrawColor;
    }

    public void setItemState(int itemState) {
        this.ITEM_STATUS = itemState;
    }

    public void setItemBackground(boolean ITEM_BACKGROUND) {
        this.ITEM_BACKGROUND = ITEM_BACKGROUND;
    }
}
