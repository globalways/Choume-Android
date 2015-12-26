package com.shichai.www.choume.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfUser;
import com.outsouring.crowdfunding.R;
import com.shichai.www.choume.activity.mine.profile.CertApplyActivity;
import com.shichai.www.choume.application.MyApplication;
import com.shichai.www.choume.network.protoenum.UserSex;

/**
 * Created by HeJianjun on 2015/12/22.
 */
public class IndividualActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout rlToCert;
    private TextView tvNick, tvTel, tvSex, tvCert, tvAddr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual);
        initActionBar();
        setTitle("个人设置");
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
        rlToCert = (RelativeLayout) findViewById(R.id.rlToCert);
        rlToCert.setOnClickListener(this);
        tvNick = (TextView) findViewById(R.id.tvNick);
        tvTel = (TextView) findViewById(R.id.tvTel);
        tvSex = (TextView) findViewById(R.id.tvSex);
        tvAddr = (TextView) findViewById(R.id.tvAddr);
        tvCert = (TextView) findViewById(R.id.tvCertType);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rlToCert:
                startActivity(new Intent(IndividualActivity.this, CertApplyActivity.class));
                break;
            default:break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserInfo();
    }

    private void loadUserInfo(){
        CfUser cfUser = MyApplication.getCfUser();
        tvNick.setText(cfUser.user.nick);
        tvTel.setText(cfUser.user.tel);
        tvSex.setText(UserSex.codeOf(cfUser.user.sex).getDesc());
        tvCert.setText(cfUser.certification == null?"暂无认证": cfUser.certification.name);
        tvAddr.setText(cfUser.user.addrs.length == 0?"暂无地址": cfUser.user.addrs[0].name);
    }
}
