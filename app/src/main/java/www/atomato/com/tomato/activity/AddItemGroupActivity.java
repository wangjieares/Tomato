package www.atomato.com.tomato.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import www.atomato.com.tomato.R;
import www.atomato.com.tomato.constants.Constants;
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
                //非空判断
                if (!title.getText().toString().equals("")){
                    //获取外围组名称
                    intent.putExtra("title", title.getText().toString());
                    intent.putExtra("group_name", getIntent().getStringExtra("group_name"));
                    setResult(Constants.REQUEST_CODE_ADD_GROUP_ITEM, intent);
                    finish();
                }else{
                    ToastUtils.show(AddItemGroupActivity.this,"请输入标题名称!");
                }
            }
        });
    }

}
