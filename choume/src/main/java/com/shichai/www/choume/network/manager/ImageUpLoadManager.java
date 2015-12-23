package com.shichai.www.choume.network.manager;

import com.globalways.user.qiniu.nano.UserQiniu.MakeQiniuUpTokenParam;
import com.globalways.user.qiniu.nano.UserQiniu.MakeQiniuUpTokenResp;
import com.globalways.user.qiniu.nano.UserQiniu.QiniuUpToken;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.shichai.www.choume.network.HttpConfig;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.task.qiniu.MakeUpTokenTask;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


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
	public void upLoadImage(final String[] path, final ManagerCallBack<List<String>> callBack) {
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
						if(path[i].equals(arg0)){
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
			getUploadToken(null, new ManagerCallBack<QiniuUpToken>() {

				@Override
				public void success(final QiniuUpToken result) {
					uploadManager.put(path[index], path[index], result.uptoken, new UpCompletionHandler() {
								@Override
								public void complete(String key, ResponseInfo info, JSONObject response) {
									try {
										qiniuList.add(result.domain+response.getString(HttpConfig.QINIU_FEEDBACK_KEY));
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
	 * @param key
	 *            更新图片需要把原图的key传递过去，新增为null
	 * @param callBack
	 */
	private void getUploadToken(String key, final ManagerCallBack<QiniuUpToken> callBack) {
		MakeQiniuUpTokenParam taskParam = new MakeQiniuUpTokenParam();
		taskParam.bucket= HttpConfig.QINIU_BUCKET;
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
