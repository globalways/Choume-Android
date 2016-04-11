package com.shichai.www.choume.activity.sponsor.fragment;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProject;
import com.globalways.choume.R;
import com.shichai.www.choume.adapter.ImageAdapter;
import com.shichai.www.choume.tools.Tool;
import com.shichai.www.choume.view.GridViewForScrollView;
import com.shichai.www.choume.view.dateareapicker.PickerDialog;
import com.shichai.www.choume.view.dateareapicker.PickerManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

/**
 * 发起项目-详细信息
 */
public class FragmentProgramDetail extends BaseFragment implements View.OnClickListener,TextWatcher, RadioGroup.OnCheckedChangeListener {
    public static OnNextListener onNextListener;
    private static final int REQUEST_CODE = 3;

    private EditText et_title,et_des,et_money,et_person_count,et_product,et_product_count, et_requiredProjectAmount, et_requiredProjectEquity;
    private TextView tv_upload_image,tv_end_time, tvLabelMoney,tvLabelPeople, tvLabelProduct, tvLabelEquity;
    private RadioGroup rgMajorType;
    private RadioButton rbMoney, rbPeoPle, rbGoods, rbEquity;
    //主要完成指标类型
    private int majorType = -100;
    private long deadline = 0;

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
        tvLabelMoney = (TextView) rootView.findViewById(R.id.tvLabelMoney);

        et_person_count = (EditText) rootView.findViewById(R.id.et_person_count);
        tvLabelPeople = (TextView) rootView.findViewById(R.id.tvLabelPeople);

        et_product = (EditText) rootView.findViewById(R.id.et_product);
        et_product_count = (EditText) rootView.findViewById(R.id.et_product_count);
        tvLabelProduct = (TextView) rootView.findViewById(R.id.tvLabelProduct);

        et_requiredProjectAmount = (EditText) rootView.findViewById(R.id.et_requiredProjectAmount);
        et_requiredProjectEquity = (EditText) rootView.findViewById(R.id.et_requiredProjectEquity);
        tvLabelEquity = (TextView) rootView.findViewById(R.id.tvLabelEquity);

