package www.atomato.com.tomato.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.atomato.com.tomato.R;
import www.atomato.com.tomato.activity.AddItemGroupActivity;
import www.atomato.com.tomato.adapter.ExpandableLayoutHelper;
import www.atomato.com.tomato.constants.Constants;
import www.atomato.com.tomato.data.GroupItem;
import www.atomato.com.tomato.data.TodoSection;
import www.atomato.com.tomato.recall.ItemClickListener;
import www.atomato.com.tomato.utils.BaseFragment;
import www.atomato.com.tomato.utils.ToastUtils;

/**
 * Created by wangjie on 16-11-17.
 * 数据存储方式选择xml存储,为了复习xml解析
 */

public class MoreFragment extends BaseFragment implements ItemClickListener {
    private View view = null;
    private RecyclerView mRecyclerView;
    private ExpandableLayoutHelper expandableLayoutHelper;
    private Subscriber<Integer> mTodoDataObserver;
    private Observable<Integer> mObservable;

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
        expandableLayoutHelper = new ExpandableLayoutHelper(getContext(), mRecyclerView, MoreFragment.this);
        initTodo();
    }

    @Override
    public void onResume() {
        super.onResume();//
    }

    private void initTodo() {
        mTodoDataObserver = new Subscriber<Integer>() {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Group", Context.MODE_PRIVATE);

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Integer integer) {
//                LogUtils.e(tag, "group_name_" + integer);
                String title = sharedPreferences.getString("group_name_" + integer, "Error!");
                expandableLayoutHelper.addSection(title, new ArrayList<GroupItem>());
                expandableLayoutHelper.notifyDataSetChanged();
            }
        };
        mObservable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Group", Context.MODE_PRIVATE);
                for (int i = sharedPreferences.getInt("group_num", 0); i >= 0; i--) {//有默认的Group
                    if (i == 0) {
                        break;
                    }
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
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
        ToastUtils.show(getContext(), todoSection.getName());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case Constants.REQUEST_CODE_ADD_GROUP_ITEM:
                if (data != null) {
                    String group_name = data.getStringExtra("group_name");
                    String title = data.getStringExtra("title");
                    addChildItem(group_name,title);
                    ToastUtils.show(getActivity(), group_name + title);
                    expandableLayoutHelper.addSection(title, new ArrayList<GroupItem>());
                    expandableLayoutHelper.notifyDataSetChanged();
                }
                break;
        }
    }
    public void addChildItem(String group_name,String title){
        expandableLayoutHelper.addItem(group_name, new GroupItem(title, 0, 0, 0, 0));
        expandableLayoutHelper.notifyDataSetChanged();
    }
    @Override
    public void ItemAddClick(View view, TodoSection todoSection) {
        Intent intent = new Intent(getActivity(), AddItemGroupActivity.class);
        //放入外围组名称
        intent.putExtra("group_name", todoSection.getName());
        startActivityForResult(intent, 0);
    }

    @Override
    public void ItemReminkClick(View view, TodoSection todoSection) {
        ToastUtils.show(getContext(), "ItemReminkClick");
        addChildItem(todoSection.getName(),"hh");
    }
}
