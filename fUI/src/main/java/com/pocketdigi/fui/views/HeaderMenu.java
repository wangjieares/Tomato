package com.pocketdigi.fui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pocketdigi.fui.R;

/**
 * 顶部菜单，左右两个按钮，中间文本
 * 
 * @author fhp
 * 
 */
public class HeaderMenu extends RelativeLayout {
	private boolean mAlreadyInflated = false;
	private ImageView mBtn_left, mBtn_right;
	private TextView mTv_header;
	private OnButtonClickListener mListener;

	String mText;
	int mLeftDrawableId, mRightDrawableId;

	public HeaderMenu(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public HeaderMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.HeaderMenu);
		mText = typedArray.getString(R.styleable.HeaderMenu_text);
		mLeftDrawableId = typedArray.getResourceId(
				R.styleable.HeaderMenu_left_drawable, 0);
		mRightDrawableId = typedArray.getResourceId(
				R.styleable.HeaderMenu_right_drawable, 0);
		typedArray.recycle();
	}

	@Override
	public void onFinishInflate() {
		if (!mAlreadyInflated) {
			mAlreadyInflated = true;
			inflate(getContext(), R.layout.fui_view_headermenu, this);
			//使用mege标签的xml中，直接设置background无效
			setBackgroundColor(getResources().getColor(R.color.f_light_blue));
			mBtn_left = (ImageView) findViewById(R.id.btn_left);
			mBtn_right = (ImageView) findViewById(R.id.btn_right);
			mTv_header = (TextView) findViewById(R.id.tv_header);
			if (!TextUtils.isEmpty(mText)) {
				mTv_header.setText(mText);
			}
			if (mLeftDrawableId != 0) {
				mBtn_left.setVisibility(View.VISIBLE);
				mBtn_left.setImageResource(mLeftDrawableId);
			}
			if (mRightDrawableId != 0) {
				mBtn_right.setVisibility(View.VISIBLE);
				mBtn_right.setImageResource(mRightDrawableId);
			}
		}
		super.onFinishInflate();
	}

	/**
	 * 设置两个按钮的监听器
	 * 
	 * @param onButtonClickListener
	 */
	public void setOnButtonClickListener(
			OnButtonClickListener onButtonClickListener) {
		this.mListener = onButtonClickListener;
		
		mBtn_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mListener.onLeftClicked();
			}
		});
		mBtn_right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mListener.onRightClicked();
			}
		});
	}
	/**
	 * 设置居中的标题
	 * @param title
	 */
	public void setTitle(String title)
	{
		this.mText=title;
		if(mAlreadyInflated)
		{
			mTv_header.setText(title);
		}
	}

	/**
	 * 顶部菜单上按钮点击监听器
	 * 
	 * @author fhp
	 * 
	 */
	public interface OnButtonClickListener {
		/** 左边按钮点击 **/
		public void onLeftClicked();

		/** 右边按钮点击 **/
		public void onRightClicked();
	}

}
