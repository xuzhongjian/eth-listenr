package com.ganten.ethlistener.feishu;

import com.ganten.ethlistener.feishu.request.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessageBuilder {

    public static Message buildSampleMessage(String msg) {
        Content content = new Content();
        content.setText(msg);
        Message message = new Message();
        message.setMsgType(MsgType.TEXT);
        message.setContent(content);
        return message;
    }

    public static Message buildListMessage(String title, List<String> msg) {
        Message message = new Message();
        message.setMsgType(MsgType.POST);
        Content content = new Content();

        Post post = new Post();
        ZhCn zhCn = new ZhCn();
        zhCn.setTitle(title);
        List<ContentItem> contentItems = new ArrayList<>();
        for (String m : msg) {
            ContentItem contentItem = new ContentItem();
            contentItem.setTag("text");
            contentItem.setText(m);
            contentItems.add(contentItem);
        }
        List<List<ContentItem>> contents = Collections.singletonList(contentItems);
        zhCn.setContent(contents);
        post.setZhCn(zhCn);
        content.setPost(post);
        message.setContent(content);
        return message;
    }
}
