package com.shichai.www.choume.activity.sponsor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.outsouring.crowdfunding.R;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.activity.sponsor.fragment.BaseFragment;
import com.shichai.www.choume.activity.sponsor.fragment.FragmentChooseType;
import com.shichai.www.choume.activity.sponsor.fragment.FragmentProgramDetail;
import com.shichai.www.choume.activity.sponsor.fragment.FragmentPublish;
import com.shichai.www.choume.activity.sponsor.fragment.FragmentRewardWay;
import com.shichai.www.choume.activity.sponsor.fragment.OnNextListener;

import java.util.Iterator;
import java.util.List;

/**
 * Created by HeJianjun on 2015/12/24.
 */
public class SponsorActivity extends BaseActivity implements View.OnClickListener,OnNextListener{

    private ImageView imageView1,imageView2,imageView3,imageView4;

    private Button bt_next;

    private int nextIndex = 1;

    private String curFragmentTag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor);
        initActionBar();
        setTitle("发起众筹");
        initViews();
        selectPace(1);
    }
    private void initViews(){

        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        imageView4 = (ImageView) findViewById(R.id.imageView4);

        bt_next = (Button) findViewById(R.id.bt_next);

        bt_next.setOnClickListener(this);
        bt_next.setClickable(false);
        bt_next.setSelected(false);
    }

    private void selectPace(int index){
        switch (index){
            case 1:
                imageView1.setSelected(true);
                imageView2.setSelected(false);
                imageView3.setSelected(false);
                imageView4.setSelected(false);
                bt_next.setClickable(false);
                bt_next.setSelected(false);
                FragmentChooseType.setOnNextListener(this);
                switchFragment(FragmentChooseType.class, null);
                break;
            case 2:
                imageView1.setSelected(false);
                imageView2.setSelected(true);
                imageView3.setSelected(false);
                imageView4.setSelected(false);
                bt_next.setClickable(false);
                bt_next.setSelected(false);
                FragmentProgramDetail.setOnNextListener(this);
                switchFragment(FragmentProgramDetail.class, null);
                break;
            case 3:
                imageView1.setSelected(false);
                imageView2.setSelected(false);
                imageView3.setSelected(true);
                imageView4.setSelected(false);
                bt_next.setClickable(false);
                bt_next.setSelected(false);
                FragmentRewardWay.setOnNextListener(this);
                switchFragment(FragmentRewardWay.class, null);
                break;
            case 4:
                imageView1.setSelected(false);
                imageView2.setSelected(false);
                imageView3.setSelected(false);
                imageView4.setSelected(true);
                bt_next.setClickable(false);
                bt_next.setSelected(false);
                FragmentPublish.setOnNextListener(this);
                switchFragment(FragmentPublish.class, null);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_next:
                if (nextIndex == 4){
                    Toast.makeText(this,"提交成功",Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }else {
                    next();
                }
                break;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void next(){
        if (nextIndex <4 ){
            nextIndex++;
            selectPace(nextIndex);
            if (nextIndex == 4){
                bt_next.setText("提交");
            }else {
                bt_next.setText("下一步");
            }
        }

    }

    /**
     *切换Fragment
     */
    public void switchFragment(Class<? extends BaseFragment> cls, Bundle bundle) {
        String className = cls.getSimpleName();
        curFragmentTag = className;
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag(className);
        if (fragment == null) {
            try {
                fragment = (Fragment) cls.newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }

            if (fragment == null) {
                return;
            }
            if (null != bundle) {
                fragment.setArguments(bundle);
            }
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment, className)
                    .commitAllowingStateLoss();
        }

        List<Fragment> fragments = fm.getFragments();
        if (fragments != null) {
            Iterator<Fragment> it = fragments.iterator();
            while (it.hasNext()) {
                fragment = it.next();
                if (className.equals(fragment.getTag())) {
                    fm.beginTransaction().show(fragment)
                            .commitAllowingStateLoss();
                    fm.beginTransaction().attach(fragment)
                            .commitAllowingStateLoss();
                } else {
                    fm.beginTransaction().hide(fragment)
                            .commitAllowingStateLoss();
                    fm.beginTransaction().detach(fragment)
                            .commitAllowingStateLoss();
                }
            }
        }
    }

    @Override
    public void onNext(boolean isNext) {
        if (isNext){
            bt_next.setClickable(true);
            bt_next.setSelected(true);
        }else {
            bt_next.setClickable(false);
            bt_next.setSelected(false);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment f = getSupportFragmentManager().findFragmentByTag(curFragmentTag);
        f.onActivityResult(requestCode, resultCode, data);
    }
}
