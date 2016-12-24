package www.atomato.com.tomato.fragment;

import android.database.Cursor;
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
import www.atomato.com.tomato.adapter.ExpandableLayoutHelper;
import www.atomato.com.tomato.data.GroupItem;
import www.atomato.com.tomato.data.ToDoData;
import www.atomato.com.tomato.data.TodoSection;
import www.atomato.com.tomato.recall.ItemClickListener;
import www.atomato.com.tomato.sqlite.ViewSQLite;
import www.atomato.com.tomato.utils.BaseFragment;

/**
 * Created by wangjie on 16-11-17.
 * 数据存储方式选择xml存储,为了复习xml解析
 *
 */

public class MoreFragment extends BaseFragment  implements ItemClickListener {
    private View view = null;
    RecyclerView mRecyclerView;
    ArrayList<GroupItem> arrayList;
    private Subscriber<ToDoData> mTodoDataObserver;
    private Observable<ToDoData> mObservable;
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
        arrayList.add(new GroupItem(getContext(), "1", 1, 1, 1, 1,1));
        expandableLayoutHelper.addSection("默认", arrayList);
        expandableLayoutHelper.addItem("默认", new GroupItem(getContext(), "1", 1, 1, 1, 1,1));
        expandableLayoutHelper.notifyDataSetChanged();
    }
    private void initTodo() {
        mTodoDataObserver = new Subscriber<ToDoData>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ToDoData toDoData) {
            }
        };

        mObservable = Observable.create(new Observable.OnSubscribe<ToDoData>() {
            @Override
            public void call(Subscriber<? super ToDoData> subscriber) {
                ViewSQLite viewSQLite = new ViewSQLite(getContext());
                try (Cursor cursor = viewSQLite.query()) {
                    while (cursor.moveToNext()) {
//                        int id = cursor.getInt(cursor.getColumnIndex("_id"));
                        String title = cursor.getString(cursor.getColumnIndex("todo_title"));
                        int time = cursor.getInt(cursor.getColumnIndex("todo_time"));
                        int state = cursor.getInt(cursor.getColumnIndex("todo_state"));
                        int progress = cursor.getInt(cursor.getColumnIndex("todo_progress"));
                        int color = cursor.getInt(cursor.getColumnIndex("todo_color"));
                        subscriber.onNext(new ToDoData(title, time, state, progress, color));
                    }
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

    }
}
