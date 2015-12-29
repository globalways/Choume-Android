package com.shichai.www.choume.activity.chou;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.globalways.choume.R;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.adapter.SupportWayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HeJianjun on 2015/12/28.
 */
public class ChouSupportWayActivity extends BaseActivity {
    private ListView listView;
    private SupportWayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chou_support_way);
        initActionBar();
        setTitle("支持方式");
        initViews();
    }

    private void initViews(){
        listView = (ListView) findViewById(R.id.listView);
        adapter = new SupportWayAdapter(this);
        listView.setAdapter(adapter);
        List<String> strings = new ArrayList<>();
        for (int i=0;i<5;i++){
            strings.add("XXX");
        }
        adapter.addDatas(strings);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
