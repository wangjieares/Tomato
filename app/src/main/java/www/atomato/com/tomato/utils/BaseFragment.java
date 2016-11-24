package www.atomato.com.tomato.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wangjie on 16-11-17.
 */

public class BaseFragment extends Fragment {
    public String tag = getClass().getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();
        LogUtils.d(tag, tag + "===onStart");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d(tag, tag + "===onCreate");

    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.d(tag, tag + "===onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.d(tag, tag + "===onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtils.d(tag, tag + "===onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d(tag, tag + "===onDestroy");
    }
}
