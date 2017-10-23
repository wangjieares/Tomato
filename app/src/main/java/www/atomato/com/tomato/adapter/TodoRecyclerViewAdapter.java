package www.atomato.com.tomato.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import www.atomato.com.tomato.constants.Constants;
import www.atomato.com.tomato.data.ToDoItem;
import www.atomato.com.tomato.recall.OnStickListener;
import www.atomato.com.tomato.sqlite.ViewSQLite;
import www.atomato.com.tomato.utils.LogUtils;
import www.atomato.com.tomato.view.ToDoView;

/**
 * Created by wangjie on 16-11-17.
 */

public class TodoRecyclerViewAdapter extends RecyclerView.Adapter<TodoRecyclerViewAdapter.MyViewHolder> {
    private List<ToDoItem> mList;
    private Context mContext;
    private OnStickListener mOnStickListener;

    public TodoRecyclerViewAdapter(Context context, List<ToDoItem> list) {
        this.mList = list;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(new ToDoView(mContext));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.toDoView.setTodoTitle(mList.get(position).getTitle());
        holder.toDoView.setTodoTime(mList.get(position).getTime());
        holder.toDoView.setProgress(mList.get(position).getProgress());
        holder.toDoView.setItemState(mList.get(position).getState());
        holder.toDoView.setDrawColor(mList.get(position).getDrawBackColor());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ToDoView toDoView;

        MyViewHolder(View itemView) {
            super(itemView);
            toDoView = (ToDoView) itemView;
        }
    }

    public void setOnStickListener(OnStickListener onStickListener) {
        this.mOnStickListener = onStickListener;
    }

    private void addData(int position, ToDoItem toDoData) {
        mList.remove(position);
        mList.add(position, toDoData);
//        notifyItemInserted(position);
        notifyItemChanged(position);
        if (mOnStickListener != null) {
            mOnStickListener.onScrollTop(mList.size());//滑动最上边
        }
    }

    public void addInsert(int positon, ToDoItem toDoData) {
        mList.add(positon, toDoData);
        notifyItemInserted(positon);
        if (mOnStickListener != null) {
            mOnStickListener.onScrollTop(mList.size());//滑动最上边
        }
    }

    //不知道是否影响效率
    public String getTitle(int position) {
        return mList.get(position).getTitle();
    }

    public void addData(ToDoItem toDoData) {
        mList.add(toDoData);
        notifyItemInserted(getItemCount());
//        if (mOnStickListener != null) {
//            mOnStickListener.onScrollTop(mList.size());//滑动最上边
//        }
    }

    public void addDataOnScroll(ToDoItem toDoData) {
        mList.add(toDoData);
        notifyItemInserted(getItemCount());
        if (mOnStickListener != null) {
            mOnStickListener.onScrollTop(mList.size());//滑动最上边
        }
    }

    public void removeData(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public void refreshAll(int positon, int state) {
        if (state == 0) {
            LogUtils.e("RecyclerView", "--------------------------true");
            mList.get(positon).setStickState(1);
            Collections.sort(mList);
            notifyDataSetChanged();
        }
        if (state == 1) {
            LogUtils.e("RecyclerView", "--------------------------false");
            mList.get(positon).setStickState(0);
        }
    }

    public void refresh(int position) {
        ViewSQLite viewSQLite = new ViewSQLite(mContext);
        String title = mList.get(position).getTitle();
        LogUtils.e("TodoRecycle refresh", "=====" + title);
        try (Cursor cursor = viewSQLite.query(Constants.TABLE_NAME, null, "todo_title=?", new String[]{title}, null, null, null)) {
            cursor.moveToNext();
            int time = cursor.getInt(cursor.getColumnIndex("todo_time"));
            int state = cursor.getInt(cursor.getColumnIndex("todo_state"));
            int progress = cursor.getInt(cursor.getColumnIndex("todo_progress"));
            int color = cursor.getInt(cursor.getColumnIndex("todo_color"));
            addData(position, new ToDoItem(title, time, state, progress, color));
        }
    }
}
