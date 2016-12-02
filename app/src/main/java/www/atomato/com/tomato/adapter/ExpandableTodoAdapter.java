package www.atomato.com.tomato.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.util.ArrayList;
import www.atomato.com.tomato.R;
import www.atomato.com.tomato.data.TodoSection;
import www.atomato.com.tomato.recall.ItemClickListener;
import www.atomato.com.tomato.recall.TodoStateChangeListener;
import www.atomato.com.tomato.view.ToDoView;

/**
 * Created by lenovo on 2/23/2016.
 */
public class ExpandableTodoAdapter extends RecyclerView.Adapter<ExpandableTodoAdapter.ViewHolder> {

    //data array
    private ArrayList<Object> mDataArrayList;

    //context
    private final Context mContext;

    //listeners
    private final ItemClickListener mItemClickListener;
    private final TodoStateChangeListener mTodoStateChangeListener;
    //view type
    private static final int VIEW_TYPE_SECTION = R.layout.fragment_more_layout_section;
    private static final int VIEW_TYPE_ITEM = R.layout.fragment_more_layout_item; //TODO : change this

    ExpandableTodoAdapter(Context context, ArrayList<Object> dataArrayList,
                                 final LinearLayoutManager linearLayoutManager, ItemClickListener itemClickListener,
                                 ExpandableLayoutHelper todoStateChangeListener) {
        mContext = context;
        mItemClickListener = itemClickListener;
        mTodoStateChangeListener = todoStateChangeListener;
        mDataArrayList = dataArrayList;
    }

    private boolean isSection(int position) {
        return mDataArrayList.get(position) instanceof TodoSection;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(viewType, parent, false), viewType);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (holder.viewType) {
            case VIEW_TYPE_ITEM :
                final TextView view = (TextView) mDataArrayList.get(position);
                view.setText("test");
//                holder.itemTextView.setText(item.getName());
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemClickListener.itemClicked(v);
                    }
                });
                break;
            case VIEW_TYPE_SECTION :
                final TodoSection todoSection = (TodoSection) mDataArrayList.get(position);
                holder.sectionTextView.setText(todoSection.getName());
                holder.sectionTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemClickListener.itemClicked(todoSection);
                    }
                });
                holder.sectionToggleButton.setChecked(todoSection.isExpanded);
                holder.sectionToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        mTodoStateChangeListener.onSectionStateChanged(todoSection, isChecked);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mDataArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isSection(position))
            return VIEW_TYPE_SECTION;
        else return VIEW_TYPE_ITEM;
    }

     static class ViewHolder extends RecyclerView.ViewHolder {

        //common
        View view;
        int viewType;

        //for section
        TextView sectionTextView;
        ToggleButton sectionToggleButton;

        //for item
        TextView itemToDoView;

         ViewHolder(View view, int viewType) {
            super(view);
            this.viewType = viewType;
            this.view = view;
            if (viewType == VIEW_TYPE_ITEM) {
            } else {
                sectionTextView = (TextView) view.findViewById(R.id.fragment_more_section_title);
                sectionToggleButton = (ToggleButton) view.findViewById(R.id.toggle_button_section);
            }
        }
    }
}
