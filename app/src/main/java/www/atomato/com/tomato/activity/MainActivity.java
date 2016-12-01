package www.atomato.com.tomato.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import www.atomato.com.tomato.R;
import www.atomato.com.tomato.adapter.FragmentViewPagerAdapter;
import www.atomato.com.tomato.constants.Constants;
import www.atomato.com.tomato.fragment.MoreFragment;
import www.atomato.com.tomato.fragment.OneFragment;
import www.atomato.com.tomato.utils.BaseActivity;
import www.atomato.com.tomato.utils.LogUtils;
import www.atomato.com.tomato.viewpager.MyViewPager;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, ViewPager.OnPageChangeListener {
    private String tag = getClass().getSimpleName();
    private FragmentViewPagerAdapter mAdapter;
    private List<Fragment> mFragmentList;
    private ImageButton mOneButton, mMoreButton;//底部按钮
    private OneFragment mOneFragment;
    private MoreFragment mMoreFragment;
    private MyViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWindow(getResources().getColor(R.color.toolBar));
        initView();
    }

    @TargetApi(21)
    private void initWindow(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
//            LogUtils.e(tag,"statusBarHeight==="+statusBarHeight);
            // 绘制一个和状态栏一样高的矩形
            View statusView = new View(this);
            ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    statusBarHeight);
            // 生成一个状态栏大小的矩形
            statusView.setLayoutParams(params);
            statusView.setBackgroundColor(color);
            // 添加 statusView 到布局中
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            decorView.addView(statusView);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }


    private void initView() {
        //start按钮初始化
        mOneButton = (ImageButton) findViewById(R.id.oneButton);
        mMoreButton = (ImageButton) findViewById(R.id.moreButton);
        mOneButton.setOnClickListener(this);
        mMoreButton.setOnClickListener(this);
        //end按钮初始化

        //start fragment初始化
        mOneFragment = new OneFragment();
        mMoreFragment = new MoreFragment();
        //end fragment初始化

        //start VIewPager初始化
        mFragmentList = new ArrayList<>();
        mFragmentList.add(mOneFragment);
        mFragmentList.add(mMoreFragment);
        mViewPager = (MyViewPager) findViewById(R.id.viewPager);
//        adapter = new MyViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        mAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), mFragmentList);
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

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (0 == mViewPager.getCurrentItem()) {
            menu.getItem(0).setIcon(R.drawable.activity_main_add_one);
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
            Toast.makeText(this, "正在开发中", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }


    //AddItemActivity回调执行方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.REQUEST_CODE_ADD://和startActivityForResult的对应 判断哪个Activity
                if (resultCode == RESULT_OK) {//setResult返回
                    Bundle bundle = data.getBundleExtra("data");
//                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        addToDo(bundle);
                    }
                    break;
                }
        }
    }

    public void addToDo(Bundle bundle) {
        int plan = bundle.getInt("plan");
        int time = bundle.getInt("time");
        int day = bundle.getInt("day");
        //默认情况创建Todo
        if (plan == 0 && time == 0 && day == 0) {
            LogUtils.e(tag, plan + "===" + time + "===" + day);
            //发消息通知更改
            bundle.putInt("time", 35);
            bundle.putInt("state", 0);
            bundle.putInt("progress", 0);
            bundle.putInt("drawColor", Color.parseColor("#1ABC9C"));
            bundle.putInt("day", Constants.EVERY_DAY_RADIO);
            bundle.putInt("plan", Constants.DEFAULT_RADIO);
            bundle.putInt("type", Constants.LONG_RADIO);
            Message message = OneFragment.handler.obtainMessage();
            message.what = Constants.CREATE_TODO;
            message.setData(bundle);
            OneFragment.handler.handleMessage(message);
        } else {
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
            if (time == Constants.CUSTOM_RADIO) {

            }
            if (time == Constants.SHORT_RADIO) {
                bundle.putInt("todo_plan", Constants.SHORT_RADIO);
            }
            if (time == Constants.LONG_RADIO) {
                bundle.putInt("todo_plan", Constants.LONG_RADIO);
            }
            bundle.putInt("state", 0);
            bundle.putInt("progress", 0);
            bundle.putInt("drawColor", Color.parseColor("#1ABC9C"));
            LogUtils.e(tag, plan + "===" + time + "===" + day + "---title" + bundle.getString("title"));
            //发消息通知更改
            Message message = OneFragment.handler.obtainMessage();
            message.what = Constants.CREATE_TODO;
            message.setData(bundle);
            OneFragment.handler.handleMessage(message);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        LogUtils.e(tag, "getItemId");
        if (id == R.id.nav_camera) {
//            item.setCheckable(true);
//            item.setChecked(true);
            Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);

            startActivity(intent);
            LogUtils.e(tag, "nav_camera");
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
//            item.setCheckable(true);
//            item.setChecked(true);
            LogUtils.e(tag, "nav_camera");
        } else if (id == R.id.nav_slideshow) {
//            item.setCheckable(true);
//            item.setChecked(true);
            LogUtils.e(tag, "nav_camera");
        } else if (id == R.id.nav_manage) {
//            item.setCheckable(true);
//            item.setChecked(true);
            LogUtils.e(tag, "nav_camera");
        } else if (id == R.id.nav_share) {
//            item.setCheckable(true);
//            item.setChecked(true);
            LogUtils.e(tag, "nav_camera");
        } else if (id == R.id.nav_send) {
//            item.setCheckable(true);
//            item.setChecked(true);
            LogUtils.e(tag, "nav_camera");
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
                LogUtils.d(tag, tag + "===oneButton Click");
                mViewPager.setCurrentItem(0);
                invalidateOptionsMenu();
                break;
            case R.id.moreButton:
                LogUtils.d(tag, tag + "===moreButton Click");
                mViewPager.setCurrentItem(1);
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
                invalidateOptionsMenu();
                break;
            case 1:
                invalidateOptionsMenu();
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
