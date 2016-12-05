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
import www.atomato.com.tomato.utils.ToastUtils;

/**
 * Created by wangjie on 2016-11-20.
 */

public class AddItemActivity extends Activity implements RadioGroup.OnCheckedChangeListener, DialogListener {
    @BindView(R.id.everyDayRadio)
    RadioButton everyDayRadio;
    @BindView(R.id.oneDayRadio)
    RadioButton oneDayRadio;
    @BindView(R.id.dayRadioGroup)
    RadioGroup dayRadioGroup;
    @BindView(R.id.defaultDayRadio)
    RadioButton defaultDayRadio;
    @BindView(R.id.timeRadio)
    RadioButton timeRadio;
    @BindView(R.id.customRadio)
    RadioButton customRadio;
    @BindView(R.id.timeRadioGroup)
    RadioGroup timeRadioGroup;
    @BindView(R.id.shortRadio)
    RadioButton shortRadio;
    @BindView(R.id.longRadio)
    RadioButton longRadio;
    @BindView(R.id.planRadioGroup)
    RadioGroup planRadioGroup;
    private String tag = getClass().getSimpleName();
    @BindView(R.id.activity_item_title)
    EditText activityItemTitle;
    @BindView(R.id.main_activity_add_item_commit)
    Button mainActivityAddItemCommit;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        bundle = new Bundle();
        planRadioGroup.setOnCheckedChangeListener(this);
        timeRadioGroup.setOnCheckedChangeListener(this);
        dayRadioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int id = group.getId();
        switch (id) {
            case R.id.planRadioGroup:
                if (checkedId == R.id.longRadio) {
                    bundle.putInt("plan", Constants.LONG_RADIO);
//                    LogUtils.e(tag, "plan==" + Constants.LONG_RADIO);
                    ButtomDialogUtils.showLongDialog(this, this);
                    break;
                } else if (checkedId == R.id.shortRadio) {
                    bundle.putInt("long", 350);
//                    LogUtils.e(tag, "plan==" + Constants.SHORT_RADIO);
                    break;
                }
                break;
            case R.id.timeRadioGroup:
                if (checkedId == R.id.customRadio) {
//                    bundle.putInt("time", Constants.CUSTOM_RADIO);
//                    LogUtils.e(tag, "time==" + Constants.CUSTOM_RADIO);
                    ButtomDialogUtils.showCustromDialog(this, this);
                    break;
                } else if (checkedId == R.id.timeRadio) {
                    bundle.putInt("time", Constants.TIME_RADIO);
//                    LogUtils.e(tag, "time==" + Constants.TIME_RADIO);
                    break;
                } else if (checkedId == R.id.defaultDayRadio) {
                    bundle.putInt("time", Constants.DEFAULT_RADIO);
//                    LogUtils.e(tag, "time==" + Constants.DEFAULT_RADIO);
                    break;
                }
                break;
            case R.id.dayRadioGroup:
                if (checkedId == R.id.oneDayRadio) {
                    ToastUtils.show(this, "正在开发中");
                    break;
                } else if (checkedId == R.id.everyDayRadio) {
                    ToastUtils.show(this, "正在开发中");
                    break;
                }
                break;
        }

    }

    @OnClick(R.id.main_activity_add_item_commit)
    public void onClick() {
        if (!activityItemTitle.getText().toString().equals("")) {
            if (titleNotRepeat()) {
                bundle.putString("todo_title", activityItemTitle.getText().toString());
//            bundle.putLong("create", SystemClock.currentThreadTimeMillis());
//            LogUtils.e(tag, activityItemTitle.getText().toString());
                Intent intent = new Intent();
                intent.putExtra("data", bundle);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                ToastUtils.show(this, "标题重复，请重新输入！");
            }

        } else {
            setResult(RESULT_CANCELED);
            Toast.makeText(this, "请输入ToD名称！", Toast.LENGTH_SHORT).show();
        }
    }

    boolean titleNotRepeat() {
        ViewSQLite viewSQLite = new ViewSQLite(this);
        try {
            Cursor cursor = viewSQLite.query(Constants.TABLE_NAME, new String[]{"todo_title"}, null, null, null, null, null);
            while (cursor.moveToNext()) {
                String oldTitle = cursor.getString(cursor.getColumnIndex("todo_title"));
                String currentTitle = activityItemTitle.getText().toString();
                if (oldTitle.equals(currentTitle)) {
                    return false;
                }
            }
            return true;
        } finally {
            viewSQLite.closedb();
        }
    }

    @Override
    public void setCustromValue(int value) {
        bundle.putInt("time", value);
        customRadio.setText(String.valueOf(value));
    }

    @Override
    public void setLongValue(int value) {
        bundle.putInt("long", value);
        longRadio.setText(String.valueOf(value));
    }
}
