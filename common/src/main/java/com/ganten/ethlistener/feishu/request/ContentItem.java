package com.ganten.ethlistener.feishu.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ContentItem {
    private String tag;
    private String text;
    private String href;
    @JsonProperty("user_id")
    private String userId;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}