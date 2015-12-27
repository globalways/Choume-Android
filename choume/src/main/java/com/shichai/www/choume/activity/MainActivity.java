package com.shichai.www.choume.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.globalways.proto.nano.Common;
import com.globalways.user.nano.UserCommon;
import com.outsouring.crowdfunding.R;
import com.shichai.www.choume.activity.chou.ChouListActvity;
import com.shichai.www.choume.activity.mine.*;
import com.shichai.www.choume.application.MyApplication;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.manager.CfUserManager;
import com.shichai.www.choume.tools.LocalDataConfig;
import com.shichai.www.choume.tools.PicassoImageLoader;
import com.shichai.www.choume.tools.Tool;
import com.shichai.www.choume.tools.UITools;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    public static final String ISLOGIN = "isLogin";
    private static final int CODE_TO_LOGIN = 10001;
    private static final int CODE_TO_REGISTER = 10002;
    private boolean isLogin = false;
    //nologin
    private Button btnToLogin;
    private TextView toRegister;
    //logined
    private PicassoImageLoader imageLoader;
    private TextView tvUserName;
    private ImageView ivAvatar;
    private View dot1,dot2,dot3,dot4;
    private RelativeLayout rlNoLoginView;
    private ViewPager viewPager;
    private ViewPagerTask pagerTask;
    private ScheduledExecutorService scheduled;
    private int currPage = 0;//当前显示的页
    private int oldPage = 0;//上一次显示的页
    private ArrayList<ImageView> imageSource = null;//图片资源
    private ArrayList<View> dots = null;//点
    private boolean isContinue = true;

    private MyPagerAdapter adapter;

    private MyTouchListener touchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMainActionBar();
        initViews();
        isLogin = getIntent().getExtras().getBoolean(ISLOGIN);
        showLeftPanelView(isLogin);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showLeftPanelView(isLogin);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CODE_TO_LOGIN:
                if (resultCode == Activity.RESULT_OK) {
                    showLeftPanelView(true);
                } else {
                    showLeftPanelView(false);
                }
                break;
            case CODE_TO_REGISTER:
                if (resultCode == Activity.RESULT_OK) {
                    showLeftPanelView(true);
                } else {
                    showLeftPanelView(false);
                }
                break;
        }
    }

    private void initMainActionBar(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();

        ab.setHomeAsUpIndicator(R.mipmap.ico_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        View actionbarLayout = LayoutInflater.from(this).inflate(
                R.layout.actionbar_main_layout, null);
        ((TextView)actionbarLayout.findViewById(R.id.textView)).setText(getString(R.string.main_title));
        ab.setCustomView(actionbarLayout);
        ab.setDisplayShowCustomEnabled(true);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        if (mNavigationView != null) {

            mNavigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(MenuItem menuItem) {

                            menuItem.setChecked(false);

                            switch (menuItem.getItemId()) {
                                case R.id.my_sponsor:
                                    startActivity(new Intent(MainActivity.this, MySponsorActivity.class));
                                    break;
                                case R.id.my_join:
                                    startActivity(new Intent(MainActivity.this, MyJoinActivity.class));
                                    break;
                                case R.id.my_collection:
                                    startActivity(new Intent(MainActivity.this, MyCollectionActivity.class));
                                    break;
                                case R.id.my_message:
                                    startActivity(new Intent(MainActivity.this, MyMessageActivity.class));
                                    break;
                                case R.id.my_wealth:
                                    startActivity(new Intent(MainActivity.this, MyWealthActivity.class));
                                    break;
                                case R.id.logout:
                                    logout();
                                    break;
                                case R.id.account_option:
                                    startActivity(new Intent(MainActivity.this, OptionActivity.class));
                                    break;
                            }
                            mDrawerLayout.closeDrawers();
                            return false;
                        }
                    });

            mNavigationView.getHeaderView(0).findViewById(R.id.im_head).setOnClickListener(this);
            //mNavigationView.getHeaderView(0).findViewById(R.id.head_user).setVisibility(View.GONE);
            //mNavigationView.getHeaderView(0).findViewById(R.id.head_login).setVisibility(View.VISIBLE);
        }


    }

    private void initViews(){
        initViewPager();
        rlNoLoginView = (RelativeLayout) findViewById(R.id.rlNoLoginView);
        showLeftPanelView(isLogin);

        //logined
        tvUserName = (TextView) mNavigationView.getHeaderView(0).findViewById(R.id.tv_username);
        ivAvatar   = (ImageView) mNavigationView.getHeaderView(0).findViewById(R.id.im_head);
        //nologin
        btnToLogin = (Button) findViewById(R.id.btnToLogin);
        btnToLogin.setOnClickListener(this);
        toRegister = (TextView) findViewById(R.id.tvToRegister);
        toRegister.setOnClickListener(this);
    }

    /**
     * 左边面板
     * @param isLogin
     */
    private void showLeftPanelView(boolean isLogin){
        if(this.isLogin != isLogin){
            this.isLogin = isLogin;
        }
        if (isLogin){
            rlNoLoginView.setVisibility(View.GONE);
            mNavigationView.setVisibility(View.VISIBLE);
            setUserNameAndAvatar();
        } else {
            rlNoLoginView.setVisibility(View.VISIBLE);
            mNavigationView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置左侧面板头像和用户名
     */
    private void setUserNameAndAvatar(){
        if(imageLoader == null) {
            imageLoader = new PicassoImageLoader(this);
        }
        if (!isLogin)
            return;
        tvUserName.setText(MyApplication.getCfUser().user.tel);
        imageLoader.loadUrlImageToView(MyApplication.getCfUser().user.avatar, 200, 200, R.mipmap.test_head, R.mipmap.test_head, ivAvatar);

    }


    private void logout(){
        UserCommon.LogoutParam logoutParam = new UserCommon.LogoutParam();
        logoutParam.token = LocalDataConfig.getToken(this);
        CfUserManager.getInstance().logoutApp(logoutParam, new ManagerCallBack<Common.Response>() {
            @Override
            public void success(Common.Response result) {
                LocalDataConfig.logout(MainActivity.this);
                showLeftPanelView(false);
            }

            @Override
            public void warning(int code, String msg) {
                UITools.ToastMsg(MainActivity.this, msg);
            }

            @Override
            public void error(Exception e) {
                UITools.ToastServerError(MainActivity.this);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.im_head:
                intent = new Intent(this,IndividualActivity.class);
                startActivity(intent);
                break;
            case R.id.big_difficult:
                intent = new Intent(this,ChouListActvity.class);
                intent.putExtra(com.shichai.www.choume.common.Common.TYPE,"世纪难题");
                startActivity(intent);
                break;
            case R.id.get_fun:
                intent = new Intent(this,ChouListActvity.class);
                intent.putExtra(com.shichai.www.choume.common.Common.TYPE,"筹乐子");
                startActivity(intent);
                break;
            case R.id.get_love:
                intent = new Intent(this,ChouListActvity.class);
                intent.putExtra(com.shichai.www.choume.common.Common.TYPE,"筹爱心");
                startActivity(intent);
                break;
            case R.id.get_money:
                intent = new Intent(this,ChouListActvity.class);
                intent.putExtra(com.shichai.www.choume.common.Common.TYPE,"筹票子");
                startActivity(intent);
                break;
            case R.id.go_where:
                intent = new Intent(this,ChouListActvity.class);
                intent.putExtra(com.shichai.www.choume.common.Common.TYPE,"周末去哪");
                startActivity(intent);
                break;
            case R.id.hot:
                intent = new Intent(this,ChouListActvity.class);
                intent.putExtra(com.shichai.www.choume.common.Common.TYPE,"热门众筹");
                startActivity(intent);
                break;
            case R.id.limited:
                intent = new Intent(this,ChouListActvity.class);
                intent.putExtra(com.shichai.www.choume.common.Common.TYPE,"限时特筹");
                startActivity(intent);
                break;
            case R.id.miao:
                intent = new Intent(this,ChouListActvity.class);
                intent.putExtra(com.shichai.www.choume.common.Common.TYPE, "一元秒筹");
                startActivity(intent);
                break;
            case R.id.most_get:
                intent = new Intent(this,ChouListActvity.class);
                intent.putExtra(com.shichai.www.choume.common.Common.TYPE,"非筹不可");
                startActivity(intent);
                break;
            case R.id.btnToLogin:
                startActivityForResult(new Intent(MainActivity.this, LoginActivity.class),CODE_TO_LOGIN);
                break;
            case R.id.tvToRegister:
                startActivityForResult(new Intent(MainActivity.this, RegisterActivity.class),CODE_TO_REGISTER);
                break;
        }
    }


    private void initViewPager(){
        dot1 = findViewById(R.id.dot1);
        dot2 = findViewById(R.id.dot2);
        dot3 = findViewById(R.id.dot3);
        dot4 = findViewById(R.id.dot4);
        viewPager = (ViewPager)findViewById(R.id.viewpager_guide);
        imageSource = new ArrayList<ImageView>();
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                150);
        for(int i = 0; i < 4;i++){
            ImageView mImageView = new ImageView(this);
            mImageView.setLayoutParams(mParams);
            mImageView.setAdjustViewBounds(true);
            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImageView.setImageDrawable(getResources().getDrawable(R.mipmap.guangyuan_1));
            imageSource.add(mImageView);
        }

        dots = new ArrayList<View>();

        dots.add(dot1);
        dots.add(dot2);
        dots.add(dot3);
        dots.add(dot4);

        viewPager.setOffscreenPageLimit(1);

        adapter = new MyPagerAdapter();
        viewPager.setAdapter(adapter);
        MyPageChangeListener listener = new MyPageChangeListener();
        viewPager.setOnPageChangeListener(listener);


        scheduled =  Executors.newSingleThreadScheduledExecutor();
        pagerTask = new ViewPagerTask();
        scheduled.scheduleAtFixedRate(pagerTask, 2, 2, TimeUnit.SECONDS);
        touchListener = new MyTouchListener();
        viewPager.setOnTouchListener(touchListener);
    }

    class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return dots.size();
        }
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageSource.get(position));
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageSource.get(position));
            return imageSource.get(position);
        }
    }

     class MyTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            // TODO Auto-generated method stub
            switch (event.getAction())
            {
                case MotionEvent.ACTION_MOVE:
                    isContinue = false;
                    break;
                case MotionEvent.ACTION_UP:
                default:
                    isContinue = true;
                    break;
            }
            return false;
        }

    }

     class ViewPagerTask implements Runnable{
        @Override
        public void run() {
            if(isContinue){
                currPage =(currPage+ 1)%dots.size();
                handler.sendEmptyMessage(0);
            }

        }
    }
     Handler handler= new Handler(){
        public void handleMessage(Message msg) {
            viewPager.setCurrentItem(currPage);
        };
    };

     class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            dots.get(position).setBackgroundResource(R.drawable.dot_circle_white);
            dots.get(oldPage).setBackgroundResource(R.drawable.dot_circle_gray);
            oldPage = position;
            currPage = position;
        }
    }

}
