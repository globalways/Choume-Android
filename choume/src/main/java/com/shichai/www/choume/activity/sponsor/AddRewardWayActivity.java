package com.shichai.www.choume.activity.sponsor;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.TextView;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProjectReward;
import com.globalways.choume.R;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.activity.sponsor.fragment.FragmentRewardWay;
import com.shichai.www.choume.tools.Tool;
import com.shichai.www.choume.tools.UITools;

/**
 * Created by HeJianjun on 2015/12/28.
 */
public class AddRewardWayActivity extends BaseActivity implements View.OnClickListener{

    private EditText et_des, et_limited;
    private TextView tvSupportDesc;
    private CfProjectReward reward;
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reward_way);
        initActionBar();
        setTitle("回报方式");
        setRightButton("保存");

        reward = new CfProjectReward();
        bt_right.setOnClickListener(this);
        et_des = (EditText) findViewById(R.id.et_des);
        et_limited = (EditText) findViewById(R.id.et_limited);
        tvSupportDesc = (TextView) findViewById(R.id.tvSupportDesc);
        tvSupportDesc.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_right:
                // add to list
                if (Tool.isEmpty(et_des.getText().toString().trim())){
                    UITools.toastMsg(this, "请输入描述信息");
                    return;
                }
                if (Tool.isEmpty(et_limited.getText().toString().trim())){
                    UITools.toastMsg(this, "请输入份数限制");
                    return;
                }
                reward.desc = et_des.getText().toString().trim();
                reward.limitedCount =  Long.parseLong(et_limited.getText().toString().trim());
                FragmentRewardWay.fragmentRewardWay.getRewardArrayList().add(reward);
                finish();
                break;
            case R.id.tvSupportDesc:
                Intent intent = new Intent(this, AddRewardTypeActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            reward.supportType = data.getIntExtra(AddRewardTypeActivity.SUPPORT_TYPE, -1);
            reward.amount = data.getIntExtra(AddRewardTypeActivity.SUPPORT_AMOUNT, 0);

            String desc = "";
            switch (reward.supportType) {
                case OutsouringCrowdfunding.MONEY_CFPST: desc = reward.amount+"筹币";break;
                case OutsouringCrowdfunding.PEOPLE_CFPST: desc = "人员"+reward.amount+"名";break;
                case OutsouringCrowdfunding.GOODS_CFPST: desc = reward.amount+"件物品";;break;
                case OutsouringCrowdfunding.EQUITY_CFPST: desc = "股权？";break;
                default: desc = "未知";break;
            }
            tvSupportDesc.setText(desc);
        }
    }

}
