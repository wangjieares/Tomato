package com.pocketdigi.fui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pocketdigi.fui.R;
/**
 * 搜索条
 * @author fhp
 *
 */
public class SearchBar extends RelativeLayout {
    private boolean mAlreadyInflated = false;
    private ImageView btn_search;
    private EditText et_search;
    //搜索按钮点击监听器
    private OnSearchListener mListener;
	public SearchBar(Context context) {
		super(context);
	}
	public SearchBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
    @Override
    public void onFinishInflate() {
        if (!mAlreadyInflated) {
        	mAlreadyInflated = true;
            inflate(getContext(), R.layout.fui_view_searchbar, this);
    
            btn_search=(ImageView)findViewById(R.id.btn_search);
            et_search=(EditText)findViewById(R.id.et_search);
            
        }
        super.onFinishInflate();
    }
    public void setOnSearchListener(OnSearchListener listener)
    {
    	this.mListener=listener;
    	btn_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mListener!=null)
				{
					String key=et_search.getText().toString();
					mListener.onSearchClick(key);
				}

			}
		});
    }
    public interface OnSearchListener{
    	public void onSearchClick(String key);
    }
}
