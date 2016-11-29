package www.atomato.com.tomato.adapter;

import android.content.Context;
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

public class TodoRecyclerViewAdapter extends RecyclerView.Adapter<TodoRecyclerViewAdapter.MyViewHolder> {
    private List<ToDoData> list;
    private Context context;
    public TodoRecyclerViewAdapter(Context context, List<ToDoData> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(new ToDoView(context));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.toDoView.setTodoTitle(list.get(position).getTitle());
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
        }
    }

    public void addData(int position, String title, int time, int state, float progree, int drawColor) {
        LogUtils.e("MyRecycler", title);
        ToDoData todoData = new ToDoData(title, time, state, progree, drawColor);
        list.add(todoData);
        notifyItemInserted(position);
    }
    //不知道是否影响效率
    public String getTitle(int position) {
        return list.get(position).getTitle();
    }

    public void addData(ToDoData toDoData) {
        list.add(toDoData);
        notifyItemInserted(getItemCount() + 1);
    }

    public void removeData(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }
}
