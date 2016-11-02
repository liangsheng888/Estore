package com.estore.pojo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/1 0001.
 */
public class Message implements Serializable{
    private  Integer userId;
    private  String title;
    private  String content;

    public Message(String content, String title, Integer userId) {
        this.content = content;
        this.title = title;
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
