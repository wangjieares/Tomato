package com.pocketdigi.fui.adapters;

import java.util.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.pocketdigi.fui.R;

/**
 * Spinner 适配器
 * 
 * @author fhp
 * 
 */
public class FSpinnerAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	List<String> mList;
	private int mSelectPosition;

	public FSpinnerAdapter(Context context, List<String> list) {
		// TODO Auto-generated constructor stub
		mInflater = LayoutInflater.from(context);
		mList = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		String str=mList.get(position);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.fui_item_spinner, null);
			holder=new ViewHolder();
			holder.textView=(TextView)convertView;
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder)convertView.getTag();
		}
		holder.textView.setText(str);
		return convertView;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return mList.size() == 0;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		DropDownViewHolder holder;
		String str=mList.get(position);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.fui_item_spinner_dropdown, null);
			holder=new DropDownViewHolder();
			holder.textView=(TextView)convertView.findViewById(R.id.textView);
			holder.checkBox=(CheckBox)convertView.findViewById(R.id.cb_check);
			convertView.setTag(holder);
		}else{
			holder=(DropDownViewHolder)convertView.getTag();
		}
		holder.textView.setText(str);
		if(mSelectPosition==position)
		{
			holder.textView.setTextColor(mInflater.getContext().getResources().getColor(R.color.f_light_blue));
			holder.checkBox.setChecked(true);
		}else{
			holder.textView.setTextColor(mInflater.getContext().getResources().getColor(R.color.f_default_textColor));
			holder.checkBox.setChecked(false);
		}
		return convertView;
	}
	
	/**
	 * 设置选择的position
	 * @param position
	 */
	public void setPosition(int position)
	{
		mSelectPosition=position;
	}
	
	class ViewHolder {
		TextView textView;
	}
	class DropDownViewHolder{
		TextView textView;
		CheckBox checkBox;
	}

}
