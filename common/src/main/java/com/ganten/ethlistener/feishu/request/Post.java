package com.ganten.ethlistener.feishu.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Post {
    @JsonProperty("zh_cn")
    private ZhCn zhCn;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("en_us")
    private EnUs enUs;

    public ZhCn getZhCn() {
        return zhCn;
    }

    public void setZhCn(ZhCn zhCn) {
        this.zhCn = zhCn;
    }

    public EnUs getEnUs() {
        return enUs;
    }

    public void setEnUs(EnUs enUs) {
        this.enUs = enUs;
    }
}
