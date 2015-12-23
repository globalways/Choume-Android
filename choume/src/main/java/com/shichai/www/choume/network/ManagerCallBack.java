package com.shichai.www.choume.network;

/**
 * Created by wyp on 15/12/22.
 */
public abstract class ManagerCallBack<E> {

    /**
     * 用户正确操作
     * @param result
     */
    public void success(E result){

    }

    /**
     * 用户操作错误，提示
     * @param code
     * @param msg
     */
    public void warning(int code, String msg){

    }

    /**
     * 服务器，网络，等其他错误
     * @param e
     */
    public void error(Exception e){

    }

    /**
     * 进度  上传图片的时候用到
     * @param progress 1-100
     */
    public void progress(int progress){

    }

}
