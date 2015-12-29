package com.shichai.www.choume.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.globalways.choume.R;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProjectReward;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HeJianjun on 2015/12/29.
 */
public class SupportWayAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private CfProjectReward[] rewards;

    public SupportWayAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addDatas(CfProjectReward[] rewards) {
        this.rewards = rewards;
        notifyDataSetChanged();
    }

    public void clearDatas() {
        this.rewards = null;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (rewards != null) {
            return rewards.length;
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (rewards != null) {
            return rewards[position];
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_support_way, null);
            holder.tvRewardDesc = (TextView) convertView.findViewById(R.id.tvRewardDesc);
            holder.tvRewardSupports = (TextView) convertView.findViewById(R.id.tvRewardSupports);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvRewardDesc.setText(rewards[position].desc);
        holder.tvRewardSupports.setText("支持数:"+rewards[position].alreadyCount);

        return convertView;
    }

    class ViewHolder {
        TextView tvRewardSupports, tvRewardDesc;
    }
}
