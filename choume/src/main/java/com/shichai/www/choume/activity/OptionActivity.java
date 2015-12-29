package com.shichai.www.choume.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.globalways.choume.R;

/**
 * Created by HeJianjun on 2015/12/22.
 */
public class OptionActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        initActionBar();
        setTitle("设置");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_normal_question:
                Toast.makeText(this,"暂无",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_feedback:
                Toast.makeText(this,"暂无",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_join_us:
                Toast.makeText(this,"暂无",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_update:
                Toast.makeText(this,"暂无",Toast.LENGTH_SHORT).show();
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
}
