package com.shichai.www.choume.activity.chou;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.globalways.choume.R;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProject;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.GetCfProjectParam;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.GetCfProjectResp;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProjectInvest;
import com.globalways.proto.nano.Common;
import com.google.gson.Gson;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.activity.mine.MySponsorActivity;
import com.shichai.www.choume.adapter.InvestRecordAdapter;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.manager.CfProjectManager;
import com.shichai.www.choume.tools.CMTool;
import com.shichai.www.choume.tools.LocalDataConfig;
import com.shichai.www.choume.tools.UITools;
import com.shichai.www.choume.tools.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HeJianjun on 2015/12/28.
 */
public class ChouMemberActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    public static final String INVEST_DETAIL = "invest_detail";
    private long projectId;
    private CfProject cfProject;

    private SwipeMenuListView listView;
    private InvestRecordAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chou_member);
        initActionBar();
        projectId = getIntent().getLongExtra(MySponsorActivity.PROJECT_ID, 0);
        setTitle("项目支持者列表");
        initViews();
        getProject();
    }
    private void initViews(){
        listView = (SwipeMenuListView) findViewById(R.id.listView);
        adapter = new InvestRecordAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem itemNotPass = new SwipeMenuItem(getApplicationContext());
                itemNotPass.setBackground(new ColorDrawable(Color.parseColor("#8a929a")));
                itemNotPass.setWidth(Utils.dp2px(ChouMemberActivity.this, 90));
                itemNotPass.setTitle("拒绝");
                itemNotPass.setTitleColor(Color.WHITE);
                itemNotPass.setTitleSize(18);
                menu.addMenuItem(itemNotPass);

                SwipeMenuItem itemPass = new SwipeMenuItem(getApplicationContext());
                itemPass.setBackground(new ColorDrawable(Color.parseColor("#11a2ff")));
                itemPass.setWidth(Utils.dp2px(ChouMemberActivity.this, 90));
                itemPass.setTitle("通过");
                itemPass.setTitleColor(Color.WHITE);
                itemPass.setTitleSize(18);
                menu.addMenuItem(itemPass);
            }
        };
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                //UITools.toastMsg(ChouMemberActivity.this,menu.getMenuItem(index).getTitle())
                switch (index) {
                    case 0: rejectInvest(adapter.getInvests()[position]);
                        break;
                    case 1: passInvest(adapter.getInvests()[position]);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

    private void getProject() {
        dialog.show();
        GetCfProjectParam projectParam  = new GetCfProjectParam();
        projectParam.projectId = projectId;
        CfProjectManager.getInstance().getCfProject(projectParam, new ManagerCallBack<GetCfProjectResp>() {
            @Override
            public void success(GetCfProjectResp result) {
                cfProject = result.project;
                adapter.setDatas(cfProject.invests);
                dialog.dismiss();
            }

            @Override
            public void error(Exception e) {
                UITools.toastServerError(ChouMemberActivity.this);
                dialog.dismiss();
            }

            @Override
            public void warning(int code, String msg) {
                UITools.warning(ChouMemberActivity.this, "获取项目详细失败", msg);
                dialog.dismiss();

            }
        });
    }


    private void rejectInvest(final CfProjectInvest invest) {
        OutsouringCrowdfunding.RejectCfProjectInvestParam param = new OutsouringCrowdfunding.RejectCfProjectInvestParam();
        param.investId = invest.id;
        param.token = LocalDataConfig.getToken(this);
        CfProjectManager.getInstance().rejectCfProjectInvest(param, new ManagerCallBack<Common.Response>() {
            @Override
            public void success(Common.Response result) {
                UITools.toastMsg(ChouMemberActivity.this, "拒绝成功");
            }

            @Override
            public void warning(int code, String msg) {
                UITools.warning(ChouMemberActivity.this, "拒绝失败", msg);
            }

            @Override
            public void error(Exception e) {
                UITools.toastServerError(ChouMemberActivity.this);
            }
        });
    }



    private void passInvest(final CfProjectInvest invest) {
        OutsouringCrowdfunding.PassCfProjectInvestParam param = new OutsouringCrowdfunding.PassCfProjectInvestParam();
        param.investId = invest.id;
        param.token = LocalDataConfig.getToken(this);
        CfProjectManager.getInstance().passCfProjectInvest(param, new ManagerCallBack<Common.Response>() {
            @Override
            public void success(Common.Response result) {
                UITools.toastMsg(ChouMemberActivity.this, "通过投资成功");
            }

            @Override
            public void warning(int code, String msg) {
                UITools.warning(ChouMemberActivity.this,"通过投资失败",msg);
            }

            @Override
            public void error(Exception e) {
                UITools.toastServerError(ChouMemberActivity.this);
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(ChouMemberActivity.this, ChouMemberDetailActivity.class);
        Gson gson = new Gson();
        intent.putExtra(INVEST_DETAIL, gson.toJson((CfProjectInvest)adapter.getItem(position)));
        intent.putExtra(CMTool.PROJECT, gson.toJson(cfProject));
        startActivity(intent);
    }
}
