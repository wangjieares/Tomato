package www.atomato.com.tomato.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import www.atomato.com.tomato.utils.BaseFragment;
import www.atomato.com.tomato.R;

/**
 * Created by wangjie on 16-11-17.
 */

public class MoreFragment extends BaseFragment {
    private View view = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_more, container, false);
        return view;
    }

}
