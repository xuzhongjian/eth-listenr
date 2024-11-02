package com.ganten.ethlistener.feishu;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ganten.ethlistener.util.JacksonUtils;

public class FeishuResponse {
    @JsonProperty("StatusCode")
    private int statusCode;
    @JsonProperty("StatusMessage")
    private String statusMessage;
    private int code;
    private Object data;  // 使用 Object，因为 data 可能是空对象或其他类型
    private String msg;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return JacksonUtils.toJson(this);
    }
}
