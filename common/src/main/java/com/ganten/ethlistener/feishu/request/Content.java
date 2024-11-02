package com.ganten.ethlistener.feishu.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Content {
    @JsonProperty("image_key")
    private String imageKey;
    private Post post;
    private String text;

    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }

    public Post getPost() {
        return post;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
