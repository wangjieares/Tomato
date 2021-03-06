package www.atomato.com.tomato.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import www.atomato.com.tomato.R;
import www.atomato.com.tomato.constants.Constants;
import www.atomato.com.tomato.utils.LogUtils;
import www.atomato.com.tomato.utils.ScreenUtils;

/**
 * 自定义圆形倒计时
 */
public class CountDownTimerView extends View {

    //默认圆
    private int defaultCircleSolideColor = Constants.DEFAULT_CIRCLE_SOLIDE_COLOR;
    private int defaultCircleStrokeColor = Constants.DEFAULT_CIRCLE_STROKE_COLOR;
    private int defaultCircleStrokeWidth = dp2px(Constants.DEFAULT_CIRCLE_STROKE_WIDTH);
    private int defaultCircleRadius = dp2px(Constants.DEFAULT_CIRCLE_RADIUS);//半径
    //进度条
    private int progressColor = Constants.PROGRESS_COLOR;
    private int progressWidth = dp2px(Constants.PROGRESS_WIDTH);
    //默认圆边框上面的小圆
    private int smallCircleSolideColor = Constants.SMALL_CIRCLE_SOLIDE_COLOR;
    private int smallCircleStrokeColor = Constants.SMALL_CIRCLE_STROKE_COLOR;
    private int smallCircleStrokeWidth = dp2px(Constants.SMALL_CIRCLE_STROKE_WIDTH);
    private int smallCircleRadius = dp2px(Constants.SMALL_CIRCLE_RADIUS);
    //最里层的文字
    private int textColor = Constants.TEXT_COLOR;
    private int textSize = sp2px(Constants.TEXT_SIZE);
    //画笔
    private Paint defaultCriclePaint;
    private Paint progressPaint;
    private Paint smallCirclePaint;//画小圆边框的画笔
    private Paint textPaint;
    private Paint smallCircleSolidePaint;//画小圆的实心的画笔

    //圆弧开始的角度
    private float mStartSweepValue = -90;
    //当前的角度
    private float currentAngle;

    public long getCountdownTime() {
        return countdownTime;
    }

    //提供一个外界可以设置的倒计时数值，毫秒值
    private long countdownTime;

    public CountDownTimer getCountDownTimer() {
        return countDownTimer;
    }

    private CountDownTimer countDownTimer;
    private boolean isNext;
    //中间文字描述
    private String textDesc;
    //    private String textDesc;
    //小圆运动路径Path
    private Path mPath;
    //圆外边的矩形
    private RectF mRectF;
    //额外距离
    private float extraDistance = 0.7F;
    private long mSecord;
    private long mMintue;
    private ValueAnimator animator;

    public CountDownTimerView(Context context) {
        this(context, null);
    }

