package com.shichai.www.choume.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.globalways.choume.R;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HeJianjun on 2015/12/22.
 */
public class MyMessageAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<CfMessage> cfMessages;

    public MyMessageAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addDatas(List<CfMessage> cfMessages){
        this.cfMessages = cfMessages;
        notifyDataSetChanged();
    }

    public void clearDatas(){
        cfMessages.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (cfMessages != null) {
            return cfMessages.size();
        }else return 0;
    }

    @Override
    public Object getItem(int position) {
        if (cfMessages != null) {
            return cfMessages.get(position);
        }else return null;
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
            convertView = inflater.inflate(R.layout.item_my_message,null);
            holder.tvMsgTitle = (TextView) convertView.findViewById(R.id.tvMsgTitle);
            holder.tvMsgContent = (TextView) convertView.findViewById(R.id.tvMsgContent);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvMsgTitle.setText(cfMessages.get(position).title);
        holder.tvMsgContent.setText(cfMessages.get(position).content);
        return convertView;
    }

    public List<CfMessage> getCfMessages() {
        return cfMessages;
    }

    class ViewHolder{
        TextView tvMsgTitle, tvMsgContent;
    }
}
