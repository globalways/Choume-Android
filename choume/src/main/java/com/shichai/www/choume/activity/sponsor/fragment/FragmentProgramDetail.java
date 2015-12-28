package com.shichai.www.choume.activity.sponsor.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.outsouring.crowdfunding.R;
import com.shichai.www.choume.adapter.ImageAdapter;
import com.shichai.www.choume.view.GridViewForScrollView;
import com.shichai.www.choume.view.dateareapicker.PickerDialog;
import com.shichai.www.choume.view.dateareapicker.PickerManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

/**
 * Created by HeJianjun on 2015/12/28.
 */
public class FragmentProgramDetail extends BaseFragment implements View.OnClickListener,TextWatcher{
    public static OnNextListener onNextListener;
    private static final int REQUEST_CODE = 3;

    private EditText et_title,et_des,et_money,et_person_count,et_product,et_product_count;
    private TextView tv_upload_image,tv_end_time;

    private GridViewForScrollView gridView;
    private ImageAdapter adapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();

    public static void setOnNextListener(OnNextListener mOnNextListener) {
        onNextListener = mOnNextListener;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.layout_program_detail;
    }

    @Override
    protected void initViews(View rootView, Bundle savedInstanceState) {
        et_title = (EditText) rootView.findViewById(R.id.et_title);
        et_des = (EditText) rootView.findViewById(R.id.et_des);
        et_money = (EditText) rootView.findViewById(R.id.et_money);
        et_person_count = (EditText) rootView.findViewById(R.id.et_person_count);
        et_product = (EditText) rootView.findViewById(R.id.et_product);
        et_product_count = (EditText) rootView.findViewById(R.id.et_product_count);

        gridView = (GridViewForScrollView) rootView.findViewById(R.id.gridView);
        adapter = new ImageAdapter(getActivity());
        gridView.setAdapter(adapter);

        et_title.addTextChangedListener(this);
        et_des.addTextChangedListener(this);

        tv_upload_image = (TextView) rootView.findViewById(R.id.tv_upload_image);
        tv_end_time = (TextView) rootView.findViewById(R.id.tv_end_time);

        tv_upload_image.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_upload_image:
                PhotoPickerIntent intent = new PhotoPickerIntent(getActivity());
                intent.setPhotoCount(10);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.tv_end_time:
                Date now = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                final PickerManager.DatePicker datePicker = new PickerManager(getActivity()).initDatePicker();
                final PickerDialog dialog = new PickerDialog(getActivity())
                        .builder()
                        .setTitle("选择日期")
                        .setView(datePicker.getDatePickerView(dateFormat.format(now)))
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                dialog.setPositiveButton("保存", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tv_end_time.setText("截至日期：" + datePicker.getCurrentDate());
                    }
                });
                dialog.show();

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<String> photos = null;
        if (resultCode == getActivity().RESULT_OK && requestCode == REQUEST_CODE) {
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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!TextUtils.isEmpty(et_title.getText().toString()) && !TextUtils.isEmpty(et_des.getText().toString())){
            onNextListener.onNext(true);
        }else {
            onNextListener.onNext(false);
        }
    }
}
