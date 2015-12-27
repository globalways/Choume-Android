package com.shichai.www.choume.network.manager;

import android.util.Log;
import com.globalways.user.qiniu.nano.UserQiniu.MakeQiniuUpTokenParam;
import com.globalways.user.qiniu.nano.UserQiniu.MakeQiniuUpTokenResp;
import com.globalways.user.qiniu.nano.UserQiniu.QiniuUpToken;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.shichai.www.choume.application.MyApplication;
import com.shichai.www.choume.network.HttpConfig;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.task.qiniu.MakeUpTokenTask;
import com.shichai.www.choume.tools.LocalDataConfig;
import com.shichai.www.choume.tools.MD5;
import com.shichai.www.choume.tools.Tool;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class ImageUpLoadManager {
	private static final String TAG = ImageUpLoadManager.class.getSimpleName();

	public ImageUpLoadManager() {
	}

	private List<String> qiniuList = new ArrayList<String>();;
	private boolean isGoOn = true;

	/**
	 * 上传图片到七牛服务器
	 * 
	 * @param path
	 *            path为图片在本地的绝对路径
	 * @param callBack
	 */
	public void upLoadImage(final String[] path, String token, final ManagerCallBack<List<String>> callBack) {
		final UploadManager uploadManager = new UploadManager();
		final double[] percents = new double[path.length];
		// modify by wyp 
		// 增加上传进度
		final UpProgressHandler progressHandler = new UpProgressHandler() {
			@Override
			public void progress(String arg0, double arg1) {
				synchronized(this) {
					int index = 0;
					double percent = 0d;
					for(int i=0;i<path.length;i++){
						//Log.i("yangping:arg0: ",arg0+"");
						if(MD5.getMD5(path[i]).equals(arg0)){
							index = i;break;
						}
					}
					percents[index] = arg1;
					for(int i=0;i<percents.length;i++){
						percent += percents[i];
					}

					//计算进度0~100%
					long longPercents = Tool.mulAsLong(Tool.div(String.valueOf(percent), String.valueOf(path.length), 2), String.valueOf(100));
					callBack.progress((int)longPercents);
				}
			}
		};
		//end
		for (int i = 0; i < path.length; i++) {
			if (!isGoOn) {
				return;
			}
			final int index = i;
			//要增加上传进度，就不能做覆盖图片,每次上传重新生成key,因为cm服务端不支持key为null by wyp 2015-12-27
			final String key = MD5.getMD5(path[index]);
			getUploadToken(key, token, new ManagerCallBack<QiniuUpToken>() {

				@Override
				public void success(final QiniuUpToken result) {
					uploadManager.put(path[index], key, result.uptoken, new UpCompletionHandler() {
								@Override
								public void complete(String key, ResponseInfo info, JSONObject response) {
									try {
										qiniuList.add("http://"+result.domain +"/"+ response.getString(HttpConfig.QINIU_FEEDBACK_KEY));
									} catch (JSONException e) {
										e.printStackTrace();
									}
									if (qiniuList.size() == path.length) {
										if (callBack != null) {
											callBack.success(qiniuList);
										}
									}
								}
							},
							// modify by wyp
							// 增加上传进度
							new UploadOptions(null, null, false, progressHandler, null));
					//end
				}

				@Override
				public void warning(int code, String msg) {
					super.warning(code, msg);
					isGoOn = true;
					if (callBack != null) {
						callBack.warning(code, msg);
					}
				}

				@Override
				public void error(Exception e) {
					super.error(e);
					isGoOn = true;
					if (callBack != null) {
						callBack.error(e);
					}
				}
			});
		}
	}
	/**
	 * 每张图片都需要到服务器获取一次token
	 * 
	 * @param key 不能为null
	 *            更新图片需要把原图的key传递过去
	 * @param callBack
	 */
	private void getUploadToken(String key, String token, final ManagerCallBack<QiniuUpToken> callBack) {
		MakeQiniuUpTokenParam taskParam = new MakeQiniuUpTokenParam();
		taskParam.bucket = HttpConfig.QINIU_BUCKET;
		taskParam.appId  = HttpConfig.APPID;
		taskParam.token  = token;
		taskParam.key    = key;
		if (null != key) {
			taskParam.key = key;
		}

		new MakeUpTokenTask().setTaskParam(taskParam).setCallBack(new ManagerCallBack<MakeQiniuUpTokenResp>() {
			@Override
			public void error(Exception e) {
				callBack.error(e);
			}

			@Override
			public void warning(int code, String msg) {
				callBack.warning(code, msg);
			}

			@Override
			public void success(MakeQiniuUpTokenResp result) {
				callBack.success(result.upToken);
			}
		}).execute();

	}

}
