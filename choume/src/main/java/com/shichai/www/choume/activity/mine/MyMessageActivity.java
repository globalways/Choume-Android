package com.shichai.www.choume.activity.mine;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.globalways.choume.R;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfMessage;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.GetRCCFUserTokenParam;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.GetRCCFUserTokenResp;
import com.google.gson.Gson;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.activity.chou.ChouDetailActivity;
import com.shichai.www.choume.adapter.MyMessageAdapter;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.rongcloud.RCMessage;
import com.shichai.www.choume.network.manager.CfUserManager;
import com.shichai.www.choume.tools.LocalDataConfig;
import com.shichai.www.choume.tools.UITools;
import com.shichai.www.choume.view.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

/**
 * Created by HeJianjun on 2015/12/22.
 */
public class MyMessageActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private PullToRefreshListView listView;
    private MyMessageAdapter adapter;
    private List<CfMessage> historyMessages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sponsor);
        initActionBar();
        setTitle("消息");
        initViews();

        //在启动APP的时候(WelcomeActivity)已经连接融云服务,如果在APP使用过程中断开连接服务(参见融云文档)应作其他处理
        RongIMClient.getInstance().getHistoryMessages(Conversation.ConversationType.SYSTEM, "88888888", "CF:message", -1, 10, new RongIMClient.ResultCallback<List<Message>>() {
            @Override
            public void onSuccess(List<Message> messages) {
                if (messages != null) {
                    historyMessages = new ArrayList<CfMessage>();
                    Gson gson = new Gson();
                    for (Message message : messages) {
                        CfMessage cfMessage = gson.fromJson(((RCMessage) message.getContent()).getContent(), CfMessage.class);
                        historyMessages.add(cfMessage);
                    }
                    adapter.addDatas(historyMessages);
                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                UITools.warning(MyMessageActivity.this, "获取消息失败", errorCode.getMessage());
            }
        });

    }

    private void initViews(){
        listView = (PullToRefreshListView) findViewById(R.id.listView);
        adapter = new MyMessageAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CfMessage cfMessage = adapter.getCfMessages().get(position - 1);
        Intent intent = new Intent(MyMessageActivity.this, ChouDetailActivity.class);
        switch (cfMessage.type) {
            case OutsouringCrowdfunding.SYS_CFMT: break;
            case OutsouringCrowdfunding.PROJECT_CFMT:
                intent.putExtra(ChouDetailActivity.PROJECT_ID, cfMessage.project.id);
                startActivity(intent);
                break;
            case OutsouringCrowdfunding.COMMENT_CFMT:
                intent.putExtra(ChouDetailActivity.PROJECT_ID, cfMessage.comment.projectId);
                startActivity(intent);
                break;
            default: break;
        }
    }
}
