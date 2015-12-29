package com.shichai.www.choume.activity.sponsor.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.outsouring.crowdfunding.R;
import com.shichai.www.choume.network.protoenum.CrowdFundingCategory;

/**
 * Created by HeJianjun on 2015/12/28.
 */
public class FragmentChooseType extends BaseFragment implements RadioGroup.OnCheckedChangeListener{

    private int type = 0;
    private RadioGroup rg_type;

    public static OnNextListener onNextListener;

    public static void setOnNextListener(OnNextListener mOnNextListener) {
        onNextListener = mOnNextListener;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_choose_type;
    }

    @Override
    protected void initViews(View rootView, Bundle savedInstanceState) {
        rg_type = (RadioGroup) rootView.findViewById(R.id.rg_type);
        rg_type.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rb_fun:
                type = OutsouringCrowdfunding.HAPPY_CFC;
                onNextListener.onNext(true);
                break;
            case R.id.rb_get_love:
                type = OutsouringCrowdfunding.LOVE_CFC;
                onNextListener.onNext(true);
                break;
            case R.id.rb_get_money:
                type = OutsouringCrowdfunding.MONEY_CFC;
                onNextListener.onNext(true);
                break;
            case R.id.rb_product:
                type = OutsouringCrowdfunding.PRODUCT_CFC;
                onNextListener.onNext(true);
                break;
            case R.id.rb_program:
                type = OutsouringCrowdfunding.PROJECT_CFC;
                onNextListener.onNext(true);
                break;
        }
    }

    @Override
    public void commitData(OutsouringCrowdfunding.CfProject cfProject) {
        super.commitData(cfProject);
        cfProject.category = type;
    }
}
