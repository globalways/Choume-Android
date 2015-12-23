package com.shichai.www.choume.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;

import com.outsouring.crowdfunding.R;
import com.shichai.www.choume.activity.chou.ChouDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HeJianjun on 2015/12/8.
 */
public class MySponsorAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<String> strings;

    public MySponsorAdapter(Context context) {
        this.context = context;
        strings = new ArrayList<>();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addDatas(List<String> strings){
        if (strings != null && strings.size() > 0 ){
            this.strings.addAll(strings);
            notifyDataSetChanged();
        }
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
            convertView = inflater.inflate(R.layout.item_my_sponsor,null);
            holder.progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
            holder.main = convertView.findViewById(R.id.main);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.progressBar.setProgress((int)(50+Math.random()*(100)));
        holder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ChouDetailActivity.class));
            }
        });
        return convertView;
    }

    class ViewHolder{
        View main;
        ProgressBar progressBar;
    }

}
