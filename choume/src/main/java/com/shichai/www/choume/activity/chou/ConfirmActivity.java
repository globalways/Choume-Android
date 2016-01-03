package com.shichai.www.choume.activity.chou;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.globalways.choume.R;
import com.shichai.www.choume.activity.BaseActivity;

public class ConfirmActivity extends BaseActivity implements View.OnClickListener{

    private TextView tvNumber;
    private Button btnLess, btnAdd;

    private int num = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        initActionBar();
        setTitle("参与项目");
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
        tvNumber = (TextView) findViewById(R.id.tvNumber);
        btnLess = (Button) findViewById(R.id.btnLess);
        btnLess.setOnClickListener(this);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                num += 1;
                tvNumber.setText(String.valueOf(num));
                break;
            case R.id.btnLess:
                if (num > 1){
                    num -= 1;
                }
                tvNumber.setText(String.valueOf(num));
                break;
        }
    }
}
