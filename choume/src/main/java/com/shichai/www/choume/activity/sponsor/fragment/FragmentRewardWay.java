package com.shichai.www.choume.activity.sponsor.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.outsouring.crowdfunding.R;
import com.shichai.www.choume.activity.sponsor.AddRewardWayActivity;

/**
 * 回报方式
 * Created by HeJianjun on 2015/12/28.
 */
public class FragmentRewardWay extends BaseFragment {
    private static final int REQUEST_CODE = 1;
    public static OnNextListener onNextListener;
    private TextView tv_add_reward_way;


    public static void setOnNextListener(OnNextListener mOnNextListener) {
        onNextListener = mOnNextListener;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_reward_way;
    }

    @Override
    protected void initViews(View rootView, Bundle savedInstanceState) {
        tv_add_reward_way = (TextView) rootView.findViewById(R.id.tv_add_reward_way);
        tv_add_reward_way.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  =new Intent(getActivity(), AddRewardWayActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE){

        }
    }
}
