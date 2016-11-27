package com.pocketdigi.fui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Paint.FontMetrics;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.pocketdigi.fui.R;

/**
 * TabBar的按钮
 * 
 * @author fhp
 * 
 */
public class TabButton extends RadioButton {
	private Drawable drawable, bgDrawable;
	Paint textPaint;

	public TabButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO 自动生成的构造函数存根
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabButton);
		drawable = a.getDrawable(R.styleable.TabButton_drawable);
		a.recycle();
		// setButtonDrawable(null);
		bgDrawable = getResources().getDrawable(R.drawable.fui_tabbar_bg_tabbtn);
		setBackgroundDrawable(bgDrawable);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int canvasWidth = canvas.getWidth();
		int canvasHeight = canvas.getHeight();
		final Drawable buttonDrawable = drawable;
		if (buttonDrawable != null) {
			
			int drawableHeight = buttonDrawable.getIntrinsicHeight();
			int drawableWidth = buttonDrawable.getIntrinsicWidth();
			
			if(drawableHeight==-1)
			{
				return;
			}
			
			SizeHolder holder=scaleImage(drawableWidth,drawableHeight,canvasWidth,canvasHeight);
			drawableHeight=holder.height;
			drawableWidth=holder.width;
			int y = (canvasHeight - drawableHeight) / 2;
			int x = (canvasWidth - drawableWidth) / 2;
			buttonDrawable.setBounds(x, y, x + drawableWidth, y + drawableHeight);
			buttonDrawable.draw(canvas);
		}
	}
	/**
	 * 根据canvas的尺寸缩放
	 * @param imgWidth
	 * @param imgHeight
	 * @param canvasWidth
	 * @param canvasHeight
	 */
	private SizeHolder scaleImage(int imgWidth,int imgHeight,int canvasWidth,int canvasHeight)
	{
		SizeHolder holder=new SizeHolder();
		if(imgWidth<canvasWidth&&imgHeight<canvasHeight)
		{
			holder.width=imgWidth;
			holder.height=imgHeight;
		}else if(imgWidth>canvasWidth&&imgHeight>canvasHeight)
		{
			//宽高都超出了，缩放
			float widthRatio=imgWidth/(float)canvasWidth;
			float heightRatio=imgHeight/(float)canvasHeight;
			//除以高的比值
			float maxRatio=Math.max(widthRatio, heightRatio);
			
			holder.width=(int)(imgWidth/maxRatio);
			holder.height=(int)(imgHeight/maxRatio);
			
		}else if(imgWidth>canvasWidth)
		{
			//只有宽超出
			float widthRatio=imgWidth/(float)canvasWidth;
			holder.width=(int)(imgWidth/widthRatio);
			holder.height=(int)(imgHeight/widthRatio);
		}else{
			//只有高度超出
			float heightRatio=imgHeight/(float)canvasHeight;
			holder.width=(int)(imgWidth/heightRatio);
			holder.height=(int)(imgHeight/heightRatio);
		}
		return holder;
	}

	@Override
	protected void drawableStateChanged() {
		// TODO 自动生成的方法存根
		if (drawable != null) {
			int[] myDrawableState = getDrawableState();
			drawable.setState(myDrawableState);
			if (bgDrawable != null)
				bgDrawable.setState(myDrawableState);
			invalidate();
		}

	}
	class SizeHolder{
		int width;
		int height;
	}

}
