package www.atomato.com.tomato.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.atomato.com.tomato.R;
import www.atomato.com.tomato.adapter.ExpandableLayoutHelper;
import www.atomato.com.tomato.data.GroupItem;
import www.atomato.com.tomato.data.ToDoData;
import www.atomato.com.tomato.data.TodoSection;
import www.atomato.com.tomato.recall.ItemClickListener;
import www.atomato.com.tomato.sqlite.ViewSQLite;
import www.atomato.com.tomato.utils.BaseFragment;
import www.atomato.com.tomato.utils.SaxXmlUtils;

/**
 * Created by wangjie on 16-11-17.
 * 数据存储方式选择xml存储,为了复习xml解析
 *
 */

public class MoreFragment extends BaseFragment  implements ItemClickListener {
    private View view = null;
    private RecyclerView mRecyclerView;
    private ArrayList<GroupItem> arrayList;
    private ExpandableLayoutHelper expandableLayoutHelper;
    private Subscriber<GroupItem> mTodoDataObserver;
    private Observable<GroupItem> mObservable;
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
        arrayList = new ArrayList<>();
        expandableLayoutHelper = new ExpandableLayoutHelper(getContext(),mRecyclerView,MoreFragment.this);
//        ExpandableLayoutHelper expandableLayoutHelper = new ExpandableLayoutHelper(getContext(),
//                mRecyclerView, this, 3);
        initTodo();
    }
    private void initTodo() {
        mTodoDataObserver = new Subscriber<GroupItem>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(GroupItem groupItem) {
//                arrayList.add(groupItem);
                expandableLayoutHelper.addItem(groupItem.getmGroupName(),arrayList);
                expandableLayoutHelper.notifyDataSetChanged();
            }
        };
        mObservable = Observable.create(new Observable.OnSubscribe<GroupItem>() {
            @Override
            public void call(Subscriber<? super GroupItem> subscriber) {
                try {
//                    expandableLayoutHelper.addSection("默认", arrayList);
                    FileInputStream fileInputStream = new FileInputStream(getContext().getFilesDir()+"/1.xml");
                    ArrayList<GroupItem> lists = SaxXmlUtils.parse(fileInputStream);
                    for(GroupItem groupItem :lists){
                        arrayList.add(groupItem);
                        subscriber.onNext(groupItem);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                subscriber.onCompleted();
            }
        });
        mObservable.subscribeOn(Schedulers.io()); // 指定 subscribe() 发生在 IO 线程
        mObservable.observeOn(AndroidSchedulers.mainThread());// 指定 Subscriber 的回调发生在主线程
        mObservable.subscribe(mTodoDataObserver);
    }
    @Override
    public void itemClicked(View item) {

    }
    @Override
    public void itemClicked(TodoSection todoSection) {

    }
}
