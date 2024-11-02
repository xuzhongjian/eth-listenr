package com.ganten.ethlistener.model;

import com.ganten.ethlistener.annotation.CSVColumn;
import com.ganten.ethlistener.util.JacksonUtils;

public class EventExport {
    @CSVColumn(order = 1)
    private String date;
    @CSVColumn(order = 2)
    private String from;
    @CSVColumn(order = 3)
    private String to;
    @CSVColumn(order = 4)
    private double value;

    public EventExport() {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String toString() {
        return JacksonUtils.toJson(this);
    }
}