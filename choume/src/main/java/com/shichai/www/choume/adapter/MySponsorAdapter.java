package com.shichai.www.choume.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfUser;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProject;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProjectInvest;
import com.globalways.choume.R;
import com.shichai.www.choume.activity.chou.ChouDetailActivity;
import com.shichai.www.choume.application.MyApplication;
import com.shichai.www.choume.tools.CMTool;
import com.shichai.www.choume.tools.PicassoImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HeJianjun on 2015/12/8.
 */
public class MySponsorAdapter extends BaseAdapter {

    public static final int NORMAL = 1;
    public static final int STAR   = 2;
    public static final int CONFIG = 3;

    private OnConfigListener onConfigListener;

    private static final int INIT_PAGE = 1;
    private int next_page = INIT_PAGE;
    private int type = NORMAL;
    private LayoutInflater inflater;
    private Context context;
    private List<CfProject> cfProjects;
    private List<CfProjectInvest> invests;

    private PicassoImageLoader imageLoader;

    public MySponsorAdapter(Context context) {
        this.context = context;
        imageLoader = new PicassoImageLoader(context);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public MySponsorAdapter(Context context, int type) {
        imageLoader = new PicassoImageLoader(context);
        this.context = context;
        this.type = type;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (cfProjects != null) {
            return cfProjects.size();
        }
        if (invests != null) {
            return invests.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (cfProjects != null) {
            return cfProjects.get(position);
        }
        if (invests != null) {
            return invests.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_my_sponsor,null);
            holder.progressBar           = (ProgressBar) convertView.findViewById(R.id.progressBar);
            holder.main                  = convertView.findViewById(R.id.main);
            holder.ibControl             = (ImageButton) convertView.findViewById(R.id.ibControl);
            holder.tvCfProjectName       = (TextView) convertView.findViewById(R.id.tv_my_sponsor_name);
            holder.tvProgress            = (TextView) convertView.findViewById(R.id.tvProgress);
            holder.ivProjectPic          = (ImageView) convertView.findViewById(R.id.ivProjectPic);
            holder.ivProjectCfuserAvatar = (ImageView) convertView.findViewById(R.id.ivProjectCfuserAvatar);
            holder.tvProjectStatus       = (TextView) convertView.findViewById(R.id.tvProjectStatus);
            switch (type) {
                case NORMAL:
                    holder.ibControl.setVisibility(View.GONE);
                    break;
                case STAR:
                    holder.ibControl.setImageDrawable(context.getResources().getDrawable(R.drawable.seletor_collect));
                    break;
                case CONFIG:
                    holder.ibControl.setImageDrawable(context.getResources().getDrawable(R.mipmap.ico_my_account_option));
                    //自己的项目隐藏头像显示项目状态
                    holder.ivProjectCfuserAvatar.setVisibility(View.GONE);
                    holder.tvProjectStatus.setVisibility(View.VISIBLE);
                    break;
                default:
                    holder.ibControl.setVisibility(View.GONE);
                    break;
            }
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.ibControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == STAR) {
                    boolean willCollect = true;
                    willCollect = !isCollectedByCurrentUser(cfProjects.get(position));
                    onConfigListener.onCollect(cfProjects.get(position).id, willCollect);
                } else if (type == CONFIG) {
                    onConfigListener.onConfig(cfProjects.get(position).id, position);
                }
            }
        });
        //set datas
        if (cfProjects != null) {
            holder.main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChouDetailActivity.class);
                    intent.putExtra(ChouDetailActivity.PROJECT_ID, cfProjects.get(position).id);
                    context.startActivity(intent);
                }
            });


            holder.progressBar.setProgress(CMTool.generateProjectProgress(cfProjects.get(position)));
            CMTool.loadProjectUserAvatar(cfProjects.get(position).hongId, context, holder.ivProjectCfuserAvatar);
            holder.tvCfProjectName.setText(cfProjects.get(position).title);
            holder.tvProgress.setText(cfProjects.get(position).desc);
            holder.tvProjectStatus.setText(CMTool.getProjectStatus(cfProjects.get(position).status));
            //没有项目图片就隐藏ImageView
            if (cfProjects.get(position).pics == null || cfProjects.get(position).pics.length == 0) {
                holder.ivProjectPic.setVisibility(View.GONE);
            } else {
                imageLoader.loadUrlImageToView(cfProjects.get(position).pics[0].url, 400, 400, R.mipmap.guangyuan_1, R.mipmap.guangyuan_1, holder.ivProjectPic);
            }
        } else if (invests != null) {
            final CfProjectInvest invest = invests.get(position);
            holder.main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChouDetailActivity.class);
                    intent.putExtra(ChouDetailActivity.PROJECT_ID, invest.cfProjectId);
                    context.startActivity(intent);
                }
            });
            holder.progressBar.setVisibility(View.GONE);
            imageLoader.loadUrlImageToView(invest.projectPic, 400, 400, R.mipmap.guangyuan_1, R.mipmap.guangyuan_1, holder.ivProjectPic);
            holder.tvProgress.setText(getSupportInfo(invest));
        }
        return convertView;
    }

    class ViewHolder{
        View main;
        ProgressBar progressBar;
        ImageButton ibControl;
        TextView tvCfProjectName, tvProgress, tvProjectStatus;
        ImageView ivProjectPic, ivProjectCfuserAvatar;
    }

    /**
     * @param isInit 是否是初始
     * @param list 数据
     */
    public void setData(boolean isInit, List<CfProject> list) {
        if(isInit){
            setCfProjects(list);
        }else{
            this.cfProjects.addAll(list);
        }
        next_page ++;
        notifyDataSetChanged();

    }

    public int getNext_page(boolean isReload) {
        if(isReload)
            this.next_page = INIT_PAGE;
        return next_page;
    }

    public List<CfProject> getCfProjects() {
        return cfProjects;
    }

    public void setCfProjects(List<CfProject> cfProjects) {
        this.cfProjects = cfProjects;
    }

    public void setInvests(List<CfProjectInvest> invests) {
        this.invests = invests;
        notifyDataSetChanged();
    }

    private boolean isCollectedByCurrentUser(CfProject cfProject) {
        for (CfUser currentUser : cfProject.collectedUsers) {
            if (currentUser.hongId == MyApplication.getCfUser().hongId){
                return true;
            }
        }
        return false;
    }


    public void setOnConfigListener(OnConfigListener onConfigListener) {
        this.onConfigListener = onConfigListener;
    }


    public interface OnConfigListener{
         void onConfig(long projectId, int position);
         void onCollect(long projectId, boolean willCollet);
    }

    public interface OnCollectListener{
         void onCollect(int position,boolean isCollect);
    }


    private String getSupportInfo(CfProjectInvest invest) {
        String abbr = "";
        switch (invest.rewardSupportType) {
            case OutsouringCrowdfunding.MONEY_CFPST:
                abbr = "支持了 "+invest.rewardAmount*invest.rewardCount+" 筹币";
                break;
            case OutsouringCrowdfunding.PEOPLE_CFPST:
                abbr = "支持了人员 "+invest.rewardAmount*invest.rewardCount+"名";
                break;
            case OutsouringCrowdfunding.GOODS_CFPST:
                abbr = "支持了物品 "+invest.rewardAmount*invest.rewardCount+" 件";
                break;
            case OutsouringCrowdfunding.EQUITY_CFPST:
                abbr = "支持了股权 "+invest.rewardAmount*invest.rewardCount+"‰";
                break;
            case OutsouringCrowdfunding.INVALID_CFPST:
                abbr = "未知";
                break;
        }
        return abbr;
    }
}
