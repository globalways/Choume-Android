package com.shichai.www.choume.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalways.choume.R;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfUser;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProjectReward;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProjectInvest;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProjectComment;
import com.shichai.www.choume.tools.CMTool;
import com.shichai.www.choume.tools.Tool;

import java.util.List;

/**
 * Created by HeJianjun on 2015/12/25.
 */
public class ProjectDetailAdapter extends BaseAdapter {

    public static final int TYPE_REWARD    = 1;
    public static final int TYPE_SUPPORTER = 2;
    public static final int TYPE_COMMENT   = 3;
    private int currentType = TYPE_REWARD;


    private Context context;
    private LayoutInflater inflater;
    private CfProjectReward[] rewards;
    private CfUser[] cfUsers;
    private CfProjectInvest[] invests;
    private InvestListener investListener;
    //项目评论
    private CfProjectComment cfProjectComment[];

    public ProjectDetailAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setInvestListener(InvestListener investListener) {
        this.investListener = investListener;
    }

    public void setDataReward(CfProjectReward[] rewards){
        currentType = TYPE_REWARD;
        this.rewards = rewards;
        notifyDataSetChanged();
    }

    public void setDataSupporter(CfProjectInvest[] invests) {
        this.currentType = TYPE_SUPPORTER;
        this.invests = invests;
        notifyDataSetChanged();
    }

    public void setDataComments(CfProjectComment[] comments) {
        this.cfProjectComment = comments;
        this.currentType = TYPE_COMMENT;
        notifyDataSetChanged();
    }

    public void clearDatas(){
        cfUsers = null;
        rewards = null;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        switch (currentType) {
            case TYPE_REWARD:
                if (rewards == null)
                    return 0;
                return rewards.length;
            case TYPE_SUPPORTER:
                if (invests == null)
                    return 0;
                return invests.length;
            case TYPE_COMMENT:
                if (cfProjectComment == null)
                    return 0;
                return cfProjectComment.length;
            default: return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        switch (currentType) {
            case TYPE_REWARD:
                if (rewards == null)
                    return null;
                return rewards[position];
            case TYPE_SUPPORTER:
                if (invests == null)
                    return null;
                return invests[position];
            case TYPE_COMMENT:
                if (cfProjectComment == null)
                    return null;
                return cfProjectComment[position];
            default: return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        switch (currentType) {
            case TYPE_REWARD:
                return getViewReward(convertView, rewards[position]);
            case TYPE_SUPPORTER:
                return getViewSupport(convertView, invests[position]);
            case TYPE_COMMENT:
                return getViewComment(convertView, cfProjectComment[position]);
            default:return null;
        }
    }


    private View getViewReward(View convertView, final CfProjectReward reward) {
        ViewHolderReward holder;
        if (convertView == null){
            holder = new ViewHolderReward();
            convertView = inflater.inflate(R.layout.item_support,null);
            holder.tvRewardDesc = (TextView) convertView.findViewById(R.id.tvRewardDesc);
            holder.tvRewardAbbr = (TextView) convertView.findViewById(R.id.tvRewardAbbr);
            holder.tvSupport = (TextView) convertView.findViewById(R.id.tvSupport);
            holder.tvSupport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //参与项目
                    investListener.onNewInvest(reward);
                }
            });

            convertView.setTag(holder);
        }else {
            holder = (ViewHolderReward) convertView.getTag();
        }

        holder.tvRewardDesc.setText(reward.desc);
        holder.tvRewardAbbr.setText(getRewardAbbr(reward));

        return convertView;
    }

    private View getViewComment(View convertView, final CfProjectComment comment) {
        ViewHolderComment holder;
        if (convertView == null){
            holder = new ViewHolderComment();
            convertView = inflater.inflate(R.layout.item_cfproject_comment,null);
            holder.ivCommentAvatar = (ImageView) convertView.findViewById(R.id.ivCommentAvatar);
            holder.tvCommentNick = (TextView) convertView.findViewById(R.id.tvCommentNick);
            holder.tvCommentTime = (TextView) convertView.findViewById(R.id.tvCommentTime);
            holder.tvReplyToLabel1 = (TextView) convertView.findViewById(R.id.tvReplyToLabel1);
            holder.tvReplyToLabel2 = (TextView) convertView.findViewById(R.id.tvReplyToLabel2);
            holder.tvReplyToNick = (TextView) convertView.findViewById(R.id.tvReplyToNick);
            holder.tvCommentContent = (TextView) convertView.findViewById(R.id.tvCommentContent);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolderComment) convertView.getTag();
        }

        holder.tvCommentNick.setText(comment.userNick);
        holder.tvCommentTime.setText(Tool.formatDateTime(comment.time * 1000));
        holder.tvCommentContent.setText(comment.content);
        CMTool.loadAvatar(comment.avatar, context, holder.ivCommentAvatar);

        if (comment.repliedUserId != 0) {
            holder.tvReplyToLabel1.setVisibility(View.VISIBLE);
            holder.tvReplyToLabel2.setVisibility(View.VISIBLE);
            holder.tvReplyToNick.setText(comment.repliedUserNick);
        }
        return convertView;
    }

    private View getViewSupport(View convertView, final CfProjectInvest invest) {
        ViewHolderSupport holder;
        if (convertView == null){
            holder = new ViewHolderSupport();
            convertView = inflater.inflate(R.layout.item_member,null);
            holder.tvUserNick = (TextView) convertView.findViewById(R.id.tvCfUserName);
            holder.ivUserAvatar = (ImageView) convertView.findViewById(R.id.ivUserAvatar);
            holder.tvInvestDesc = (TextView) convertView.findViewById(R.id.tvInvestDesc);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolderSupport) convertView.getTag();
        }

        holder.tvUserNick.setText(invest.investorNick);
        CMTool.loadAvatar(invest.investorAvatar, context, holder.ivUserAvatar);
        holder.tvInvestDesc.setText(CMTool.getRewardAbbr(invest.rewardSupportType, invest.rewardAmount * invest.rewardCount, ""));
        return convertView;
    }






    class ViewHolderReward {
        TextView tvRewardDesc, tvRewardAbbr, tvSupport;
    }
    class ViewHolderComment {
        ImageView ivCommentAvatar;
        TextView tvCommentNick, tvCommentTime, tvReplyToNick, tvCommentContent, tvReplyToLabel1, tvReplyToLabel2;
    }
    class ViewHolderSupport{
        TextView tvUserNick, tvInvestDesc;
        ImageView ivUserAvatar;
    }


    private String getRewardAbbr(CfProjectReward reward) {
        String abbr = "";
        switch (reward.supportType) {
            case OutsouringCrowdfunding.MONEY_CFPST:
                abbr = "需要支持"+reward.amount+"筹币";
                break;
            case OutsouringCrowdfunding.PEOPLE_CFPST:
                abbr = "需要"+reward.amount+"人参与项目";
                break;
            case OutsouringCrowdfunding.GOODS_CFPST:
                abbr = "需要支持物品"+reward.amount+"件";
                break;
            case OutsouringCrowdfunding.EQUITY_CFPST:
                abbr = "需要入股"+reward.amount;
                break;
            case OutsouringCrowdfunding.INVALID_CFPST:
                abbr = "未知";
                break;
        }
        return abbr;
    }

    /**
     * 参与项目（投资）
     */
    public interface InvestListener{
        public void onNewInvest(CfProjectReward reward);
    }
}
