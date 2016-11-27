package com.pocketdigi.fui.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pocketdigi.fui.R;

/**
 * 自定义的AlertDialog 系统的AlertDialog样式是私有的，不允许继承，所以，没法通过定义主题来修改外观
 * 
 * @author fhp
 * 
 */
public class FAlertDialog extends Dialog {

	private CharSequence mTitle, mMessage, mPositiveButtonText, mNegativeButtonText;
	private TextView mTitleView, mMessageView;
	private Button btn_negative, btn_positve;
	private View.OnClickListener mPositiveListener, mNegativeListener;
	private View mViewDivider;

	public FAlertDialog(Context context) {
		super(context);
		// TODO 自动生成的构造函数存根
		this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fui_view_falertdialog);
		mTitleView = (TextView) findViewById(R.id.tv_title);
		mMessageView = (TextView) findViewById(R.id.tv_message);
		btn_negative = (Button) findViewById(R.id.btn_negative);
		btn_positve = (Button) findViewById(R.id.btn_positve);
		mViewDivider = (View) findViewById(R.id.view_divider);

		if (mTitle != null)
			mTitleView.setText(mTitle);
		if (mMessage != null)
			mMessageView.setText(mMessage);

		if (mPositiveButtonText != null) {
			btn_positve.setVisibility(View.VISIBLE);
			btn_positve.setText(mPositiveButtonText);
			btn_positve.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO 自动生成的方法存根
					mPositiveListener.onClick(v);
					dismiss();
				}
			});
		}

		if (mNegativeButtonText != null) {
			btn_negative.setVisibility(View.VISIBLE);
			btn_negative.setText(mNegativeButtonText);
			btn_negative.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO 自动生成的方法存根
					mNegativeListener.onClick(v);
					dismiss();
				}
			});
		}

		if (mPositiveButtonText != null && mNegativeButtonText != null) {
			mViewDivider.setVisibility(View.VISIBLE);
		}

	}

	/**
	 * 设置标题
	 */
	@Override
	public void setTitle(CharSequence title) {
		// TODO 自动生成的方法存根
		System.out.println("set Title");
		mTitle = title;
		if (mTitleView != null) {
			mTitleView.setText(title);
		}
	}

	@Override
	public void setTitle(int titleId) {
		// TODO 自动生成的方法存根
		this.setTitle(this.getContext().getString(titleId));
	}

	/**
	 * 设置信息内容
	 * 
	 * @param message
	 */
	public void setMessage(CharSequence message) {
		this.mMessage = message;
		if (mMessageView != null) {
			mMessageView.setText(message);
		}
	}

	/**
	 * 设置确定按钮（右）
	 * 
	 * @param text
	 * @param listener
	 */
	public void setPositiveButton(CharSequence text, final View.OnClickListener listener) {
		mPositiveListener = listener;
		mPositiveButtonText = text;
		if (btn_positve != null) {
			if (btn_positve.getVisibility() == View.GONE) {
				btn_positve.setVisibility(View.VISIBLE);
				if (btn_negative.getVisibility() == View.VISIBLE && mViewDivider.getVisibility() == View.GONE) {
					mViewDivider.setVisibility(View.VISIBLE);
				}
			}
			btn_positve.setText(text);
			btn_positve.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO 自动生成的方法存根
					listener.onClick(v);
					FAlertDialog.this.dismiss();
				}
			});
		}
	}

	/**
	 * 设置取消按钮（左）
	 * 
	 * @param text
	 * @param listener
	 */
	public void setNegativeButton(CharSequence text, final View.OnClickListener listener) {
		mNegativeListener = listener;
		mNegativeButtonText = text;
		if (btn_negative != null) {
			if (btn_negative.getVisibility() == View.GONE) {
				btn_negative.setVisibility(View.VISIBLE);
				if (btn_positve.getVisibility() == View.VISIBLE && mViewDivider.getVisibility() == View.GONE) {
					mViewDivider.setVisibility(View.VISIBLE);
				}
			}
			btn_negative.setText(text);
			btn_negative.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO 自动生成的方法存根
					listener.onClick(v);
					FAlertDialog.this.dismiss();
				}
			});
		}

	}
	

}
