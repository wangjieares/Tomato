package www.atomato.com.tomato.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import www.atomato.com.tomato.adapter.ExpandableLayoutHelper;
import www.atomato.com.tomato.recall.ItemClickListener;
import www.atomato.com.tomato.recall.TodoSection;
import www.atomato.com.tomato.utils.BaseFragment;
import www.atomato.com.tomato.R;

/**
 * Created by wangjie on 16-11-17.
 */

public class MoreFragment extends BaseFragment  implements ItemClickListener {
    private View view = null;
    RecyclerView mRecyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_more, container, false);
        initView();
        return view;
    }

    private void initView() {
        //setting the recycler view
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_more_recycler_view);
//        ExpandableLayoutHelper expandableLayoutHelper = new ExpandableLayoutHelper(getContext(),
//                mRecyclerView, this, 3);
        ExpandableLayoutHelper expandableLayoutHelper = new ExpandableLayoutHelper(getContext(),mRecyclerView,this,3);
        //random data
        ArrayList<View> arrayList = new ArrayList<>();
        arrayList.add(new EditText(getContext()));
        expandableLayoutHelper.addSection("Apple Products", arrayList);
        expandableLayoutHelper.addItem("Apple Products", new EditText(getContext()));
        expandableLayoutHelper.notifyDataSetChanged();
    }

    @Override
    public void itemClicked(View item) {

    }

    @Override
    public void itemClicked(TodoSection todoSection) {

    }
}
