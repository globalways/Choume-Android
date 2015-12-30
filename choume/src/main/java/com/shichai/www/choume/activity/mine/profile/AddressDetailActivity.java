package com.shichai.www.choume.activity.mine.profile;

import android.os.Bundle;
import android.support.v4.content.res.TypedArrayUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.globalways.proto.nano.Common;
import com.globalways.user.nano.UserApp;
import com.globalways.user.nano.UserCommon;
import com.globalways.choume.R;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.application.MyApplication;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.manager.UserManager;
import com.shichai.www.choume.tools.LocalDataConfig;
import com.shichai.www.choume.tools.Tool;
import com.shichai.www.choume.tools.UITools;
import com.shichai.www.choume.view.dateareapicker.PickerDialog;
import com.shichai.www.choume.view.dateareapicker.PickerManager;

import java.util.Arrays;

public class AddressDetailActivity extends BaseActivity {

    private EditText etAddrName, etAddrContact, etAddrDetail;
    private Button btnDeleteAddr;
    private TextView tvAddrArea;
    private int addr_index = -1;
    private UserCommon.UserAddress tmpAddr;

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
        btnDeleteAddr = (Button) findViewById(R.id.btnDeleteAddr);

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
        addr_index = getIntent().getIntExtra(AddressManagerActivity.ADDR_INDEX, -1);
        if (addr_index != -1){
            UserCommon.UserAddress addr = MyApplication.getCfUser().user.addrs[addr_index];
            tmpAddr = new UserCommon.UserAddress();
            tmpAddr.name = addr.name;
            tmpAddr.contact = addr.contact;
            tmpAddr.area = addr.area;
            tmpAddr.detail = addr.detail;

            etAddrName.setText(addr.name);
            etAddrContact.setText(addr.contact);
            tvAddrArea.setText(addr.area);
            etAddrDetail.setText(addr.detail);
            btnDeleteAddr.setVisibility(View.VISIBLE);
            btnDeleteAddr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteAddr();
                }
            });
        }
    }

    private void toSaveAddr() {
        String name = etAddrName.getText().toString().trim();
        String contact = etAddrContact.getText().toString().trim();
        String detail = etAddrDetail.getText().toString().trim();
        String area = tvAddrArea.getText().toString().trim();

        if (Tool.isEmpty(name) || Tool.isEmpty(contact) || Tool.isEmpty(detail) || Tool.isEmpty(area)){
            UITools.toastMsg(this, "请补充完整地址");
            return;
        }

        if (addr_index != -1){
            //更新地址
            UserCommon.UserAddress address = MyApplication.getCfUser().user.addrs[addr_index];
            address.contact = contact;
            address.detail = detail;
            address.name = name;
            address.area = area;
            updateAddr(address);
            return;
        }

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

    private void updateAddr(final UserCommon.UserAddress address){
        UserApp.UpdateUserAddrsParam param = new UserApp.UpdateUserAddrsParam();
        param.addrs = new UserCommon.UserAddress[]{address};
        param.token = LocalDataConfig.getToken(this);
        UserManager.getInstance().updateUserAddrs(param, new ManagerCallBack<Common.Response>() {
            @Override
            public void success(Common.Response result) {
                AddressDetailActivity.this.finish();
            }

            @Override
            public void warning(int code, String msg) {
                UITools.warning(AddressDetailActivity.this,"更新地址失败",msg);
                address.name = tmpAddr.name;
                address.contact =tmpAddr.contact;
                address.area = tmpAddr.area;
                address.detail = tmpAddr.detail;
            }

            @Override
            public void error(Exception e) {
                UITools.toastServerError(AddressDetailActivity.this);
                address.name = tmpAddr.name;
                address.contact =tmpAddr.contact;
                address.area = tmpAddr.area;
                address.detail = tmpAddr.detail;
            }
        });
    }


    private void deleteAddr() {
        UserCommon.UserAddress address = MyApplication.getCfUser().user.addrs[addr_index];
        UserApp.DelUserAddrParam param = new UserApp.DelUserAddrParam();
        param.addrIds = new long[]{address.id};
        param.token = LocalDataConfig.getToken(this);
        UserManager.getInstance().delUserAddr(param, new ManagerCallBack<Common.Response>() {
            @Override
            public void success(Common.Response result) {
                //修改本地数据
                int orgLen = MyApplication.getCfUser().user.addrs.length;
                UserCommon.UserAddress[] newAddrs = new UserCommon.UserAddress[orgLen-1];
                for (int i = 0; i < orgLen; i++) {
                    if (i < addr_index){
                        newAddrs[i] = MyApplication.getCfUser().user.addrs[i];
                    }
                    if (i > addr_index){
                        newAddrs[i-1] = MyApplication.getCfUser().user.addrs[i];
                    }
                }
                MyApplication.getCfUser().user.addrs = newAddrs;
                AddressDetailActivity.this.finish();
            }

            @Override
            public void warning(int code, String msg) {
                UITools.warning(AddressDetailActivity.this, "删除地址失败", msg);
            }

            @Override
            public void error(Exception e) {
                UITools.toastServerError(AddressDetailActivity.this);
            }
        });
    }


}
