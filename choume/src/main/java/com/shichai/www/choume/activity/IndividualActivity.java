package com.shichai.www.choume.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfUser;
import com.globalways.proto.nano.Common;
import com.globalways.user.nano.UserApp;
import com.outsouring.crowdfunding.R;
import com.shichai.www.choume.activity.mine.profile.CertApplyActivity;
import com.shichai.www.choume.application.MyApplication;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.manager.ImageUpLoadManager;
import com.shichai.www.choume.network.manager.UserManager;
import com.shichai.www.choume.network.protoenum.UserSex;
import com.shichai.www.choume.tools.LocalDataConfig;
import com.shichai.www.choume.tools.PicassoImageLoader;
import com.shichai.www.choume.tools.Tool;
import com.shichai.www.choume.tools.UITools;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HeJianjun on 2015/12/22.
 */
public class IndividualActivity extends BaseActivity implements View.OnClickListener {

    private final int CAPTURE_CODE = 1;
    private RelativeLayout rlToCert;
    private TextView tvNick, tvTel, tvSex, tvCert, tvAddr;
    private ImageView ivAvatar;
    private PicassoImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual);
        initActionBar();
        setTitle("个人设置");
        initViews();
        imageLoader = new PicassoImageLoader(this);
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
        rlToCert = (RelativeLayout) findViewById(R.id.rlToCert);
        rlToCert.setOnClickListener(this);
        tvNick = (TextView) findViewById(R.id.tvNick);
        tvTel = (TextView) findViewById(R.id.tvTel);
        tvSex = (TextView) findViewById(R.id.tvSex);
        tvAddr = (TextView) findViewById(R.id.tvAddr);
        tvCert = (TextView) findViewById(R.id.tvCertType);
        ivAvatar = (ImageView) findViewById(R.id.ivAvatar);
        ivAvatar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rlToCert:
                startActivity(new Intent(IndividualActivity.this, CertApplyActivity.class));
                break;
            case R.id.ivAvatar:
                PhotoPickerIntent intent = new PhotoPickerIntent(this);
                intent.setPhotoCount(1);
                intent.setShowCamera(true);
                intent.setShowGif(false);
                startActivityForResult(intent, CAPTURE_CODE);
                break;
            default:break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserInfo();
    }

    private void loadUserInfo(){
        CfUser cfUser = MyApplication.getCfUser();
        tvNick.setText(cfUser.user.nick);
        tvTel.setText(cfUser.user.tel);
        tvSex.setText(UserSex.codeOf(cfUser.user.sex).getDesc());
        tvCert.setText(cfUser.certification == null?"暂无认证": cfUser.certification.name);
        tvAddr.setText(cfUser.user.addrs.length == 0?"暂无地址": cfUser.user.addrs[0].name);
        imageLoader.loadUrlImageToView(cfUser.user.avatar,200,200,R.mipmap.test_head,R.mipmap.test_head,ivAvatar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == CAPTURE_CODE) {
            if (data != null) {
                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                Bitmap bitmap = null;
                try {
                    bitmap = Tool.getCorrectlyOrientedImage(this, Tool.getImageContentUri(this, new File(photos.get(0))));
                    ivAvatar.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                 final String userToken = LocalDataConfig.getToken(IndividualActivity.this);
                 new ImageUpLoadManager().upLoadImage(photos.toArray(new String[photos.size()]), userToken, new ManagerCallBack<List<String>>() {
                     @Override
                     public void success(List<String> result) {
                         final UserApp.ChangeUserAvatarParam p = new UserApp.ChangeUserAvatarParam();
                         p.avatar = result.get(0);
                         p.token = userToken;
                         UserManager.getInstance().changeUserAvatar(p, new ManagerCallBack<Common.Response>() {
                             @Override
                             public void success(Common.Response result) {
                                 MyApplication.getCfUser().user.avatar = p.avatar;
                                 imageLoader.loadUrlImageToView(p.avatar,200,200,R.mipmap.test_head,R.mipmap.test_head,ivAvatar);
                             }

                             @Override
                             public void warning(int code, String msg) {
                                 UITools.ToastMsg(IndividualActivity.this, msg);
                             }

                             @Override
                             public void error(Exception e) {
                                UITools.ToastServerError(IndividualActivity.this);
                             }
                         });
                     }

                     @Override
                     public void progress(int progress) {

                     }

                     @Override
                     public void error(Exception e) {
                         super.error(e);
                         UITools.ToastServerError(IndividualActivity.this);
                     }

                     @Override
                     public void warning(int code, String msg) {
                         super.warning(code, msg);
                         UITools.ToastMsg(IndividualActivity.this, msg);
                     }
                 });
            }
        }


    }
}
