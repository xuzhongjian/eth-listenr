package com.ganten.ethlistener.feishu.request;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MsgType {
    INTERACTIVE("interactive"),
    IMAGE("image"),
    TEXT("text"),
    POST("post");

    private final String value;

    MsgType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
