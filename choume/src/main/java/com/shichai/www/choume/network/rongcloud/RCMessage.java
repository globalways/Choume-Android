package com.shichai.www.choume.network.rongcloud;

import android.os.Parcel;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

/**
 * Created by wang on 16/1/28.
 */
@MessageTag(value = "CF:message", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class RCMessage extends MessageContent{

    private String content;

    protected RCMessage() {}

    /**
     * 构造函数。
     *
     * @param in 初始化传入的 Parcel。
     */
    public RCMessage(Parcel in) {
        setContent(ParcelUtils.readFromParcel(in));
        //Log.d("yangping", "RCMessage(Parcel in)" + ParcelUtils.readFromParcel(in));
    }
    /**
     * 构造函数。
     *
     * @param data 初始化传入的二进制数据。
     */
    public RCMessage(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e("JSONException", e.getMessage());
        }

        setContent(jsonStr);
    }

    public static RCMessage obtain(String text) {
        RCMessage model = new RCMessage();
        model.setContent(text);
        return model;
    }

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("content", getContent());

        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
        }

        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, content);
    }

    public static final Creator<RCMessage> CREATOR = new Creator<RCMessage>() {
        @Override
        public RCMessage createFromParcel(Parcel source) {
            return new RCMessage(source);
        }

        @Override
        public RCMessage[] newArray(int size) {
            return new RCMessage[size];
        }
    };

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}