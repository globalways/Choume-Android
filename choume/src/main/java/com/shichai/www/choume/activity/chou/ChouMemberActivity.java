package com.shichai.www.choume.activity.chou;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.outsouring.crowdfunding.R;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.adapter.MemberAdapter;
import com.shichai.www.choume.tools.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HeJianjun on 2015/12/28.
 */
public class ChouMemberActivity extends BaseActivity implements View.OnClickListener{

    private SwipeMenuListView listView;
    private MemberAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chou_member);
        initActionBar();
        setTitle("参与者");
        initViews();
    }
    private void initViews(){
        listView = (SwipeMenuListView) findViewById(R.id.listView);
        adapter = new MemberAdapter(this);
        listView.setAdapter(adapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                  createMenu1(menu);
            }

            private void createMenu1(SwipeMenu menu) {
                SwipeMenuItem item1 = new SwipeMenuItem(
                        getApplicationContext());
                item1.setBackground(new ColorDrawable(Color.rgb(0xE5, 0x18,
                        0x5E)));
                item1.setWidth(Utils.dp2px(ChouMemberActivity.this, 90));
                item1.setTitle("不同意");
                item1.setTitleColor(getResources().getColor(R.color.white));
                menu.addMenuItem(item1);
                SwipeMenuItem item2 = new SwipeMenuItem(
                        getApplicationContext());
                item2.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                item2.setWidth(Utils.dp2px(ChouMemberActivity.this, 90));
                item2.setTitle("同意");
                item2.setTitleColor(getResources().getColor(R.color.white));
                menu.addMenuItem(item2);
            }

        };
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                return false;
            }
        });
        List<String> strings = new ArrayList<>();
        for (int i=0;i<10;i++){
            strings.add("XXXX");
        }
        adapter.setDatas(strings);
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
}
