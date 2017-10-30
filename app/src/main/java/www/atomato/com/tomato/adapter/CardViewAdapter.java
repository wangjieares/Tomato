package www.atomato.com.tomato.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import www.atomato.com.tomato.R;
import www.atomato.com.tomato.data.CardViewData;

/**
 * Created by Administrator on 2017/10/30.
 */

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {
    private ArrayList<CardViewData> arrayList;
    public CardViewAdapter(ArrayList<CardViewData> arrayList){
        this.arrayList=arrayList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_item,parent);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextViewTitle.setText(arrayList.get(position).getTitle());
        holder.mTextViewContent.setText(arrayList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextViewTitle;
        TextView mTextViewContent;
        ViewHolder(View view){
            super(view);
            mTextViewTitle = (TextView) view.findViewById(R.id.card_view_item_textView2);
            mTextViewContent = (TextView) view.findViewById(R.id.card_view_item_textView3);
        }
    }
}
