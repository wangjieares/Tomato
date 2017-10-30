package www.atomato.com.tomato.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import www.atomato.com.tomato.utils.LogUtils;

/**
 * Created by wangjie on 2016-11-20.
 */

public class MyRecyclerView extends RecyclerView {
    private View emptyView;

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return super.onTouchEvent(e);
    }

    public void setEmpty(View v) {
//        LogUtils.e("ssss", "setEmtpy");
        emptyView = v;
    }

    private AdapterDataObserver observer = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            showEmptyView();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            showEmptyView();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            showEmptyView();
        }
    };

    public void showEmptyView() {
        Adapter<?> adapter = getAdapter();
        if (adapter != null && emptyView != null) {
            if (adapter.getItemCount() == 0) {
                emptyView.setVisibility(VISIBLE);
                MyRecyclerView.this.setVisibility(GONE);
            } else {
                emptyView.setVisibility(GONE);
                MyRecyclerView.this.setVisibility(VISIBLE);
            }
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
            observer.onChanged();
        }
    }
}
