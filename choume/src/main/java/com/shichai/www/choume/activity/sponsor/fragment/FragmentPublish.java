package com.shichai.www.choume.activity.sponsor.fragment;

import android.os.Bundle;
import android.view.View;

import com.outsouring.crowdfunding.R;

/**
 * Created by HeJianjun on 2015/12/28.
 */
public class FragmentPublish  extends BaseFragment{
    public static OnNextListener onNextListener;

    public static void setOnNextListener(OnNextListener mOnNextListener) {
        onNextListener = mOnNextListener;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_publish;
    }

    @Override
    protected void initViews(View rootView, Bundle savedInstanceState) {

    }
}
