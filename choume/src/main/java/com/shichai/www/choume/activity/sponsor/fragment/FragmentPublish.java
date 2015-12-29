package com.shichai.www.choume.activity.sponsor.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import android.widget.EditText;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.globalways.choume.R;
import com.shichai.www.choume.tools.Tool;

/**
 * Created by HeJianjun on 2015/12/28.
 */
public class FragmentPublish  extends BaseFragment implements TextWatcher {
    public static OnNextListener onNextListener;
    private EditText et_contact, et_contact_number;

    public static void setOnNextListener(OnNextListener mOnNextListener) {
        onNextListener = mOnNextListener;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_publish;
    }

    @Override
    protected void initViews(View rootView, Bundle savedInstanceState) {
        onNextListener.onNext(true);
        et_contact = (EditText) rootView.findViewById(R.id.et_contact);
        et_contact_number = (EditText) rootView.findViewById(R.id.et_contact_number);
        et_contact.addTextChangedListener(this);
        et_contact_number.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!Tool.isEmpty(et_contact.getText().toString()) && !Tool.isEmpty(et_contact_number.getText().toString())){
            onNextListener.onNext(true);
        }
    }

    @Override
    public void commitData(OutsouringCrowdfunding.CfProject cfProject) {
        cfProject.contact = et_contact.getText().toString();
        cfProject.tel = et_contact.getText().toString();
    }
}
