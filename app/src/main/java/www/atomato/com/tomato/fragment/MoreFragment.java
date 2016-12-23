package www.atomato.com.tomato.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import www.atomato.com.tomato.R;
import www.atomato.com.tomato.adapter.ExpandableLayoutHelper;
import www.atomato.com.tomato.data.Item;
import www.atomato.com.tomato.data.Person;
import www.atomato.com.tomato.data.ToDoData;
import www.atomato.com.tomato.data.TodoSection;
import www.atomato.com.tomato.recall.ItemClickListener;
import www.atomato.com.tomato.utils.BaseFragment;
import www.atomato.com.tomato.utils.SaxXmlUtils;

/**
 * Created by wangjie on 16-11-17.
 * 数据存储方式选择xml存储,为了复习xml解析
 *
 */

public class MoreFragment extends BaseFragment  implements ItemClickListener {
    private View view = null;
    RecyclerView mRecyclerView;
    ArrayList<Item> arrayList;
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
        arrayList = new ArrayList<>();
        arrayList.add(new Item(getContext(), "1", 1, 1, 1, 1,1));
        expandableLayoutHelper.addSection("默认", arrayList);
        expandableLayoutHelper.addItem("默认", new Item(getContext(), "1", 1, 1, 1, 1,1));
        expandableLayoutHelper.notifyDataSetChanged();
        List<Person> list = new ArrayList<>();
        list.add(new Person(1,"wangjie","test"));
        list.add(new Person(2,"wangjie1","t1est"));
        try {
            SaxXmlUtils.save(list,new FileOutputStream(getContext().getPackageResourcePath()+"/file"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void itemClicked(View item) {

    }

    @Override
    public void itemClicked(TodoSection todoSection) {

    }
}
