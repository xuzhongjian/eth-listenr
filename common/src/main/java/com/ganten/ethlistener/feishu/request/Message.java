package com.ganten.ethlistener.feishu.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ganten.ethlistener.util.JacksonUtils;

public class Message {
    @JsonProperty("msg_type")
    private String msgType;
    private Card card;
    private Content content;

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType.getValue();
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public String toString() {
        return JacksonUtils.toJson(this);
    }
}


