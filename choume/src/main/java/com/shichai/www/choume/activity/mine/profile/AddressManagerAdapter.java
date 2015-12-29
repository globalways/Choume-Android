package com.shichai.www.choume.activity.mine.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.globalways.user.nano.UserCommon;
import com.globalways.choume.R;

import java.util.List;

/**
 * Created by wyp on 15/12/27.
 */
public class AddressManagerAdapter extends BaseAdapter {
    private Context context;
    private List<UserCommon.UserAddress> list;
    public AddressManagerAdapter(Context context){
        this.context = context;
    }

    public void setDatas(List<UserCommon.UserAddress> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (this.list != null)
            return list.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (this.list != null)
            return list.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_address_manager_item, null);
            viewHolder.tvAddres = (TextView) convertView.findViewById(R.id.tvAddres);
            viewHolder.tvAddresContact = (TextView) convertView.findViewById(R.id.tvAddresContact);
            viewHolder.tvAddresName = (TextView) convertView.findViewById(R.id.tvAddresName);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvAddresName.setText(list.get(position).name);
        viewHolder.tvAddresContact.setText(list.get(position).contact);
        viewHolder.tvAddres.setText(list.get(position).area+list.get(position).detail);
        return convertView;
    }



    private class ViewHolder{
        TextView tvAddresName, tvAddresContact, tvAddres;
    }
}
