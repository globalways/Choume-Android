package com.shichai.www.choume.activity.mine.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.globalways.user.nano.UserCommon;
import com.globalways.choume.R;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.application.MyApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddressManagerActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private AddressManagerAdapter adapter;
    private ListView lvAddrList;
    private List<UserCommon.UserAddress> addrList;
    public static final String ADDR_INFO = "addr_info";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_manager);
        initActionBar();
        setTitle("我的地址");
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

    @Override
    protected void onResume() {
        super.onResume();
        loadAddrs();
    }

    private void initViews() {
        bt_add.setVisibility(View.VISIBLE);
        bt_add.setOnClickListener(this);

        lvAddrList = (ListView) findViewById(R.id.lvAddrList);
        adapter = new AddressManagerAdapter(this);
        lvAddrList.setAdapter(adapter);
        lvAddrList.setOnItemClickListener(this);
    }

    private void loadAddrs() {
        addrList = new ArrayList<UserCommon.UserAddress>
               (Arrays.asList(MyApplication.getCfUser().user.addrs));
        adapter.setDatas(addrList);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_add) {
            startActivity(new Intent(this, AddressDetailActivity.class));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UserCommon.UserAddress address = addrList.get(position);
        String[] addrInfo = new String[]{address.name, address.contact, address.area, address.detail};
        Intent intent = new Intent(this, AddressDetailActivity.class);
        intent.putExtra(ADDR_INFO, addrInfo);
        startActivity(intent);
    }
}
