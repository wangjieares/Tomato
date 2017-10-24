package www.atomato.com.tomato.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import www.atomato.com.tomato.data.GroupItem;
import www.atomato.com.tomato.data.TodoSection;
import www.atomato.com.tomato.recall.ItemClickListener;
import www.atomato.com.tomato.recall.TodoStateChangeListener;

/**
 * Created by wangjie on 2/23/2016.
 */
public class ExpandableLayoutHelper implements TodoStateChangeListener {

    //data list
    private LinkedHashMap<TodoSection, ArrayList<GroupItem>> mSectionDataMap = new LinkedHashMap<>();
    private ArrayList<Object> mDataArrayList = new ArrayList<>();

    //section map
    private HashMap<String, TodoSection> mSectionMap = new HashMap<>();

    //adapter
    private ExpandableTodoAdapter mExpandableTodoAdapter;

    //recycler view
    private RecyclerView mRecyclerView;

    public ExpandableLayoutHelper(Context context, RecyclerView recyclerView, ItemClickListener itemClickListener) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        //linearLayoutManager
        mExpandableTodoAdapter = new ExpandableTodoAdapter(context, mDataArrayList,
                linearLayoutManager, itemClickListener, this);
        recyclerView.setAdapter(mExpandableTodoAdapter);
        mRecyclerView = recyclerView;
//        int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, context.getResources().getDisplayMetrics());//转换成6dp
//        mRecyclerView.addItemDecoration(new SpaceItemDecoration(space));
    }

    private void notifyDataSetChanged() {
        //TODO : handle this condition such that these functions won't be called if the recycler view is on scroll
        generateDataList();
        mExpandableTodoAdapter.notifyDataSetChanged();
    }

    public void addSection(String section, ArrayList<GroupItem> items) {
        TodoSection todoSection;
        mSectionMap.put(section, (todoSection = new TodoSection(section)));//section
        mSectionDataMap.put(todoSection, items);
        notifyDataSetChanged();
    }

    public void addItem(String section, GroupItem item) {
//        mSectionDataMap.get(mSectionMap.get(section)).add(item);
        TodoSection todoSection = mSectionMap.get(section);
        ArrayList<GroupItem> arrayList = mSectionDataMap.get(todoSection);
        arrayList.add(item);
        notifyDataSetChanged();
    }

    public void removeItem(String section, GroupItem item) {
        mSectionDataMap.get(mSectionMap.get(section)).remove(item);
        notifyDataSetChanged();
    }

    public void removeAll() {
//        mSectionMap.clear();
        mSectionDataMap.clear();
    }

    public void removeSection(String section) {
        mSectionDataMap.remove(mSectionMap.get(section));
        notifyDataSetChanged();
        mSectionMap.remove(section);
    }

    private void generateDataList() {
        mDataArrayList.clear();
        for (Map.Entry<TodoSection, ArrayList<GroupItem>> entry : mSectionDataMap.entrySet()) {
            TodoSection key;
            mDataArrayList.add((key = entry.getKey()));
            if (key.isExpanded)
                mDataArrayList.addAll(entry.getValue());
        }
    }

    //回掉接口，用来监听更改向下按钮
    @Override
    public void onSectionStateChanged(TodoSection todoSection, boolean isOpen) {
        if (!mRecyclerView.isComputingLayout()) {
            todoSection.isExpanded = isOpen;
            notifyDataSetChanged();
        }
    }
}
