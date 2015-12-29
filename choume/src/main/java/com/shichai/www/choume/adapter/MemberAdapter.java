package com.shichai.www.choume.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.globalways.choume.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by HeJianjun on 2015/12/29.
 */
public class MemberAdapter extends BaseAdapter{
    private Context context;
    private LayoutInflater inflater;
    private List<String> strings = new ArrayList<>();

    public MemberAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setDatas(List<String> strings){
        this.strings.addAll(strings);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public Object getItem(int position) {
        return strings.get(position);
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

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    class ViewHolder{
        TextView textView;
    }
}
