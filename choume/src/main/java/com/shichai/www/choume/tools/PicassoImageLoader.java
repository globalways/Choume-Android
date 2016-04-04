package com.shichai.www.choume.tools;

/**
 * Created by wyp on 15/12/27.
 */

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.lang.ref.SoftReference;

/**
 * Picasso图片加载器
 * @author wyp
 */
public class PicassoImageLoader {
    private SoftReference<Context> mContext = null;

    public PicassoImageLoader(Context context) {
        mContext = new SoftReference<Context>(context);
    }

    public void loadImageToView(File image, int targetWidth, int targetHeight, int placeholderResId, int errorResId,
                          ImageView view) {
        if (null != image && image.exists()) {
            Picasso.with(mContext.get()).load(image).resize(targetWidth, targetHeight).centerCrop()
                    .placeholder(placeholderResId).error(errorResId).into(view);
        }
    }

    public void loadImageToView(File image, int placeholderResId, int errorResId, ImageView view) {
        if (null != image && image.exists()) {
            Picasso.with(mContext.get()).load(image).placeholder(placeholderResId).error(errorResId).into(view);
        }
    }

    public void loadUrlImageToView(String uri, int targetWidth, int targetHeight, int placeholderResId, int errorResId,
                                   ImageView view) {
        if (null != mContext && null != uri && !uri.isEmpty() ) {
            Picasso.with(mContext.get()).load(getUrl(targetWidth, targetHeight, uri))
                    .resize(targetWidth, targetHeight).centerCrop().placeholder(placeholderResId).error(errorResId)
                    .into(view);
        }else {
            view.setImageResource(errorResId);
        }
    }

    public void loadUrlImageToView(String uri, int targetWidth, int targetHeight, int placeholderResId, int errorResId,
                                   ImageView view, Callback callback) {
        if (null != mContext && null != uri && !uri.isEmpty() ) {
            Picasso.with(mContext.get()).load(getUrl(targetWidth, targetHeight, uri))
                    .resize(targetWidth, targetHeight).centerCrop().placeholder(placeholderResId).error(errorResId)
                    .into(view, callback);
        }else {
            view.setImageResource(errorResId);
        }
    }

    private String getUrl(int targetWidth, int targetHeight, String url) {
        StringBuilder sb = new StringBuilder(url);
        sb.append("?").append("imageView2/1/w/").append(targetWidth)
                .append("/h/").append(targetHeight);
        return sb.toString();
    }
}
