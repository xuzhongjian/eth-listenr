package com.ganten.ethlistener.feishu.request;

import java.util.List;

public class Card {
    private List<Element> elements;
    private Header header;

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }
}