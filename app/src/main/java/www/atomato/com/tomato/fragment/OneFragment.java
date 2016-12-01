package www.atomato.com.tomato.fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.atomato.com.tomato.R;
import www.atomato.com.tomato.activity.CountProgressActivity;
import www.atomato.com.tomato.activity.DetailActivity;
import www.atomato.com.tomato.recall.OnStickListener;
import www.atomato.com.tomato.adapter.RecyclerListener;
import www.atomato.com.tomato.adapter.TodoRecyclerViewAdapter;
import www.atomato.com.tomato.constants.Constants;
import www.atomato.com.tomato.data.SpaceItemDecoration;
import www.atomato.com.tomato.data.ToDoData;
import www.atomato.com.tomato.recall.BottomWindowListener;
import www.atomato.com.tomato.pop.ButtomWindow;
import www.atomato.com.tomato.service.RemindService;
import www.atomato.com.tomato.sqlite.ViewSQLite;
import www.atomato.com.tomato.utils.BaseFragment;
import www.atomato.com.tomato.utils.LogUtils;
import www.atomato.com.tomato.view.ToDoView;

/**
 * Created by wangjie on 16-11-17.
 */

public class OneFragment extends BaseFragment implements RecyclerListener.OnItemClickListener, BottomWindowListener, OnStickListener {
    private View mView = null;
    private RecyclerView mRecyclerView;
    private List<ToDoData> mList;
    private TodoRecyclerViewAdapter mAdapter;
    public static ViewHandler handler;
    //    public static  Handler handler;
    private Subscriber<ToDoData> mTodoDataObserver;
    private Observable<ToDoData> mObservable;
    private LinearLayoutManager mLinearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_one, container, false);
        init();
        return mView;
    }

    private void init() {
        handler = new ViewHandler();
        //start RecyclerView
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.fragment_recyclerView);
        mList = new ArrayList<>();
        mAdapter = new TodoRecyclerViewAdapter(getActivity(), mList);
        //设置布局管理器
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setStackFromEnd(true);//列表再底部开始展示，反转后由上面开始展示
        mLinearLayoutManager.setReverseLayout(true);//列表反转
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(10));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerListener(getActivity(), this));
        mAdapter.setOnStickListener(this);
        //end RecyclerView
        initTodo();
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
                mAdapter.addData(toDoData);
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

    /**
     * 1.onStart(): 这是 Subscriber 增加的方法。它会在 subscribe 刚开始，而事件还未发送之前被调用，
     * 可以用于做一些准备工作，例如数据的清零或重置。这是一个可选方法，默认情况下它的实现为空。
     * 需要注意的是，如果对准备工作的线程有要求（例如弹出一个显示进度的对话框，这必须在主线程
     * 执行）， onStart() 就不适用了，因为它总是在 subscribe 所发生的线程被调用，而不能指定线程。
     * 要在指定的线程来做准备工作，可以使用 doOnSubscribe() 方法，具体可以在后面的文中看到。
     * -------------------------------------------------------------------------------------------------
     * 2.unsubscribe(): 这是 Subscriber 所实现的另一个接口 Subscription 的方法，用于取消订阅。在这
     * 个方法被调用后，Subscriber 将不再接收事件。一般在这个方法调用前，可以使用 isUnsubscribed()
     * 先判断一下状态。 unsubscribe() 这个方法很重要，因为在 subscribe() 之后， Observable 会持有 Su
     * bscriber 的引用，这个引用如果不能及时被释放，将有内存泄露的风险。所以最好保持一个原则：要在
     * 不再使用的时候尽快在合适的地方（例如 onPause() onStop() 等方法中）调用 unsubscribe() 来解
     * 除引用关系，以避免内存泄露的发生。
     */
    public TodoRecyclerViewAdapter getAdapter() {
        return mAdapter;
    }


    @Override
    public void onLongLeftItemClick(View view, int position) {
//        LogUtils.e(tag, tag + "===onLongRightItenClick===>" + position);
        showPopFormBottom(view, position);
//        ButtomAlertDialog.showDialog(getContext());
    }

    @Override
    public void onLeftItemClick(View view, int position) {
//        LogUtils.e(tag, tag + "===onRightItemClick===>" + position);
        String todo_title = ((ToDoView) view).getTodoTitle();
        ViewSQLite viewSQLite = new ViewSQLite(getContext());
        Cursor cursor = viewSQLite.query(Constants.TABLE_NAME, null, "todo_title = ?", new String[]{todo_title}, null, null, null);
        if (cursor.moveToNext()) {
            int todo_total_time = cursor.getInt(cursor.getColumnIndex("todo_total_time"));
            int todo_plan_time = cursor.getInt(cursor.getColumnIndex("todo_plan_time"));
            int todo_current_time = cursor.getInt(cursor.getColumnIndex("todo_time"));
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra("title", todo_title);//标题
            intent.putExtra("total_time", todo_total_time);//总时间
            intent.putExtra("todo_plan_time", todo_plan_time);//计划完成时间
            intent.putExtra("todo_current_time", todo_current_time);//当前todo时间
            startActivityForResult(intent, Constants.REQUEST_CODE_PROGRESS);
        }

    }

    @Override
    public void onRightItemClick(View view, int position) {
//        LogUtils.e(tag, tag + "===onLeftItemClick===>" + position);
        String todo_title = ((ToDoView) view).getTodoTitle();
        ViewSQLite viewSQLite = new ViewSQLite(getContext());
        LogUtils.e(tag,"todo_title==="+todo_title);
        try {
            Cursor cursor = viewSQLite.query(Constants.TABLE_NAME, null, "todo_title = ?", new String[]{todo_title}, null, null, null);
            if (cursor.moveToNext()) {
//            LogUtils.e(tag,"time==="+time);
                Intent intent = new Intent(getContext(), CountProgressActivity.class);
                intent.putExtra("todo_title", todo_title);
                intent.putExtra("todo_time", cursor.getInt(cursor.getColumnIndex("todo_time")));
                startActivityForResult(intent, Constants.REQUEST_CODE_PROGRESS);
            }
        } finally {
            viewSQLite.closedb();
        }
    }

    public void showPopFormBottom(View view, int position) {
        ButtomWindow buttomWindow = new ButtomWindow(getActivity());
        //showAtLocation(View parent, int gravity, int x, int y)
        buttomWindow.setBottomWindowListener(this);
        buttomWindow.setTitle(mAdapter.getTitle(position));
        buttomWindow.setItemView((ToDoView)view);
        buttomWindow.setItemPosition(position);
        buttomWindow.showAtLocation(getView(), Gravity.CENTER, 0, 0);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mTodoDataObserver.isUnsubscribed()) {
            mTodoDataObserver.unsubscribe();
        }
    }

    @Override
    public void stickClick(View view, int positon) {
        ViewSQLite viewSQLite = new ViewSQLite(getContext());
        try (Cursor cursor = viewSQLite.query(Constants.TABLE_NAME, null, "todo_title=?", new String[]{mAdapter.getTitle(positon)}, null, null, null)) {
            cursor.moveToNext();
            int state = cursor.getInt(cursor.getColumnIndex("todo_stick"));
            mAdapter.refreshAll(positon, state);
        } finally {
            viewSQLite.closedb();

        }
//        mAdapter.
    }

    @Override
    public void remindClick(View view, int positon) {
//        LogUtils.e(tag, (view.getParent().getClass().getSimpleName()) + "===position" + positon);
        Intent intent = new Intent(getContext(), RemindService.class);
        intent.putExtra("time", 35);
        getContext().startService(intent);
    }

    @Override
    public void markClick(View view, int positon) {
        ViewSQLite viewSQLite = new ViewSQLite(getContext());
        ContentValues values = new ContentValues();
        values.put("todo_state", 1);
        values.put("todo_day_index", 1);
        values.put("todo_day_total_time", 35);
        viewSQLite.update(Constants.TABLE_NAME, values, "todo_title=?", new String[]{mAdapter.getTitle(positon)});
        mAdapter.refresh(positon);
    }

    @Override
    public void editClick(View view, int positon) {
    }

    @Override
    public void deleteClick(View view, int positon) {
        //待优化代码---------------------------------------------------
        String todo_title = mAdapter.getTitle(positon);
        ViewSQLite viewSQLite = new ViewSQLite(getContext());
        try {
            viewSQLite.delete(todo_title);
        } finally {
            viewSQLite.closedb();
        }
        mAdapter.removeData(positon);
    }

