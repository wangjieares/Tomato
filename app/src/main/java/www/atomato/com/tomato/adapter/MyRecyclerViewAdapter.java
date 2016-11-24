package www.atomato.com.tomato.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import www.atomato.com.tomato.data.ToDoData;
import www.atomato.com.tomato.sqlite.ViewSQLite;
import www.atomato.com.tomato.utils.LogUtils;
import www.atomato.com.tomato.view.ToDoView;

/**
 * Created by wangjie on 16-11-17.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    private List<ToDoData> list;
    private Context context;

    public MyRecyclerViewAdapter(Context context, List<ToDoData> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(new ToDoView(context));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.toDoView.setTodoTitle(list.get(position).getTitle());//为什么为空
        holder.toDoView.setTodoTime(list.get(position).getTime());
        holder.toDoView.setProgress(list.get(position).getProgress());
        holder.toDoView.setItemState(list.get(position).getState());
        holder.toDoView.setDrawColor(list.get(position).getDrawBackColor());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ToDoView toDoView;

        MyViewHolder(View itemView) {
            super(itemView);
            toDoView = (ToDoView) itemView;
//            toDoView.setFocusable(true);
        }
    }

    public void addData(int position, String title, int time,int state,float progree, int drawColor) {
        LogUtils.e("MyRecycler",title);
        ToDoData todoData = new ToDoData(title, time,state,progree, drawColor);
//        ViewSQLite viewSQLite = new ViewSQLite(context);
//        viewSQLite.insert(todoData);
        list.add(todoData);
        notifyItemInserted(position);
//        viewSQLite.closedb();
    }

    public void addData(ToDoData toDoData) {
        list.add(toDoData);
        notifyDataSetChanged();
    }

    public void removeData(int position) {
//        ViewSQLite viewSQLite = new ViewSQLite(context);
//        viewSQLite.delete();
        list.remove(position);
        notifyItemRemoved(position);
    }
}
