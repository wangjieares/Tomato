/**
 * 底部tab导航条
 */
package com.pocketdigi.fui.views;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.pocketdigi.fui.R;

public class TabBar extends RadioGroup implements OnCheckedChangeListener{
    private List<OnTabChangeListener> tabChangeListeners;
    public TabBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnCheckedChangeListener(this);
        this.setOrientation(RadioGroup.HORIZONTAL);
        this.setBackgroundColor(getResources().getColor(R.color.f_light_blue));
        tabChangeListeners=new ArrayList<OnTabChangeListener>();
    }
    /**
     * 添加tab切换监听器
     * @param listener
     */
    public void addOnTabChangeListener(OnTabChangeListener listener)
    {
    	this.tabChangeListeners.add(listener);
    }
    
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // TODO Auto-generated method stub
    	
    	if(tabChangeListeners.size()>0)
    	{
    		int checkedIndex=0;
    		TabButton button=(TabButton)findViewById(checkedId);
    		checkedIndex=indexOfChild(button);
    		for(OnTabChangeListener listener:tabChangeListeners)
    		{
    			listener.onTabChanged(checkedId,checkedIndex);
    		}
    	}
    }
    /**
     * tab改变
     * @author fhp
     *
     */
    public interface OnTabChangeListener
    {
    	/**
    	 * tab点中改变时触发
    	 * @param tabId TabButton的id
    	 * @param checkedIndex tabButton在TabBar中的的索引,0开始
    	 */
    	public void onTabChanged(int tabId,int checkedIndex);
    }
    /**
     * OnDestory中调用
     * 清理操作
     */
    public void destory() {
    	tabChangeListeners.clear();
	}
}
