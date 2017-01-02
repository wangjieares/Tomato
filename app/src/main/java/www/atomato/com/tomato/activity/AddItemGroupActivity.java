package www.atomato.com.tomato.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
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
    EditText title;
    Button ok;
    Intent intent ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_group);
        ScreenUtils.setMainColor(this,getResources().getColor(R.color.toolBarDark));
        init();
    }

    private void init() {
        intent = new Intent();
        title = (EditText)findViewById(R.id.main_activity_add_item_title);
        ok = (Button)findViewById(R.id.main_activity_add_item_commit);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!title.getText().toString().equals("")){
                    intent.putExtra("title", title.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    ToastUtils.show(AddItemGroupActivity.this,"请输入标题名称!");
                }
            }
        });
    }

}