    public CountDownTimerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownTimerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CountDownView);
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.CountDownView_default_circle_solide_color:
                    defaultCircleSolideColor = typedArray.getColor(attr, defaultCircleSolideColor);
                    break;
                case R.styleable.CountDownView_default_circle_stroke_color:
                    defaultCircleStrokeColor = typedArray.getColor(attr, defaultCircleStrokeColor);
                    break;
                case R.styleable.CountDownView_default_circle_stroke_width:
                    defaultCircleStrokeWidth = (int) typedArray.getDimension(attr, defaultCircleStrokeWidth);
                    break;
                case R.styleable.CountDownView_default_circle_radius:
                    defaultCircleRadius = (int) typedArray.getDimension(attr, defaultCircleRadius);
                    break;
                case R.styleable.CountDownView_progress_color:
                    progressColor = typedArray.getColor(attr, progressColor);
                    break;
                case R.styleable.CountDownView_progress_width:
                    progressWidth = (int) typedArray.getDimension(attr, progressWidth);
                    break;
                case R.styleable.CountDownView_small_circle_solide_color:
                    smallCircleSolideColor = typedArray.getColor(attr, smallCircleSolideColor);
                    break;
                case R.styleable.CountDownView_small_circle_stroke_color:
                    smallCircleStrokeColor = typedArray.getColor(attr, smallCircleStrokeColor);
                    break;
                case R.styleable.CountDownView_small_circle_stroke_width:
                    smallCircleStrokeWidth = (int) typedArray.getDimension(attr, smallCircleStrokeWidth);
                    break;
                case R.styleable.CountDownView_small_circle_radius:
                    smallCircleRadius = (int) typedArray.getDimension(attr, smallCircleRadius);
                    break;
                case R.styleable.CountDownView_text_color:
                    textColor = typedArray.getColor(attr, textColor);
                    break;
                case R.styleable.CountDownView_text_size:
                    textSize = (int) typedArray.getDimension(attr, textSize);
                    break;
            }
        }
        //回收typedArray对象
        typedArray.recycle();
        //设置画笔
        defaultCircleRadius = ScreenUtils.getScreenWidth(context) / 3;
        setPaint();
    }

    private void setPaint() {
        //默认圆
        defaultCriclePaint = new Paint();
        defaultCriclePaint.setAntiAlias(true);//抗锯齿
        defaultCriclePaint.setDither(true);//防抖动
        defaultCriclePaint.setStyle(Paint.Style.STROKE);
        defaultCriclePaint.setStrokeWidth(defaultCircleStrokeWidth);
        defaultCriclePaint.setColor(defaultCircleStrokeColor);//这里先画边框的颜色，后续再添加画笔画实心的颜色
        //默认圆上面的进度弧度
        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setDither(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(progressWidth);
        progressPaint.setColor(progressColor);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);//设置画笔笔刷样式
        //进度上面的小圆
        smallCirclePaint = new Paint();
        smallCirclePaint.setAntiAlias(true);
        smallCirclePaint.setDither(true);
        smallCirclePaint.setStyle(Paint.Style.STROKE);
        smallCirclePaint.setStrokeWidth(smallCircleStrokeWidth);
        smallCirclePaint.setColor(smallCircleStrokeColor);
        //画进度上面的小圆的实心画笔（主要是将小圆的实心颜色设置成白色）
        smallCircleSolidePaint = new Paint();
        smallCircleSolidePaint.setAntiAlias(true);
        smallCircleSolidePaint.setDither(true);
        smallCircleSolidePaint.setStyle(Paint.Style.FILL);
        smallCircleSolidePaint.setColor(smallCircleSolideColor);

        //文字画笔
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);

        mRectF = new RectF(0, 0, defaultCircleRadius * 2, defaultCircleRadius * 2);//可能出错
    }

    public void setNext(boolean next) {
        isNext = next;
    }

    /**
     * 如果该View布局的宽高开发者没有精确的告诉，则需要进行测量，如果给出了精确的宽高则我们就不管了
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize;
        int heightSize;
        int strokeWidth = Math.max(defaultCircleStrokeWidth, progressWidth);
        if (widthMode != MeasureSpec.EXACTLY) {
            widthSize = getPaddingLeft() + defaultCircleRadius * 2 + strokeWidth + getPaddingRight();
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        }
        if (heightMode != MeasureSpec.EXACTLY) {
            heightSize = getPaddingTop() + defaultCircleRadius * 2 + strokeWidth + getPaddingBottom();
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());
        //画默认圆
//        canvas.drawCircle(defaultCircleRadius, defaultCircleRadius, defaultCircleRadius, defaultCriclePaint);
        //画进度圆弧
        //currentAngle = getProgress()*1.0f/getMax()*360;
        canvas.drawArc(mRectF, mStartSweepValue, 360 * currentAngle, false, progressPaint);
        //画中间文字
        //   String text = getProgress()+"%";
        //获取文字的长度的方法
        if (textDesc != null) {
            float textWidth = textPaint.measureText(textDesc);
            float textHeight = (textPaint.descent() + textPaint.ascent()) / 2;
            canvas.drawText(textDesc, defaultCircleRadius - textWidth / 2, defaultCircleRadius - textHeight, textPaint);
        }
        //画小圆
        float currentDegreeFlag = 360 * currentAngle + extraDistance;
        float smallCircleX, smallCircleY;
        float radian = (float) Math.abs(Math.PI * currentDegreeFlag / 180);//Math.abs：绝对值 ，Math.PI：表示π ， 弧度 = 度*π / 180
        smallCircleX = (float) Math.abs(Math.sin(radian) * defaultCircleRadius + defaultCircleRadius);
        smallCircleY = (float) Math.abs(defaultCircleRadius - Math.cos(radian) * defaultCircleRadius);
        canvas.drawCircle(smallCircleX, smallCircleY, smallCircleRadius, smallCirclePaint);
        canvas.drawCircle(smallCircleX, smallCircleY, smallCircleRadius - smallCircleStrokeWidth, smallCircleSolidePaint);//画小圆的实心
        canvas.restore();
    }

    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    protected int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());
    }

    //提供一个外界可以设置的倒计时数值
    public void setCountdownTime(long countdownTime) {
        this.countdownTime = countdownTime;
        mMintue = countdownTime / 1000 / 60;//分钟
        //2*60*1000=119000
        mSecord = countdownTime / 1000 % 60;
        textDesc = mMintue + ":" + mSecord;
        LogUtils.e("CountDownTimerView=time", textDesc);
    }

    public void onRestartAni(OnCountdownFinishListener onCountdownFinishListener, long countdownTime) {
        this.countdownTime = countdownTime;
        mMintue = countdownTime / 1000 / 60;//分钟
        //2*60*1000=119000
        mSecord = countdownTime / 1000 % 60;
        textDesc = mMintue + ":" + mSecord;
        startCountDownTime(onCountdownFinishListener);
    }

    //属性动画
    public void startCountDownTime(final OnCountdownFinishListener countdownFinishListener) {
        setClickable(false);//不能点击
        animator = ValueAnimator.ofFloat(1.0f, 0);
//        ValueAnimator animator = ValueAnimator.ofFloat(0, 1.0f);
        //动画时长，让进度条在CountDown时间内正好从0-360走完，这里由于用的是CountDownTimer定时器，倒计时要想减到0则总时长需要多加1000毫秒，所以这里时间也跟着+1000ms
        animator.setDuration(countdownTime*2);//不知道为什么 手机执行时间少一半
//        animator.setDuration(countdownTime);//不知道为什么 手机执行时间少一半
//        animator.setDuration(countdownTime + 1000);
        animator.setInterpolator(new LinearInterpolator());//匀速
        animator.setRepeatCount(0);//表示不循环，-1表示无限循环
        //值从0-1.0F 的动画，动画时长为countdownTime，ValueAnimator没有跟任何的控件相关联，那也正好说明ValueAnimator只是对值做动画运算，而不是针对控件的，我们需要监听ValueAnimator的动画过程来自己对控件做操作
        //添加监听器,监听动画过程中值的实时变化(animation.getAnimatedValue()得到的值就是0-1.0)
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                /**
                 * 这里我们已经知道ValueAnimator只是对值做动画运算，而不是针对控件的，因为我们设置的区间值为0-1.0f
                 * 所以animation.getAnimatedValue()得到的值也是在[0.0-1.0]区间，而我们在画进度条弧度时，设置的当前角度为360*currentAngle，
                 * 因此，当我们的区间值变为1.0的时候弧度刚好转了360度
                 */
                currentAngle = (float) animation.getAnimatedValue();