        gridView = (GridViewForScrollView) rootView.findViewById(R.id.gridView);
        adapter = new ImageAdapter(getActivity());
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                onNextListener.onNext(checkFields());
            }
        });
        gridView.setAdapter(adapter);

        et_title.addTextChangedListener(this);
        et_des.addTextChangedListener(this);
        et_money.addTextChangedListener(this);
        et_person_count.addTextChangedListener(this);
        et_product.addTextChangedListener(this);
        et_product_count.addTextChangedListener(this);
        et_requiredProjectAmount.addTextChangedListener(this);
        et_requiredProjectEquity.addTextChangedListener(this);

        tv_upload_image = (TextView) rootView.findViewById(R.id.tv_upload_image);
        tv_end_time = (TextView) rootView.findViewById(R.id.tv_end_time);
        rgMajorType = (RadioGroup) rootView.findViewById(R.id.rgMajorType);
        rgMajorType.setOnCheckedChangeListener(this);

        rbMoney = (RadioButton) rootView.findViewById(R.id.rbMoney);
        rbPeoPle = (RadioButton) rootView.findViewById(R.id.rbPeoPle);
        rbGoods = (RadioButton) rootView.findViewById(R.id.rbGoods);
        rbEquity = (RadioButton) rootView.findViewById(R.id.rbEquity);

        tv_upload_image.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
        filterViews();
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
                        tv_end_time.setText(datePicker.getCurrentDate());
                        deadline = Tool.getDateLong(datePicker.getCurrentDate());
                        onNextListener.onNext(checkFields());
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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}
    @Override
    public void afterTextChanged(Editable s) {
        onNextListener.onNext(checkFields());
    }

    //检查字段是否符合要求
    private boolean checkFields(){
        String titleStr = et_title.getText().toString().trim();
        String descStr = et_des.getText().toString().trim();
        String moneyStr =  et_money.getText().toString().trim();
        String peopleStr = et_person_count.getText().toString().trim();
        String goodStr = et_product_count.getText().toString().trim();
        String goodName = et_product.getText().toString().trim();
        String equityAmountStr  = et_requiredProjectAmount.getText().toString().trim();
        String equitStr = et_requiredProjectEquity.getText().toString().trim();


        if (Tool.isEmpty(titleStr)){
            //UITools.toastMsg(getContext(),"请填写标题");
            return false;
        }

        if (Tool.isEmpty(descStr)) {
            //UITools.toastMsg(getContext(),"请填写描述");
            return false;
        }

        //至少选择一张图片
        if (selectedPhotos.size() < 1) {
            return false;
        }

        if (Tool.isEmpty(moneyStr) && Tool.isEmpty(peopleStr) && Tool.isEmpty(goodStr) && Tool.isEmpty(equitStr)) {
            //UITools.toastMsg(getContext(),"筹资／召集人员／筹集物品/融资 至少填一种");
            return false;
        }

        if (!(Tool.isEmpty(goodName) == Tool.isEmpty(goodStr))){
            return false;
        }

        //融资额跟募股数必须同时存在或没有
        if (!(Tool.isEmpty(equityAmountStr) == Tool.isEmpty(equitStr))){
            return false;
        }
        //主要完成指标类型不是指定类型
        if (majorType > 4 || majorType < 0) {
            return false;
        }
        if (deadline <= Tool.getCurrentDateTime()){
            return false;
        }

        return true;
    }


    @Override
    public void commitData(CfProject cfProject) {

        String titleStr = et_title.getText().toString().trim();
        String descStr = et_des.getText().toString().trim();
        String moneyStr =  et_money.getText().toString().trim();
        String peopleStr = et_person_count.getText().toString().trim();
        String goodStr = et_product_count.getText().toString().trim();
        String goodName = et_product.getText().toString().trim();
        String equityAmountStr  = et_requiredProjectAmount.getText().toString().trim();
        String equityStr = et_requiredProjectEquity.getText().toString().trim();

//        if (Tool.isEmpty(titleStr)){
//            UITools.toastMsg(getContext(),"请填写标题");
//            return;
//        }
//
//        if (Tool.isEmpty(descStr)) {
//            UITools.toastMsg(getContext(),"请填写描述");
//            return;
//        }
//
//        if (Tool.isEmpty(moneyStr) && Tool.isEmpty(peopleStr) && Tool.isEmpty(goodStr)) {
//            UITools.toastMsg(getContext(),"筹资／召集人员／筹集物品 至少填一种");
//            return;
//        }
//
        if (!Tool.isEmpty(goodName) && !Tool.isEmpty(goodStr)){
//            UITools.toastMsg(getContext(),"您填了物品数量但没有填写物品名称");
            cfProject.requiredGoodsAmount = Integer.parseInt(goodStr);
            cfProject.requiredGoodsName = goodName;
        }

        if (!Tool.isEmpty(equityStr) && !Tool.isEmpty(equityAmountStr)){
            cfProject.requiredProjectAmount = Tool.yuanToFen(equityAmountStr);
            cfProject.requiredProjectEquity = Integer.parseInt(equityStr);
        }


        try {
            cfProject.requiredMoneyAmount = Tool.yuanToFen(moneyStr,0);
            cfProject.requiredPeopleAmount = Integer.parseInt(peopleStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        cfProject.title = titleStr;
        cfProject.desc  = descStr;
        cfProject.majarType = majorType;
        cfProject.fundTime = Tool.getCurrentDateTime();
        cfProject.deadline = deadline;
        getSponsorActivity().selectedPhotos = selectedPhotos;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbMoney:
                majorType = OutsouringCrowdfunding.MONEY_CFPST;
                break;
            case R.id.rbPeoPle:
                majorType = OutsouringCrowdfunding.PEOPLE_CFPST;
                break;
            case R.id.rbGoods:
                majorType = OutsouringCrowdfunding.GOODS_CFPST;
                break;
            case R.id.rbEquity:
                majorType = OutsouringCrowdfunding.EQUITY_CFPST;
                break;
        }
        onNextListener.onNext(checkFields());
    }

    /**
     * 根据不同的项目类型有选择性的显示组建
     */
    private void filterViews() {
        CfProject  cfProject = getSponsorActivity().getCfProject();
        switch (cfProject.category) {
            case OutsouringCrowdfunding.HAPPY_CFC:
                //隐藏物品
                tvLabelProduct.setVisibility(View.GONE);
                et_product.setVisibility(View.GONE);
                et_product_count.setVisibility(View.GONE);
                rbGoods.setVisibility(View.GONE);

                //隐藏股权合伙
                tvLabelEquity.setVisibility(View.GONE);
                et_requiredProjectAmount.setVisibility(View.GONE);
                et_requiredProjectEquity.setVisibility(View.GONE);
                rbEquity.setVisibility(View.GONE);

                break;
            case OutsouringCrowdfunding.MONEY_CFC:
                //隐藏人员
                tvLabelPeople.setVisibility(View.GONE);
                et_person_count.setVisibility(View.GONE);
                rbPeoPle.setVisibility(View.GONE);

                //隐藏物品
                tvLabelProduct.setVisibility(View.GONE);
                et_product.setVisibility(View.GONE);
                et_product_count.setVisibility(View.GONE);
                rbGoods.setVisibility(View.GONE);

                //隐藏股权合伙
                tvLabelEquity.setVisibility(View.GONE);
                et_requiredProjectAmount.setVisibility(View.GONE);
                et_requiredProjectEquity.setVisibility(View.GONE);
                rbEquity.setVisibility(View.GONE);

                break;
            case OutsouringCrowdfunding.LOVE_CFC:
                //隐藏股权合伙
                tvLabelEquity.setVisibility(View.GONE);
                et_requiredProjectAmount.setVisibility(View.GONE);
                et_requiredProjectEquity.setVisibility(View.GONE);
                rbEquity.setVisibility(View.GONE);
                break;
            case OutsouringCrowdfunding.PROJECT_CFC:
                break;
            case OutsouringCrowdfunding.PRODUCT_CFC:
                break;
        }
    }
}
