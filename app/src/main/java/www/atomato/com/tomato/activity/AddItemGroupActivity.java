package www.atomato.com.tomato.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.atomato.com.tomato.R;
import www.atomato.com.tomato.constants.Constants;
import www.atomato.com.tomato.pop.ButtomDialogUtils;
import www.atomato.com.tomato.recall.DialogListener;
import www.atomato.com.tomato.sqlite.ViewSQLite;
import www.atomato.com.tomato.utils.ScreenUtils;
import www.atomato.com.tomato.utils.ToastUtils;

/**
 * Created by wangjie on 2016-11-20.
 */

public class AddItemGroupActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_group);
        init();
    }

    private void init() {
    }
}
