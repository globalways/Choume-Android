package com.shichai.www.choume.activity.sponsor.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProjectReward;
import com.outsouring.crowdfunding.R;
import com.shichai.www.choume.activity.sponsor.AddRewardWayActivity;

import java.util.ArrayList;

/**
 * 回报方式
 * Created by HeJianjun on 2015/12/28.
 */
public class FragmentRewardWay extends BaseFragment {
    private static final int REQUEST_CODE = 1;
    public static OnNextListener onNextListener;
    private TextView tv_add_reward_way;
    private ListView listView;
    private RewardAdapter adapter;
    private ArrayList<CfProjectReward> rewardArrayList = new ArrayList<CfProjectReward>();
    public static FragmentRewardWay fragmentRewardWay;

    public ArrayList<CfProjectReward> getRewardArrayList() {
        return rewardArrayList;
    }

    public static void setOnNextListener(OnNextListener mOnNextListener) {
        onNextListener = mOnNextListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentRewardWay = this;

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

        listView = (ListView) rootView.findViewById(R.id.rewardListView);
        adapter = new RewardAdapter();
        listView.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (rewardArrayList.size() != 0) {
                onNextListener.onNext(true);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void commitData(OutsouringCrowdfunding.CfProject cfProject) {
        cfProject.rewards = rewardArrayList.toArray(cfProject.rewards);
    }

    private class RewardAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return rewardArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return rewardArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_reward_list_item, null);
            }
            TextView rewardAbbr = (TextView) convertView.findViewById(R.id.tvRewardAbbr);
            rewardAbbr.setText(rewardArrayList.get(position).desc);
            return rewardAbbr;
        }
    }
}
