package com.shichai.www.choume.activity.mine.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.globalways.choume.R;
import com.shichai.www.choume.activity.BaseActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

public class CertApplyActivity extends BaseActivity {

    private static final int REQUEST_CODE_CERT = 2;
    private TextView ibToSelectImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cert_apply);
        initActionBar();
        setTitle("提交认证");
        ibToSelectImage = (TextView) findViewById(R.id.ibToSelectImage);
        ibToSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSelectImage();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toSelectImage(){
        PhotoPickerIntent intent = new PhotoPickerIntent(this);
        intent.setPhotoCount(1);
        intent.setShowCamera(true);
        intent.setShowGif(false);
        startActivityForResult(intent, REQUEST_CODE_CERT);
    }
}
