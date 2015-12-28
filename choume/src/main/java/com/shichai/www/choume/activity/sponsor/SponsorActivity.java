package com.shichai.www.choume.activity.sponsor;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.outsouring.crowdfunding.R;
import com.shichai.www.choume.activity.BaseActivity;

import java.io.ByteArrayOutputStream;

/**
 * Created by HeJianjun on 2015/12/24.
 */
public class SponsorActivity extends BaseActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener{

    private static final int CAPTURE_CODE = 2;

    private View layout_choose_type,layout_program_detail,layout_reward_way,layout_publish;

    private ImageView imageView1,imageView2,imageView3,imageView4;

    private View tv_upload_image;

    private RadioGroup rg_type;

    private Button bt_next;

    private int type = 0;

    private int nextIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor);
        initActionBar();
        setTitle("发起众筹");
        initViews();
        selectPace(1);
    }
    private void initViews(){
        layout_choose_type = findViewById(R.id.layout_choose_type);
        layout_program_detail = findViewById(R.id.layout_program_detail);
        layout_reward_way = findViewById(R.id.layout_reward_way);
        layout_publish = findViewById(R.id.layout_publish);

        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        imageView4 = (ImageView) findViewById(R.id.imageView4);

        bt_next = (Button) findViewById(R.id.bt_next);

        rg_type = (RadioGroup) findViewById(R.id.rg_type);

        tv_upload_image = findViewById(R.id.tv_upload_image);

        tv_upload_image.setOnClickListener(this);
        rg_type.setOnCheckedChangeListener(this);
        bt_next.setOnClickListener(this);
        bt_next.setClickable(false);
        bt_next.setSelected(false);
    }

    private void selectPace(int index){
        switch (index){
            case 1:
                layout_choose_type.setVisibility(View.VISIBLE);
                layout_program_detail.setVisibility(View.GONE);
                layout_reward_way.setVisibility(View.GONE);
                layout_publish.setVisibility(View.GONE);
                imageView1.setSelected(true);
                imageView2.setSelected(false);
                imageView3.setSelected(false);
                imageView4.setSelected(false);
                break;
            case 2:
                layout_choose_type.setVisibility(View.GONE);
                layout_program_detail.setVisibility(View.VISIBLE);
                layout_reward_way.setVisibility(View.GONE);
                layout_publish.setVisibility(View.GONE);
                imageView1.setSelected(false);
                imageView2.setSelected(true);
                imageView3.setSelected(false);
                imageView4.setSelected(false);
                break;
            case 3:
                layout_choose_type.setVisibility(View.GONE);
                layout_program_detail.setVisibility(View.GONE);
                layout_reward_way.setVisibility(View.VISIBLE);
                layout_publish.setVisibility(View.GONE);
                imageView1.setSelected(false);
                imageView2.setSelected(false);
                imageView3.setSelected(true);
                imageView4.setSelected(false);
                break;
            case 4:
                layout_choose_type.setVisibility(View.GONE);
                layout_program_detail.setVisibility(View.GONE);
                layout_reward_way.setVisibility(View.GONE);
                layout_publish.setVisibility(View.VISIBLE);
                imageView1.setSelected(false);
                imageView2.setSelected(false);
                imageView3.setSelected(false);
                imageView4.setSelected(true);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_next:
                if (nextIndex == 4){
                    Toast.makeText(this,"提交成功",Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }else {
                    next();
                }
                break;
            case R.id.tv_upload_image:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, CAPTURE_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_CODE){
            Uri uri = data.getData();
//            img.setImageURI(uri);//设置图片
            ContentResolver cr = this.getContentResolver();
            Cursor c = cr.query(uri, null, null, null, null);
            c.moveToFirst();
            //这是获取的图片保存在sdcard中的位置
            String srcPath = c.getString(c.getColumnIndex("_data"));
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
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rb_fun:
                type = 1;
                bt_next.setClickable(true);
                bt_next.setSelected(true);
                break;
            case R.id.rb_get_love:
                type = 3;
                bt_next.setClickable(true);
                bt_next.setSelected(true);
                break;
            case R.id.rb_get_money:
                type = 2;
                bt_next.setClickable(true);
                bt_next.setSelected(true);
                break;
            case R.id.rb_product:
                type = 4;
                bt_next.setClickable(true);
                bt_next.setSelected(true);
                break;
            case R.id.rb_program:
                type = 4;
                bt_next.setClickable(true);
                bt_next.setSelected(true);
                break;
        }
    }

    private void next(){
        if (nextIndex <4 ){
            nextIndex++;
            selectPace(nextIndex);
            if (nextIndex == 4){
                bt_next.setText("提交");
            }else {
                bt_next.setText("下一步");
            }
        }


    }

}
