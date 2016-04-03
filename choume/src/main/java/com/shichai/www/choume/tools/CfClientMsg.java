package com.shichai.www.choume.tools;

import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfMessage;

/**
 * Created by wang on 16/4/3.
 * 封装CfMessage<br/>
 * 更方便地获取所需字段
 */
public class CfClientMsg {

    private CfMessage cfMessage;
    public String title;
    public String content;
    public String datetime;

    public CfClientMsg(CfMessage cfMessage) {
        this.cfMessage = cfMessage;

        //生成数据
        genTitle();
        genContent();
        this.datetime = Tool.formatDateTime(this.cfMessage.time * 1000);
    }


    private void genTitle() {
        this.title = cfMessage.title;
    }

    private void genContent() {
        //评论消息单独设定
        if (cfMessage.type == OutsouringCrowdfunding.COMMENT_CFMT) {
            //拼接消息内容
            StringBuilder content = new StringBuilder(cfMessage.comment.userNick+": ");
            if (!Tool.isEmpty(cfMessage.comment.repliedUserNick)){
                content.append("回复 ").append(cfMessage.comment.repliedUserNick).append(" ");
            }
            content.append(cfMessage.comment.content);
            this.content = content.toString();
        }else {
            this.content = cfMessage.content;
        }
    }

}
