package com.shichai.www.choume.tools;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalways.choume.R;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProject;
import com.globalways.user.nano.UserCommon;
import com.shichai.www.choume.application.MyApplication;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.manager.CfUserManager;


/**
 * Created by wyp on 15/12/30.
 */
public class CMTool {
    public static final String PROJECT = "cfproject";
    public static final String WEBVIEW_TITLE = "webview_title";
    public static final String WEBVIEW_CONTENT_HTML = "webview_content_html";

    public static String getProjectStatus(int status) {
        switch (status) {
            case OutsouringCrowdfunding.INVALID_CFPS: return "不合法";
            case OutsouringCrowdfunding.AUDITING_CFPS: return "审核中";
            case OutsouringCrowdfunding.PUBLISHED_CFPS: return "筹集中";
            case OutsouringCrowdfunding.FINISH_CFPS: return "已筹满";
            case OutsouringCrowdfunding.FAILURE_CFPS: return "失败";
            case OutsouringCrowdfunding.COMPLETED_CFPS: return "已完成";
            default:return "未知";
        }
    }

    public static String getCfProjectInvestStatus(int status) {
        switch (status) {
            case OutsouringCrowdfunding.PENDING_CFPIS: return "等待";
            case OutsouringCrowdfunding.PAID_CFPIS: return "已支付";
            case OutsouringCrowdfunding.SUCCESS_CFPIS: return "成功";
            case OutsouringCrowdfunding.EXPIRED_CFPIS: return "过期";
            default: return "未知";
        }
    }

    /**
     * 根据回报方式类型(钱,人,物品,股份), 数量, 物品名名称组合出整体的回报方式描述.
     * @param rewardSupportType 回报方式类型
     * @param amount 数量
     * @param requiredGoodsName  物品名称(可选)
     * @return 回报方式描述
     */
    public static String getRewardAbbr(int rewardSupportType, long amount, String requiredGoodsName) {
        String abbr = "";
        switch (rewardSupportType) {
            case OutsouringCrowdfunding.MONEY_CFPST:
                abbr = amount+" 筹币";
                break;
            case OutsouringCrowdfunding.PEOPLE_CFPST:
                abbr = "人员"+amount+"名";
                break;
            case OutsouringCrowdfunding.GOODS_CFPST:
                if (Tool.isEmpty(requiredGoodsName)){
                    abbr = "物品"+amount+"件";
                }else abbr = "物品［"+requiredGoodsName+"］ "+amount+"件";
                break;
            case OutsouringCrowdfunding.EQUITY_CFPST:
                abbr = "入股"+amount;
                break;
            case OutsouringCrowdfunding.INVALID_CFPST:
                abbr = "未知";
                break;
        }
        return abbr;
    }


    /**
     * 计算项目进度
     * @param cfProject
     * @return
     */
    public static int generateProjectProgress(CfProject cfProject) {

        switch (cfProject.majarType) {
            case OutsouringCrowdfunding.MONEY_CFPST:
                return moneyProcess(cfProject);
            case OutsouringCrowdfunding.PEOPLE_CFPST:
                return peopleProcess(cfProject);
            case OutsouringCrowdfunding.GOODS_CFPST:
                return goodsProcess(cfProject);
        }
        return 0;
    }

    private static int moneyProcess(CfProject cfProject){
        return (int)(Tool.divAsDouble(String.valueOf(cfProject.alreadyMoneyAmount),String.valueOf(cfProject.requiredMoneyAmount),2)*100);
    }
    private static int peopleProcess(CfProject cfProject){
        return (int)(Tool.divAsDouble(String.valueOf(cfProject.alreadyPeopleAmount),String.valueOf(cfProject.requiredPeopleAmount),2)*100);
    }
    private static int goodsProcess(CfProject cfProject){
        return (int)(Tool.divAsDouble(String.valueOf(cfProject.alreadyGoodsAmount),String.valueOf(cfProject.requiredGoodsAmount),2)*100);
    }

    /**
     * 以默认设置加载头像
     * @param uri
     * @param context
     * @param view
     */
    public static void loadAvatar(String uri,Context context, ImageView view){
        PicassoImageLoader imageLoader = new PicassoImageLoader(context);
        imageLoader.loadUrlImageToView(uri, 200, 200, R.mipmap.user_default,R.mipmap.user_default,view);
    }

    /**
     * 项目发起者的头像
     * @param hongId 发起者hongId
     */
    public static void loadProjectUserAvatar(long hongId, final Context context, final ImageView view) {
        UserCommon.GetAppUserParam param = new UserCommon.GetAppUserParam();
        final PicassoImageLoader imageLoader = new PicassoImageLoader(context);
        param.hongId = hongId;
        CfUserManager.getInstance().getAppUser(param, new ManagerCallBack<OutsouringCrowdfunding.GetCFUserResp>() {
            @Override
            public void success(OutsouringCrowdfunding.GetCFUserResp result) {
                loadAvatar(result.cfUser.user.avatar,context,view);
            }
        });
    }

    /**
     * 加载指定用户的名称
     * @param hongId 发起者hongId
     */
    public static void loadUserName(long hongId,final TextView view) {
        UserCommon.GetAppUserParam param = new UserCommon.GetAppUserParam();
        param.hongId = hongId;
        CfUserManager.getInstance().getAppUser(param, new ManagerCallBack<OutsouringCrowdfunding.GetCFUserResp>() {
            @Override
            public void success(OutsouringCrowdfunding.GetCFUserResp result) {

                view.setText(result.cfUser.user.nick);
            }
        });
    }

    /**
     * 指定项目是否被当前用户收藏
     * @param cfProject
     * @return true 收藏了，false 没被收藏
     */
    public static boolean isCollectedByCurrentUser(CfProject cfProject) {
        if (MyApplication.getCfUser() == null){
            return false;
        }
        for (CfProject project : MyApplication.getCfUser().collectedProjects) {
            if (project.id == cfProject.id) {
                return true;
            }
        }
        return false;
    }

}
