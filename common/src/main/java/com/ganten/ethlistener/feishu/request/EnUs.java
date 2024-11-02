package com.ganten.ethlistener.feishu.request;

import java.util.List;

public class EnUs {
    private String title;
    private List<List<ContentItem>> content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<List<ContentItem>> getContent() {
        return content;
    }

    public void setContent(List<List<ContentItem>> content) {
        this.content = content;
    }
}
