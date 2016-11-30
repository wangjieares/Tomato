package www.atomato.com.tomato.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
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
                    break;
                } else if (checkedId == R.id.shortRadio) {
                    bundle.putInt("plan", Constants.SHORT_RADIO);
//                    LogUtils.e(tag, "plan==" + Constants.SHORT_RADIO);
                    break;
                }
                break;
            case R.id.timeRadioGroup:
                if (checkedId == R.id.customRadio) {
//                    bundle.putInt("time", Constants.CUSTOM_RADIO);
//                    LogUtils.e(tag, "time==" + Constants.CUSTOM_RADIO);
                    ButtomDialogUtils.showDialog(this, this);

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
                    bundle.putInt("day", Constants.ONE_DAY_RADIO);
//                    LogUtils.e(tag, "day==" + Constants.ONE_DAY_RADIO);

                    break;
                } else if (checkedId == R.id.everyDayRadio) {
                    bundle.putInt("day", Constants.EVERY_DAY_RADIO);
//                    LogUtils.e(tag, "day==" + Constants.EVERY_DAY_RADIO);
                    break;
                }
                break;
        }

    }

    @OnClick(R.id.main_activity_add_item_commit)
    public void onClick() {
        if (!activityItemTitle.getText().toString().equals("")) {
            bundle.putString("title", activityItemTitle.getText().toString());
            bundle.putLong("cteate", SystemClock.currentThreadTimeMillis());
//            LogUtils.e(tag, activityItemTitle.getText().toString());
            Intent intent = new Intent();
            intent.putExtra("data", bundle);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            setResult(RESULT_CANCELED);
            Toast.makeText(this, "请输入ToD名称！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setValue(int value) {
        bundle.putInt("time", value);
        customRadio.setText(String.valueOf(value));
    }
}
