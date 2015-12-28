package com.shichai.www.choume.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.outsouring.crowdfunding.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by HeJianjun on 2015/12/28.
 */
public class ImageAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<String> photoPaths = new ArrayList<String>();

    public ImageAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    public void setDatas(ArrayList<String> photoPaths){
        this.photoPaths.clear();
        this.photoPaths.addAll(photoPaths);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return photoPaths.size();
    }

    @Override
    public Object getItem(int position) {
        return photoPaths.get(position);
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
            convertView = inflater.inflate(R.layout.item_image,null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Uri uri = Uri.fromFile(new File(photoPaths.get(position)));
        Glide.with(context)
                .load(uri)
                .centerCrop()
                .thumbnail(0.1f)
                .placeholder(me.iwf.photopicker.R.drawable.ic_photo_black_48dp)
                .error(me.iwf.photopicker.R.drawable.ic_broken_image_black_48dp)
                .into(holder.imageView);

        return convertView;
    }
    class ViewHolder{
        ImageView imageView;
    }
}
