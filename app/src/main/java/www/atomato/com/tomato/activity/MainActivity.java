package www.atomato.com.tomato.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.mob.tools.gui.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import www.atomato.com.tomato.R;
import www.atomato.com.tomato.adapter.FragmentViewPagerAdapter;
import www.atomato.com.tomato.constants.Constants;
import www.atomato.com.tomato.fragment.MoreFragment;
import www.atomato.com.tomato.fragment.OneFragment;
import www.atomato.com.tomato.service.MessageService;
import www.atomato.com.tomato.utils.BaseActivity;
import www.atomato.com.tomato.utils.LogUtils;
import www.atomato.com.tomato.utils.ScreenUtils;
import www.atomato.com.tomato.view.FragmentViewPager;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, ViewPager.OnPageChangeListener {
    private String tag = getClass().getSimpleName();
    private ImageButton mOneButton, mMoreButton;//底部按钮
    private FragmentViewPager mViewPager;
    private MoreFragment mMoreFragment;
    private ServiceConnection connection;
    private boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScreenUtils.setMainColor(this, getResources().getColor(R.color.toolBar));
        initView();
    }


    private void initView() {
        //start按钮初始化
        mOneButton = (ImageButton) findViewById(R.id.oneButton);
        mMoreButton = (ImageButton) findViewById(R.id.moreButton);
        mOneButton.setOnClickListener(this);
        mMoreButton.setOnClickListener(this);
        mOneButton.setImageResource(R.drawable.todo_one_selector);
        mMoreButton.setImageResource(R.drawable.todo_more);
        //end按钮初始化
        //start fragment初始化
        OneFragment mOneFragment = new OneFragment();
        mMoreFragment = new MoreFragment();
        //end fragment初始化
        //start VIewPager初始化
        final List<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(mOneFragment);
        mFragmentList.add(mMoreFragment);
        mViewPager = (FragmentViewPager)findViewById(R.id.viewPager);
//      adapter = new MyViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        FragmentViewPagerAdapter mAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(this);
        //end   VIewPager初始化
//        mViewPager.setOnDragListener();
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemTextAppearance(R.style.MenuTextStyle);
        setConnection();
    }

    //获取时间
    private int getTime() {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    private void setConnection() {
        Intent intent = new Intent(MainActivity.this, MessageService.class);
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
//                messageBean=MessageItem.Stub.asInterface(service);
                mBound = true;
                LogUtils.e(tag, "连接成功");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mBound = false;
                LogUtils.e(tag, "连接断开");
            }
        };
        this.bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (0 == mViewPager.getCurrentItem()) {
            menu.getItem(0).setIcon(R.drawable.fragment_more_layout_section_add);
        }
        if (1 == mViewPager.getCurrentItem()) {
            menu.getItem(0).setIcon(R.drawable.activity_main_add_more);
        }
        return true;
//        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (0 == mViewPager.getCurrentItem()) {
            int id = item.getItemId();
            //noinspection SimplifiableIfStatement
            if (id == R.id.main_activity_add_item) {
                Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
                startActivityForResult(intent, Constants.REQUEST_CODE_ADD);
                return true;
            }
        }
        if (1 == mViewPager.getCurrentItem()) {
            Intent intent = new Intent(MainActivity.this, AddItemGroupActivity.class);
            startActivityForResult(intent, Constants.REQUEST_CODE_ADD_GROUP);
        }
        return super.onOptionsItemSelected(item);
    }


    //AddItemActivity回调执行方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.REQUEST_CODE_ADD://和startActivityForResult的对应 判断哪个Activity
                // if (resultCode == RESULT_OK) {//setResult返回
                // }
                Bundle bundle = data.getBundleExtra("data");
