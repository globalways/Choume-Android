package com.shichai.www.choume.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.globalways.choume.R;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProjectInvest;
import com.shichai.www.choume.tools.CMTool;

import java.util.ArrayList;
import java.util.List;


/**
 * 投资记录（参与项目）
 * Created by HeJianjun on 2015/12/29.
 */
public class InvestRecordAdapter extends BaseAdapter{
    private Context context;
    private LayoutInflater inflater;
    private CfProjectInvest[] invests;

    public CfProjectInvest[] getInvests() {
        return invests;
    }

    public InvestRecordAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setDatas(CfProjectInvest[] invests){
        this.invests = invests;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (invests != null) {
            return invests.length;
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (invests != null) {
            return invests[position];
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
        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_member,null);
            holder.textView = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(invests[position].hongId+"  支付筹币:"+invests[position].coinPay +" "+CMTool.getCfProjectInvestStatus(invests[position].status));
        return convertView;
    }

    class ViewHolder{
        TextView textView;
    }
}
