package com.shichai.www.choume.activity.sponsor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.globalways.choume.R;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.tools.Tool;

/**
 * Created by HeJianjun on 2015/12/28.
 */
public class AddRewardTypeActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    public static final String SUPPORT_TYPE = "supportType";
    public static final String SUPPORT_AMOUNT = "support_amount";

    private RadioGroup rg_type;
    private RadioButton rb_get_money, rb_get_people, rb_get_goods;
    private EditText etAmount;
    private int supportType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rewward_type);
        initActionBar();
        setTitle("回报类型");
        setRightButton("保存");
        bt_right.setOnClickListener(this);
        rg_type = (RadioGroup) findViewById(R.id.rg_type);
        rg_type.setOnCheckedChangeListener(this);
        etAmount = (EditText) findViewById(R.id.etAmount);

        rb_get_money = (RadioButton) findViewById(R.id.rb_get_money);
        rb_get_people = (RadioButton) findViewById(R.id.rb_get_people);
        rb_get_goods = (RadioButton) findViewById(R.id.rb_get_goods);

        filterViews();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent();
            intent.putExtra(SUPPORT_TYPE,-1);
            intent.putExtra(SUPPORT_AMOUNT,0);
            setResult(Activity.RESULT_OK, intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_right:

                String amountStr = etAmount.getText().toString().trim();
                if (Tool.isEmpty(amountStr))
                    return;
                int amount = Integer.parseInt(amountStr);
                Intent intent = new Intent();
                intent.putExtra(SUPPORT_TYPE,supportType);
                intent.putExtra(SUPPORT_AMOUNT,amount);
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_get_money: supportType = OutsouringCrowdfunding.MONEY_CFPST;break;
            case R.id.rb_get_people: supportType = OutsouringCrowdfunding.PEOPLE_CFPST;break;
            case R.id.rb_get_goods: supportType = OutsouringCrowdfunding.GOODS_CFPST;break;
            //case 4: supportType = OutsouringCrowdfunding.EQUITY_CFPST;break;
            default: supportType = OutsouringCrowdfunding.INVALID_CFPST;break;
        }
    }

    /**
     * 根据不同的项目类型有选择性的显示组建
     */
    private void filterViews() {
        OutsouringCrowdfunding.CfProject cfProject = SponsorActivity.cfProject;
        switch (cfProject.category) {
            case OutsouringCrowdfunding.HAPPY_CFC:
                rb_get_goods.setVisibility(View.GONE);
                break;
            case OutsouringCrowdfunding.MONEY_CFC:
                rb_get_people.setVisibility(View.GONE);
                rb_get_goods.setVisibility(View.GONE);
                break;
            case OutsouringCrowdfunding.LOVE_CFC:
                break;
            case OutsouringCrowdfunding.PROJECT_CFC:
                break;
            case OutsouringCrowdfunding.PRODUCT_CFC:
                break;
        }
    }
}
