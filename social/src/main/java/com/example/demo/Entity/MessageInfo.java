package com.example.demo.Entity;

import java.sql.Timestamp;
import java.util.Date;

public class MessageInfo {
    private Integer message_id;
    private String user_email;
    private String theme;
    private String content;
    private Timestamp posttime;
    private String user_images;
    private String user_name;

    public Integer getMessage_id() {
        return message_id;
    }

    public void setMessage_id(Integer message_id) {
        this.message_id = message_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getPosttime() {
        return posttime;
    }

    public void setPosttime(Timestamp posttime) {
        this.posttime = posttime;
    }

    public String getUser_images() {
        return user_images;
    }

    public void setUser_images(String user_images) {
        this.user_images = user_images;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
