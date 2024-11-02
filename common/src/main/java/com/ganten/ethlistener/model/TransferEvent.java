package com.ganten.ethlistener.model;

import com.ganten.ethlistener.util.JacksonUtils;

import java.util.Date;

public class TransferEvent {
    private String from;
    private String to;
    private double value;
    private Date date;

    public TransferEvent(String from, String to, double value, Date date) {
        this.from = from;
        this.to = to;
        this.value = value;
        this.date = date;
    }

    public TransferEvent() {
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public double getValue() {
        return value;
    }

    public Date getDate() {
        return date;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String toString() {
        return JacksonUtils.toJson(this);
    }
}