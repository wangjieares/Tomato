package www.atomato.com.tomato.fragment;

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

import www.atomato.com.tomato.R;
import www.atomato.com.tomato.activity.CountProgressActivity;
import www.atomato.com.tomato.adapter.ItemListener;
import www.atomato.com.tomato.adapter.MyRecyclerViewAdapter;
import www.atomato.com.tomato.constants.Constants;
import www.atomato.com.tomato.data.SpaceItemDecoration;
import www.atomato.com.tomato.data.ToDoData;
import www.atomato.com.tomato.pop.ButtomWindow;
import www.atomato.com.tomato.sqlite.ViewSQLite;
import www.atomato.com.tomato.utils.BaseFragment;
import www.atomato.com.tomato.utils.LogUtils;
import www.atomato.com.tomato.utils.ToastUtils;

/**
 * Created by wangjie on 16-11-17.
 */

public class OneFragment extends BaseFragment implements ItemListener.OnItemClickListener {
    private View mView = null;
    private RecyclerView mRecyclerView;
    private List<ToDoData> mList;
    private MyRecyclerViewAdapter mAdapter;
    public ViewHandler handler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.d(tag, "===onCreateView");
        mView = inflater.inflate(R.layout.fragment_one, container, false);
        init();
        return mView;
    }

    private void init() {
        handler = new ViewHandler();
        //start RecyclerView
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.fragment_recyclerView);
        mList = new ArrayList<>();
        mAdapter = new MyRecyclerViewAdapter(getActivity(), mList);
//        mAdapter.addData(1,"背单词",35,true,100,Color.RED);
//        mAdapter.addData(1,"读课文",35,false,50,Color.BLUE);
//        mAdapter.addData(1,"android开发",35,true,5,Color.BLACK);
        //设置布局管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(10));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new ItemListener(getActivity(), this));
        //end RecyclerView
//        ViewSQLite viewSQLite = new ViewSQLite(getContext());
//        viewSQLite.insert(new ToDoData("android开发", 35, 1, 5, Color.BLACK));
        initToDo();
    }

    public void initToDo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ViewSQLite viewSQLite = new ViewSQLite(getContext());
                try (Cursor cursor = viewSQLite.query()) {
                    while (cursor.moveToNext()) {
                        int id = cursor.getInt(cursor.getColumnIndex("_id"));
                        String title = cursor.getString(cursor.getColumnIndex("todo_title"));
                        int time = cursor.getInt(cursor.getColumnIndex("todo_time"));
                        int state = cursor.getInt(cursor.getColumnIndex("todo_state"));
                        int progress = cursor.getInt(cursor.getColumnIndex("todo_progress"));
                        int color = cursor.getInt(cursor.getColumnIndex("todo_color"));
//                        Bundle bundle = new Bundle();
//                        bundle.putInt("id", id);
//                        bundle.putString("title", title);
//                        bundle.putInt("time", time);
//                        bundle.putInt("state", state);
//                        bundle.putInt("progress", progress);
//                        bundle.putInt("color", color);
                        mAdapter.addData(new ToDoData(title, time, state, progress, color));
                    }
                }
            }
        }).start();
    }

    public MyRecyclerViewAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void onLongRightItenClick(View view, int position) {
        LogUtils.e(tag, tag + "===onLongRightItenClick===>" + position);
        showPopFormBottom(view);
//        ButtomAlertDialog.showDialog(getContext());
    }

    public void showPopFormBottom(View view) {
        ButtomWindow buttomWindow = new ButtomWindow(getActivity());
        //showAtLocation(View parent, int gravity, int x, int y)
        buttomWindow.showAtLocation(getView(), Gravity.CENTER, 0, 0);
    }

    @Override
    public void onLeftItemClick(View view, int position) {
        LogUtils.e(tag, tag + "===onLeftItemClick===>" + position);
        Intent intent = new Intent(getContext(), CountProgressActivity.class);
        startActivityForResult(intent,Constants.REQUEST_CODE_PROGRESS);
    }

    @Override
    public void onRightItemClick(View view, int position) {
        LogUtils.e(tag, tag + "===onRightItemClick===>" + position);
    }

    public class ViewHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constants.CREATE_TODO:
                    Bundle bundle = msg.getData();
//                    String title = bundle.getString("title");
                    String title = bundle.getString("title");//------------
                    int time = bundle.getInt("time",35);
                    int state = bundle.getInt("state",0);
                    int progress = bundle.getInt("progress",0);
                    int drawColor = bundle.getInt("drawColor");
                    int day = bundle.getInt("day");
                    int plan = bundle.getInt("plan");
                    int type = bundle.getInt("type");
                    ToDoData todoData = new ToDoData(getContext(), title, time, state, progress, drawColor, day, plan, type);
                    mAdapter.addData(todoData);
                    break;
                case Constants.DELETE_TODO:
                    break;
                case Constants.UPDATE_TODO:
                    break;
            }
        }
    }
}
