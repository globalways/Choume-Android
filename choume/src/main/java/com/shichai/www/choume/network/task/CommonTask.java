package com.shichai.www.choume.network.task;

import android.os.AsyncTask;
import com.shichai.www.choume.network.ManagerCallBack;
import io.grpc.ManagedChannel;

/**
 * task的公共类
 * Created by wyp on 15/12/22.
 */
public class CommonTask<P,R> extends AsyncTask<Object, Void, R> {

    protected ManagerCallBack<R> callBack;
    protected P taskParam;
    protected ManagedChannel channel;
    protected Exception exception;

    public CommonTask setCallBack(ManagerCallBack<R> callBack) {
        this.callBack = callBack;
        return this;
    }

    public CommonTask setTaskParam(P taskParam) {
        this.taskParam = taskParam;
        return this;
    }

    @Override

    protected R doInBackground(Object... params) {
        return null;
    }

    @Override
    protected void onPostExecute(R response) {

    }
}