//    public void addItem() {
//        mTodoDataObserver = new Subscriber<ToDoData>() {
//            @Override
//            public void onCompleted() {
//            }
//
//            @Override
//            public void onError(Throwable e) {
//            }
//
//            @Override
//            public void onNext(ToDoData toDoData) {
//                mAdapter.addData(toDoData);
//            }
//        };
//
//        mObservable = Observable.create(new Observable.OnSubscribe<ToDoData>() {
//            @Override
//            public void call(Subscriber<? super ToDoData> subscriber) {
//
//            }
//        });
//        mObservable.subscribeOn(Schedulers.io()); // 指定 subscribe() 发生在 IO 线程
//        mObservable.observeOn(AndroidSchedulers.mainThread());// 指定 Subscriber 的回调发生在主线程
//        mObservable.subscribe(mTodoDataObserver);
//    }

    @Override
    public void onScrollTop(int position) {
        mRecyclerView.smoothScrollToPosition(position);//添加Item之后自动滑动到顶端
//        LogUtils.e(tag, tag + position);
    }

    public class ViewHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constants.CREATE_TODO:
                    Bundle bundle = msg.getData();
                    String title = bundle.getString("title");
                    int time = bundle.getInt("time");
                    int state = bundle.getInt("state");
                    int progress = bundle.getInt("progress");
                    int drawColor = bundle.getInt("drawColor");
                    int longPlan = bundle.getInt("long");
                    ToDoData todoData = new ToDoData(getContext(), title, time, state, progress, drawColor,longPlan);
                    mAdapter.addDataOnScroll(todoData);
                    break;
                case Constants.DELETE_TODO:
                    break;
                case Constants.UPDATE_TODO:
                    break;
            }
        }
    }
}
