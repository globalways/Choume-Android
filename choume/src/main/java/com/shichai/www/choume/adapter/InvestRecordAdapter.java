package com.shichai.www.choume.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalways.choume.R;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProjectInvest;
import com.shichai.www.choume.tools.CMTool;
import com.shichai.www.choume.tools.Tool;


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
        CfProjectInvest invest = invests[position];
        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_member,null);
            holder.tvCfUserName = (TextView) convertView.findViewById(R.id.tvCfUserName);
            holder.tvInvestTime = (TextView) convertView.findViewById(R.id.tvInvestTime);
            holder.tvInvestDesc = (TextView) convertView.findViewById(R.id.tvInvestDesc);
            holder.ivUserAvatar = (ImageView) convertView.findViewById(R.id.ivUserAvatar);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        //CMTool.loadUserName(invests[position].hongId, holder.tvCfUserName);
        holder.tvInvestTime.setText(Tool.formatDateTime(invest.investTime * 1000));
        holder.tvCfUserName.setText(invest.investorNick + "小明 "+ CMTool.getCfProjectInvestStatus(invest.status));
        CMTool.loadAvatar(invests[position].investorAvatar, context, holder.ivUserAvatar);

        String desc = "支持";
        switch (invests[position].rewardSupportType) {
            case OutsouringCrowdfunding.MONEY_CFPST: desc += invest.coinPay+"筹币";break;
            case OutsouringCrowdfunding.PEOPLE_CFPST: desc += "人员"+invest.rewardAmount+"名";break;
            case OutsouringCrowdfunding.GOODS_CFPST: desc += invest.rewardCount+"件物品";;break;
            case OutsouringCrowdfunding.EQUITY_CFPST: desc = "股权";break;
            case OutsouringCrowdfunding.INVALID_CFPST: desc = "未知支持"; break;
        }
        holder.tvInvestDesc.setText(desc);


        return convertView;
    }

    class ViewHolder{
        TextView tvCfUserName, tvInvestTime, tvInvestDesc;
        ImageView ivUserAvatar;
    }
}
