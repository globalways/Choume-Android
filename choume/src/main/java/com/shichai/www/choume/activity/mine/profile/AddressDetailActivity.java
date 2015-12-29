package com.shichai.www.choume.activity.mine.profile;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.globalways.user.nano.UserApp;
import com.globalways.user.nano.UserCommon;
import com.globalways.choume.R;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.application.MyApplication;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.manager.UserManager;
import com.shichai.www.choume.tools.LocalDataConfig;
import com.shichai.www.choume.tools.UITools;
import com.shichai.www.choume.view.dateareapicker.PickerDialog;
import com.shichai.www.choume.view.dateareapicker.PickerManager;

import java.util.Arrays;

public class AddressDetailActivity extends BaseActivity {

    private EditText etAddrName, etAddrContact, etAddrDetail;
    private TextView tvAddrArea;
    private String[] addrInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_detail);
        initActionBar();
        setTitle("地址详情");
        initViews();
        initDatas();
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
        bt_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSaveAddr();
            }
        });
        etAddrContact = (EditText) findViewById(R.id.etAddrContact);
        etAddrDetail = (EditText) findViewById(R.id.etAddrDetail);
        etAddrName = (EditText) findViewById(R.id.etAddrName);
        tvAddrArea = (TextView) findViewById(R.id.etAddrArea);

        final PickerManager.AreaPicker areaPicker = new PickerManager(this).initAreaPicker();
        tvAddrArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PickerDialog areaPickerDialog = new PickerDialog(AddressDetailActivity.this)
                        .builder()
                        .setTitle("选择地区")
                        .setView(areaPicker.getAreaView())
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                areaPickerDialog.setPositiveButton("保存", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tvAddrArea.setText(areaPicker.getAreaStr());
                    }
                });
                areaPickerDialog.show();
            }
        });
    }

    private void initDatas() {
        addrInfo = getIntent().getStringArrayExtra(AddressManagerActivity.ADDR_INFO);
        if (addrInfo != null) {
            etAddrName.setText(addrInfo[0]);
            etAddrContact.setText(addrInfo[1]);
            tvAddrArea.setText(addrInfo[2]);
            etAddrDetail.setText(addrInfo[3]);
        }
    }

    private void toSaveAddr() {
        String name = etAddrName.getText().toString().trim();
        String contact = etAddrContact.getText().toString().trim();
        String detail = etAddrDetail.getText().toString().trim();
        String area = "xxxxx";

        UserApp.NewUserAddrParam param = new UserApp.NewUserAddrParam();
        param.name = name;
        param.contact = contact;
        param.detail = detail;
        param.area = area;
        param.token = LocalDataConfig.getToken(this);
        UserManager.getInstance().newUserAddr(param, new ManagerCallBack<UserApp.NewUserAddrResp>() {
            @Override
            public void success(UserApp.NewUserAddrResp result) {
                UserCommon.UserAddress[] newArray;
                newArray = Arrays.copyOf(MyApplication.getCfUser().user.addrs,MyApplication.getCfUser().user.addrs.length+1);
                newArray[newArray.length - 1] = result.addr;
                MyApplication.getCfUser().user.addrs = newArray;
                AddressDetailActivity.this.finish();
            }

            @Override
            public void warning(int code, String msg) {
                UITools.toastMsg(AddressDetailActivity.this, msg);
            }

            @Override
            public void error(Exception e) {
                UITools.toastServerError(AddressDetailActivity.this);
            }
        });
    }


}