//                    Bundle bundle = data.getExtras();
                if (bundle != null) {
                    addToDo(bundle);
                }
                break;
            case Constants.REQUEST_CODE_ADD_GROUP:
                if (data != null) {
                    SharedPreferences sharedPreferences = getSharedPreferences("Group", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    int i = sharedPreferences.getInt("group_num", 0);
                    editor.putInt("group_num", i + 1);
                    editor.putString("group_name_" + (i + 1), data.getStringExtra("title"));
//                LogUtils.e(tag,"group_name_"+i);
                    editor.apply();
                    mMoreFragment.addGroup(data.getStringExtra("title"));
                }
                break;
        }
    }

    //该功能实现较为繁琐,应该修改 2016.12.25
    public void addToDo(Bundle bundle) {
        int plan = bundle.getInt("todo_plan");
        int time = bundle.getInt("todo_time");
        int day = bundle.getInt("day");//没实现功能
        //默认情况创建Todo
        if (plan == 0 && time == 0 && day == 0) {
            LogUtils.e(tag, plan + "===" + time + "===" + day);
            //发消息通知更改
            bundle.putInt("todo_time", 35);
            bundle.putInt("todo_state", 0);
            bundle.putInt("todo_progress", 0);
            bundle.putInt("todo_drawColor", Color.parseColor("#1ABC9C"));
            bundle.putInt("todo_plan_time", bundle.getInt("time") * 10);//短期十个一组
            Message message = OneFragment.handler.obtainMessage();
            message.what = Constants.CREATE_TODO;
            message.setData(bundle);
            OneFragment.handler.handleMessage(message);
        } else {
            bundle.putInt("todo_plan_time", bundle.getInt("todo_time") * 10);//短期十个一组
            bundle.putInt("todo_state", 0);
            bundle.putInt("todo_progress", 0);
            bundle.putInt("todo_destory", 0);
            if (time == Constants.EVERY_DAY_RADIO) {//如果每天 销毁时间为0
                bundle.putInt("todo_destory", 0);
            }
            if (time == Constants.ONE_DAY_RADIO) {//一天 24小时 或者执行完毕销毁 销毁值为1
                bundle.putInt("todo_destory", 1);
            }
            if (time == Constants.DEFAULT_RADIO) {
                bundle.putInt("todo_time", 35);
            }
            if (time == Constants.TIME_RADIO) {
                bundle.putInt("todo_time", 45);
            }
            if (time == Constants.SHORT_RADIO) {
                bundle.putInt("todo_plan_time", bundle.getInt("todo_time") * 10);//短期十个一组
            }
            if (time == Constants.LONG_RADIO) {
                bundle.putInt("todo_plan_time", bundle.getInt("todo_time") * 100);//长期100个一组
            }
            LogUtils.e(tag, plan + "===" + time + "===" + day + "---title" + bundle.getString("todo_title"));
            //发消息通知更改
            Message message = OneFragment.handler.obtainMessage();
            message.what = Constants.CREATE_TODO;
            message.setData(bundle);
            OneFragment.handler.handleMessage(message);
        }

    }
    //返回键 返回DrawerLayout
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    //菜单跳转相应的Activity
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.statistics) {
//            item.setCheckable(true);
//            item.setChecked(true);
            Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.relax) {
            Intent intent = new Intent(MainActivity.this, RelaxActivity.class);
            startActivity(intent);
//            item.setCheckable(true);
//            item.setChecked(true);
        } else if (id == R.id.future_plan) {
            Intent intent = new Intent(MainActivity.this, FetureActivity.class);
            startActivity(intent);
//            item.setCheckable(true);
//            item.setChecked(true);
        } else if (id == R.id.setting) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
//            item.setCheckable(true);
//            item.setChecked(true);
        } else if (id == R.id.about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
//            item.setCheckable(true);
//            item.setChecked(true);
        } else if (id == R.id.feedback) {
//            item.setCheckable(true);
//            item.setChecked(true);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        int id = view.getId();
        switch (id) {
            case R.id.oneButton:
//                LogUtils.d(tag, tag + "===oneButton Click");
                mViewPager.setCurrentItem(0);
                mOneButton.setImageResource(R.drawable.todo_one_selector);
                mMoreButton.setImageResource(R.drawable.todo_more);
                invalidateOptionsMenu();
                break;
            case R.id.moreButton:
//                LogUtils.d(tag, tag + "===moreButton Click");
                mViewPager.setCurrentItem(1);
                mMoreButton.setImageResource(R.drawable.todo_more_selector);
                mOneButton.setImageResource(R.drawable.todo_one);
                invalidateOptionsMenu();
                break;
        }
    }

    //mViewPager监听
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                mOneButton.setImageResource(R.drawable.todo_one_selector);
                mMoreButton.setImageResource(R.drawable.todo_more);
                invalidateOptionsMenu();
                break;
            case 1:
                mMoreButton.setImageResource(R.drawable.todo_more_selector);
                mOneButton.setImageResource(R.drawable.todo_one);
                invalidateOptionsMenu();
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connection != null) {
            unbindService(connection);
        }
    }
}
