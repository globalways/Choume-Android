package com.shichai.www.choume.activity.mine.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.globalways.choume.R;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.adapter.ImageAdapter;
import com.shichai.www.choume.application.MyApplication;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.manager.CfUserManager;
import com.shichai.www.choume.network.manager.ImageUpLoadManager;
import com.shichai.www.choume.tools.LocalDataConfig;
import com.shichai.www.choume.tools.Tool;
import com.shichai.www.choume.tools.UITools;
import com.shichai.www.choume.view.GridViewForScrollView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

public class CertApplyActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private GridViewForScrollView gridView;
    private ImageAdapter adapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private static final int REQUEST_CODE_CERT = 2;
    private TextView ibToSelectImage;
    private RadioGroup rgApplyType;
    private EditText etRealName;
    private OutsouringCrowdfunding.CfUserCertPic[] pics;
    private int certType = -100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cert_apply);
        initActionBar();
        setTitle("提交认证");
        setRightButton("保存");
        bt_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImagesAndNewCert();
            }
        });
        ibToSelectImage = (TextView) findViewById(R.id.ibToSelectImage);
        ibToSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSelectImage();
            }
        });

        gridView = (GridViewForScrollView) findViewById(R.id.gridView);
        adapter = new ImageAdapter(this);
        gridView.setAdapter(adapter);
        rgApplyType = (RadioGroup) findViewById(R.id.rgApplyType);
        rgApplyType.setOnCheckedChangeListener(this);
        etRealName = (EditText) findViewById(R.id.etRealName);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<String> photos = null;
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CERT) {
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();
            if (photos != null && photos.size() > 0) {
                selectedPhotos.addAll(photos);
                adapter.setDatas(selectedPhotos);
            }
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

    private void toSelectImage(){
        PhotoPickerIntent intent = new PhotoPickerIntent(this);
        intent.setPhotoCount(1);
        intent.setShowCamera(true);
        intent.setShowGif(false);
        startActivityForResult(intent, REQUEST_CODE_CERT);
    }

    private void saveImagesAndNewCert() {

        if (selectedPhotos.size() == 0) {
            UITools.toastMsg(this, "请选择认证材料");
            return;
        }

        if (certType > OutsouringCrowdfunding.AGENCY_CFUCT || certType < OutsouringCrowdfunding.INVALID_CFUCT){
            UITools.toastMsg(this, "请选择要认证的类型");
            return;
        }
        if (Tool.isEmpty(etRealName.getText().toString())){
            UITools.toastMsg(this, "请输入认证姓名");
            return;
        }
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        String[] photosPath = new String[selectedPhotos.size()];
        selectedPhotos.toArray(photosPath);
        new ImageUpLoadManager().upLoadImage(photosPath, LocalDataConfig.getToken(this), new ManagerCallBack<List<String>>() {
            @Override
            public void success(List<String> result) {
                pics = new OutsouringCrowdfunding.CfUserCertPic[result.size()];
                for (int i = 0; i < result.size(); i++) {
                    OutsouringCrowdfunding.CfUserCertPic pic = new OutsouringCrowdfunding.CfUserCertPic();
                    pic.url = result.get(i);
                    pics[i] = pic;
                }
                //当上传图片完了，提交申请
                newCert();
            }

            @Override
            public void warning(int code, String msg) {
                UITools.warning(CertApplyActivity.this, "上传图片失败", msg);
                dialog.dismiss();
            }

            @Override
            public void error(Exception e) {
                UITools.toastServerError(CertApplyActivity.this);
                dialog.dismiss();
            }

            @Override
            public void progress(int progress) {
                //进度
            }
        });
    }

    private void newCert(){
        OutsouringCrowdfunding.UserCertApplyParam param = new OutsouringCrowdfunding.UserCertApplyParam();
        OutsouringCrowdfunding.CfUserCertApply apply = new OutsouringCrowdfunding.CfUserCertApply();
        //需要提供这几个参数
        apply.type = certType;
        apply.comment = "";
        apply.name = etRealName.getText().toString();
        apply.school  = "西南石油大学";
        apply.pics = null;
        param.apply = apply;
        param.token = LocalDataConfig.getToken(this);
        CfUserManager.getInstance().userCertApply(param, new ManagerCallBack<OutsouringCrowdfunding.UserCertApplyResp>() {
            @Override
            public void success(OutsouringCrowdfunding.UserCertApplyResp result) {
                //提交申请完成
                MyApplication.getCfUser().certification = result.apply;
                dialog.dismiss();
                CertApplyActivity.this.finish();
            }

            @Override
            public void warning(int code, String msg) {
                UITools.warning(CertApplyActivity.this, "提交认证失败", msg);
                dialog.dismiss();
            }

            @Override
            public void error(Exception e) {
                UITools.toastServerError(CertApplyActivity.this);
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbCertAgency: certType = OutsouringCrowdfunding.AGENCY_CFUCT;break;
            case R.id.rbCertStudent: certType = OutsouringCrowdfunding.STUDENT_CFUCT;break;
        }
    }
}
