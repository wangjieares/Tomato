package www.atomato.com.tomato.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.atomato.com.tomato.R;
import www.atomato.com.tomato.adapter.ExpandableLayoutHelper;
import www.atomato.com.tomato.data.GroupItem;
import www.atomato.com.tomato.data.SpaceItemDecoration;
import www.atomato.com.tomato.data.TodoSection;
import www.atomato.com.tomato.recall.ItemClickListener;
import www.atomato.com.tomato.utils.BaseFragment;
import www.atomato.com.tomato.utils.LogUtils;
import www.atomato.com.tomato.utils.ToastUtils;

/**
 * Created by wangjie on 16-11-17.
 * 数据存储方式选择xml存储,为了复习xml解析
 *
 */

public class MoreFragment extends BaseFragment  implements ItemClickListener {
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
        expandableLayoutHelper = new ExpandableLayoutHelper(getContext(),mRecyclerView,MoreFragment.this);

//        expandableLayoutHelper.addSection("test",new ArrayList<GroupItem>());
//        expandableLayoutHelper.addItem("test",new GroupItem("aa",0,0,0,0));
//        expandableLayoutHelper.addItem("test",new GroupItem("bb",0,0,0,0));

//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Group", Context.MODE_PRIVATE);
//        for (int i = sharedPreferences.getInt("group_num", 0); i >= 0; i--) {//有默认的Group
//            if (i==0){
//                break;
//            }
//            String title = sharedPreferences.getString("group_name_" + i, "Error!");
//            LogUtils.e(tag,"group_name_"+i);
//            expandableLayoutHelper.addSection(title, arrayList);
//            expandableLayoutHelper.notifyDataSetChanged();
//        }
//        initTodo();
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Group", Context.MODE_PRIVATE);
        for (int i = sharedPreferences.getInt("group_num", 0); i >= 0; i--) {//有默认的Group
            if (i==0){
                break;
            }
            String title = sharedPreferences.getString("group_name_" + i, "Error!");
            expandableLayoutHelper.removeSection(title);
        }
        expandableLayoutHelper.notifyDataSetChanged();
        initTodo();
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
        ToastUtils.show(getContext(),todoSection.getName());
    }

    @Override
    public void ItemAddClick(View view,TodoSection todoSection) {
        expandableLayoutHelper.addItem(todoSection.getName(),new GroupItem("bb",0,0,0,0));
        expandableLayoutHelper.notifyDataSetChanged();
    }

    @Override
    public void ItemReminkClick(View view,TodoSection todoSection) {
        ToastUtils.show(getContext(),"ItemReminkClick");
    }
}