//                currentAngle = (float) animation.getAnimatedValue();
                invalidate();//实时刷新view，这样我们的进度条弧度就动起来了
            }
        });
        //还需要另一个监听，监听动画状态的监听器
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                LogUtils.e(getClass().getName(), "start");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //倒计时结束的时候，需要通过自定义接口通知UI去处理其他业务逻辑
                if (countdownFinishListener != null) {
                    countdownFinishListener.performFinished();
                }
//                if (countdownTime > 0) {
//                    setClickable(true);
//                } else {
//                    setClickable(false);
//                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animator.start();
        //调用倒计时操作
        countdownMethod();
    }

    public void stopTime() {
        animator.pause();
    }
    public void resumeTime(){
        animator.resume();
        countdownMethod();
    }

    //倒计时的方法
    private void countdownMethod() {
        countDownTimer = new CountDownTimer(countdownTime + 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //         Log.e("time",countdownTime+"");
                countdownTime = countdownTime - 1000;
//                textDesc = countdownTime / 1000 + "″";
                //2*60*1000=120000
                mMintue = countdownTime / 1000 / 60;//分钟
                //2*60*1000=119000
                mSecord = countdownTime / 1000 % 60;//秒
                if (mSecord < 10) {
                    textDesc = mMintue + ":0" + mSecord;
                } else {
                    textDesc = mMintue + ":" + mSecord;
                }
                //countdownTime = countdownTime-1000;
//                LogUtils.e("time", countdownTime + "");
                //刷新view
                invalidate();
            }

            @Override
            public void onFinish() {
                if (!isNext) {
                    //textDesc = 0 + "″";
                    textDesc = "已完成";
                    //同时隐藏小球
                    smallCirclePaint.setColor(getResources().getColor(android.R.color.transparent));
                    smallCircleSolidePaint.setColor(getResources().getColor(android.R.color.transparent));
                    //刷新view
                    invalidate();
                }
            }
        }.start();
    }

    //通过自定义接口通知UI去处理其他业务逻辑
    public interface OnCountdownFinishListener {
        void performFinished();
    }

}
