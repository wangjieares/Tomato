package com.pocketdigi.fui.views;

import com.pocketdigi.fui.views.TabBar.OnTabChangeListener;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.util.AttributeSet;
/**
 * 白线指示器，可以绑定tabBar,当tarBar的tab改变时会有动画效果
 * @author fhp
 *
 */
public class LineIndicator extends Indicator {

	Paint paint;
	public LineIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint=new Paint();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO 自动生成的方法存根
		super.onDraw(canvas);
		paint.setColor(Color.WHITE);
		if (childWidth == 0) {
			childWidth = getWidth() / childSum;
		}
		canvas.drawRect(targetX, 0, targetX+childWidth, this.getHeight(), paint);
	}
	
	/**
	 * 切换页面调用，从0开始
	 * 
	 * @param pageIndex
	 *            0～childSum-1
	 */
	public final void switchTo(final int pageIndex) {
		if (currentIndex == pageIndex || pageIndex > childSum - 1 || pageIndex < 0) {
			return;
		}
		if (running) {
			countDown.cancel();
		}
		// 最终位置
		final int finalTargetX = pageIndex * childWidth;
		if (targetX == 0)
			targetX = currentIndex * childWidth;
		// 计算需要移动的宽度
		final float moveWidth = (pageIndex - currentIndex) * childWidth;
		frameCount = Math.abs((int) moveWidth / pixelPerFrame);
		// 每桢持续时间
		durationPerFrame = Math.abs(aniDuration / frameCount);
		countDown = new CountDownTimer(aniDuration, durationPerFrame) {

			public void onTick(long millisUntilFinished) {
				// 计算接下来移动的目标位置
				// 剩余需要移动多少像素
				int leftPixels = (int) (millisUntilFinished / (float) aniDuration * moveWidth);
				targetX = finalTargetX - leftPixels;
				invalidate();
			}

			public void onFinish() {
				targetX = finalTargetX;
				invalidate();
				currentIndex = pageIndex;
			}
		}.start();
	}
	
	/**
	 * 设置被附加的TabBar 功能：获取tabBar的Button数量，自动添加tabBar监听器
	 * 
	 * @param tabBar
	 */
	public void setTabBar(TabBar tabBar) {
		this.tabBar = tabBar;
		childSum = tabBar.getChildCount();
		tabBar.addOnTabChangeListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(int tabId, int checkedIndex) {
				switchTo(checkedIndex);
			}
		});
	}
}
