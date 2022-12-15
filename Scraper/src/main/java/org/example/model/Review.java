package org.example.model;

import java.util.Date;

public class Review {
    private String text;
    private String title;
    private Date date;
    private int value;

    public Review(String text, String title, Date date, int value) {
        this.text = text;
        this.title = title;
        this.date = date;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
