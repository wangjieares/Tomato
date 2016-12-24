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
 * Created by bpncool on 2/23/2016.
 */
public class ExpandableLayoutHelper implements TodoStateChangeListener {

    //data list
    private LinkedHashMap<TodoSection, ArrayList<GroupItem>> mSectionDataMap = new LinkedHashMap<>();
    private ArrayList<Object> mDataArrayList = new ArrayList<>();

    //section map
    //TODO : look for a way to avoid this
    private HashMap<String, TodoSection> mSectionMap = new HashMap<>();

    //adapter
    private ExpandableTodoAdapter mExpandableTodoAdapter;

    //recycler view
    RecyclerView mRecyclerView;

    public ExpandableLayoutHelper(Context context, RecyclerView recyclerView, ItemClickListener itemClickListener) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        //linearLayoutManager
        mExpandableTodoAdapter = new ExpandableTodoAdapter(context, mDataArrayList,
                linearLayoutManager, itemClickListener, this);
        recyclerView.setAdapter(mExpandableTodoAdapter);
        mRecyclerView = recyclerView;
    }

    public void notifyDataSetChanged() {
        //TODO : handle this condition such that these functions won't be called if the recycler view is on scroll
        generateDataList();
        mExpandableTodoAdapter.notifyDataSetChanged();
    }

    public void addSection(String section, ArrayList<GroupItem> items) {
        TodoSection newTodoSection;
        mSectionMap.put(section, (newTodoSection = new TodoSection(section)));//section
        mSectionDataMap.put(newTodoSection, items);
    }

    public void addItem(String section, GroupItem item) {
        mSectionDataMap.get(mSectionMap.get(section)).add(item);
    }

    public void removeItem(String section, GroupItem item) {
        mSectionDataMap.get(mSectionMap.get(section)).remove(item);
    }

    public void removeSection(String section) {
        mSectionDataMap.remove(mSectionMap.get(section));
        mSectionMap.remove(section);
    }

    private void generateDataList () {
        mDataArrayList.clear();
        for (Map.Entry<TodoSection, ArrayList<GroupItem>> entry : mSectionDataMap.entrySet()) {
            TodoSection key;
            mDataArrayList.add((key = entry.getKey()));
            if (key.isExpanded)
                mDataArrayList.addAll(entry.getValue());
        }
    }

    @Override
    public void onSectionStateChanged(TodoSection todoSection, boolean isOpen) {
        if (!mRecyclerView.isComputingLayout()) {
            todoSection.isExpanded = isOpen;
            notifyDataSetChanged();
        }
    }
}
