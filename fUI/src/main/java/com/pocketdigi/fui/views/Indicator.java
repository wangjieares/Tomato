package com.pocketdigi.fui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;

import com.pocketdigi.fui.R;

public abstract class Indicator extends View {
	// 页面总数
	protected int childSum = 4;
	// 动画持续时间，单位毫秒
	protected int aniDuration = 200;

	protected int currentIndex;
	// 一项的宽度
	protected int childWidth = 0;
	// 每桢间隔,毫秒
	protected int durationPerFrame = 0;
	// 每桢移动像素数
	protected int pixelPerFrame = 10;
	protected int frameCount = 0;
	// 目标位置x座标
	protected int targetX = 0;

	protected boolean running = false;
	CountDownTimer countDown;
	TabBar tabBar;

	public Indicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Indicator);
		childSum = typedArray.getInt(R.styleable.Indicator_childSum, 0);
		aniDuration = typedArray.getInt(R.styleable.Indicator_aniDuration, 200);
		typedArray.recycle();
	}
	public int getChildSum() {
		return childSum;
	}

	/**
	 * 设置页面数量
	 * 
	 * @param childSum
	 */
	public void setChildSum(int childSum) {
		this.childSum = childSum;
	}

	public int getAniDuration() {
		return aniDuration;
	}

	/**
	 * 设置动画持续时间 单位毫秒
	 * 
	 * @param aniDuration
	 */
	public void setAniDuration(int aniDuration) {
		this.aniDuration = aniDuration;
	}


}
