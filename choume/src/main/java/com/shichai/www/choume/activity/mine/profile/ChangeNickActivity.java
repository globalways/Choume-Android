package com.shichai.www.choume.activity.mine.profile;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.globalways.proto.nano.Common;
import com.globalways.user.nano.UserApp;
import com.globalways.choume.R;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.application.MyApplication;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.manager.UserManager;
import com.shichai.www.choume.tools.LocalDataConfig;
import com.shichai.www.choume.tools.Tool;
import com.shichai.www.choume.tools.UITools;

public class ChangeNickActivity extends BaseActivity {

    private EditText etNick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_change_nick);
        initActionBar();
        setTitle("修改昵称");
        initViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        setRightButton("保存");
        etNick = (EditText) findViewById(R.id.etNick);
        etNick.setHint(MyApplication.getCfUser().user.nick);
        bt_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toUpdateNick();
            }
        });
    }

    private void toUpdateNick() {
        String newNick = etNick.getText().toString().trim();
        if (Tool.isEmpty(newNick))
            return;
        if (MyApplication.getCfUser().user.nick.equals(newNick)) {
            return;
        }
        final UserApp.ChangeUserNickParam param = new UserApp.ChangeUserNickParam();
        param.token = LocalDataConfig.getToken(this);
        param.nick = newNick;
        UserManager.getInstance().changeUserNick(param, new ManagerCallBack<Common.Response>() {
            @Override
            public void success(Common.Response result) {
                MyApplication.getCfUser().user.nick = param.nick;
                ChangeNickActivity.this.finish();
            }

            @Override
            public void warning(int code, String msg) {
                UITools.warning(ChangeNickActivity.this,"修改昵称失败", msg, code);
            }

            @Override
            public void error(Exception e) {
                UITools.toastServerError(ChangeNickActivity.this);
            }
        });
    }
}
