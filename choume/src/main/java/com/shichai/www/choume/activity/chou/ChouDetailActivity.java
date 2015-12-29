package com.shichai.www.choume.activity.chou;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.GetCfProjectParam;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProject;
import com.outsouring.crowdfunding.R;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.adapter.MySponsorAdapter;
import com.shichai.www.choume.adapter.SupportAdapter;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.manager.CfProjectManager;
import com.shichai.www.choume.tools.UITools;
import com.shichai.www.choume.view.ListViewForScrollView;

import java.util.ArrayList;

/**
 * Created by HeJianjun on 2015/12/22.
 */
public class ChouDetailActivity extends BaseActivity implements View.OnClickListener{
    private ListViewForScrollView listView;
    private SupportAdapter adapter;
    private View headerView;
    private View tv_reply,tv_comment,tv_supporter;

    //项目信息
    private TextView tvProjectName, tvProjectDesc, tvProgressPercent;
    private ImageView ivProjectCfuserAvatar;
    private ProgressBar progressBar;



    private CfProject currentProject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chou);
        ActionBar();
        initViews();

        long projectId = getIntent().getLongExtra(MySponsorAdapter.PROJECT_ID, -1);
        if (projectId != -1) {
            loadProject(projectId);
        }else {
            UITools.toastMsg(this, "获取项目信息错误");
        }
    }

    private void ActionBar(){

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        ab.setHomeAsUpIndicator(R.mipmap.ico_back);
        ab.setDisplayHomeAsUpEnabled(true);

        View actionbarLayout = LayoutInflater.from(this).inflate(
                R.layout.actionbar_main_layout, null);
        ((TextView)actionbarLayout.findViewById(R.id.textView)).setTextColor(getResources().getColor(R.color.white));
        ((TextView)actionbarLayout.findViewById(R.id.textView)).setText("项目介绍");
        ab.setCustomView(actionbarLayout);
        ab.setDisplayShowCustomEnabled(true);

    }

    private void initViews(){
        listView = (ListViewForScrollView) findViewById(R.id.listView);
        adapter = new SupportAdapter(this);


        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        headerView = inflater.inflate(R.layout.layout_chou_header, null);

        listView.addHeaderView(headerView);

        tv_reply = headerView.findViewById(R.id.tv_reply);
        tv_comment = headerView.findViewById(R.id.tv_comment);
        tv_supporter = headerView.findViewById(R.id.tv_supporter);
        tv_reply.setOnClickListener(this);
        tv_comment.setOnClickListener(this);
        tv_supporter.setOnClickListener(this);

        tv_reply.performClick();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_reply:
                tv_reply.setSelected(true);
                tv_comment.setSelected(false);
                tv_supporter.setSelected(false);
                adapter.clearDatas();
                listView.setAdapter(adapter);
                ArrayList<String> strings = new ArrayList<>();
                for (int i=0; i<3 ;i++){
                    strings.add("XXSASD");
                }
                adapter.addDatas(strings);
                break;
            case R.id.tv_comment:
                tv_reply.setSelected(false);
                tv_comment.setSelected(true);
                tv_supporter.setSelected(false);
                adapter.clearDatas();
                listView.setAdapter(adapter);
                ArrayList<String> strings2 = new ArrayList<>();
                for (int i=0; i<8 ;i++){
                    strings2.add("XXSASD");
                }
                adapter.addDatas(strings2);
                break;
            case R.id.tv_supporter:
                tv_reply.setSelected(false);
                tv_comment.setSelected(false);
                tv_supporter.setSelected(true);
                adapter.clearDatas();
                listView.setAdapter(adapter);
                ArrayList<String> strings3 = new ArrayList<>();
                for (int i=0; i<5 ;i++){
                    strings3.add("XXSASD");
                }
                adapter.addDatas(strings3);
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

    private void loadProject(long projetId){
        GetCfProjectParam projectParam = new GetCfProjectParam();
        projectParam.projectId = projetId;
        CfProjectManager.getInstance().getCfProject(projectParam, new ManagerCallBack<OutsouringCrowdfunding.GetCfProjectResp>() {
            @Override
            public void success(OutsouringCrowdfunding.GetCfProjectResp result) {
                super.success(result);
            }

            @Override
            public void warning(int code, String msg) {
                super.warning(code, msg);
            }

            @Override
            public void error(Exception e) {
                super.error(e);
            }
        });
    }
}
