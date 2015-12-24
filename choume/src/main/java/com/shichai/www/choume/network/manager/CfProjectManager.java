package com.shichai.www.choume.network.manager;

import com.globalways.choume.proto.nano.OutsouringCrowdfunding.*;
import com.globalways.proto.nano.Common;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.task.cfproject.*;

/**
 * Created by wyp on 15/12/23.
 */
public class CfProjectManager {
    private  CfProjectManager manager;
    private CfProjectManager(){}

    public CfProjectManager getInstance(){
        if (manager == null){
            manager = new CfProjectManager();
        }
        return manager;
    }

    /**
     * 发起一个项目
     * @param param
     * @param callBack
     */
    public void raiseCfProject(final RaiseCfProjectParam param, ManagerCallBack<RaiseCfProjectResp> callBack) {
        new RaiseCfProjectTask().setCallBack(callBack).setTaskParam(param).execute();
    }

    /**
     * 查询不同种类的项目(首页分类项目列表)
     * @param param
     * @param callBack
     */
    public void findCfProjects(final FindCfProjectsParam param, ManagerCallBack<FindCfProjectsResp> callBack) {
        new FindCfProjectsTask().setCallBack(callBack).setTaskParam(param).execute();
    }

    /**
     * 查询一个项目详情
     * @param param
     * @param callBack
     */
    public void getCfProject(final GetCfProjectParam param, ManagerCallBack<GetCfProjectResp> callBack) {
        new GetCfProjectTask().setCallBack(callBack).setTaskParam(param).execute();
    }

    /**
     * 结束项目
     * @param param
     * @param callBack
     */
    public void closeCfProject(final CloseCfProjectParam param, ManagerCallBack<CloseCfProjectResp> callBack) {
        new CloseCfProjectTask().setCallBack(callBack).setTaskParam(param).execute();
    }

    /**
     * 更新项目基本信息
     * @param param
     * @param callBack
     */
    public void UpdateCfProject(final UpdateCfProjectParam param, ManagerCallBack<UpdateCfProjectResp> callBack) {
        new UpdateCfProjectTask().setCallBack(callBack).setTaskParam(param).execute();
    }

    /**
     * 新增项目投资
     * @param param
     * @param callBack
     */
    public void newCfProjectInvest(final NewCfProjectInvestParam param, ManagerCallBack<NewCfProjectInvestResp> callBack) {
        new NewCfProjectInvestTask().setCallBack(callBack).setTaskParam(param).execute();
    }

    /**
     * 接受项目投资
     * @param param
     * @param callBack
     */
    public void passCfProjectInvest(final PassCfProjectInvestParam param, ManagerCallBack<Common.Response> callBack) {
        new PassCfProjectInvestTask().setCallBack(callBack).setTaskParam(param).execute();
    }

    /**
     * 拒绝项目投资
     * @param param
     * @param callBack
     */
    public void rejectCfProjectInvest(final RejectCfProjectInvestParam param, ManagerCallBack<Common.Response> callBack) {
        new RejectCfProjectInvestTask().setCallBack(callBack).setTaskParam(param).execute();
    }

}
