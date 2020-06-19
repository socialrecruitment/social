package com.example.demo.Entity;

import java.util.Date;

public class BlogInfo {
    private Integer blog_id;
    private Date blog_date;
    private String blog_user_email;
    private String blog_photo;
    private String blog_title;
    private String blog_minitext;
    private String blog_text;
    private String blog_tag;
    private Integer comment_id;
    private String blog_comment_email;
    private String comment_text;
    private Date comment_date;

    public Integer getBlog_id() {
        return blog_id;
    }

    public void setBlog_id(Integer blog_id) {
        this.blog_id = blog_id;
    }

    public Date getBlog_date() {
        return blog_date;
    }

    public void setBlog_date(Date blog_date) {
        this.blog_date = blog_date;
    }

    public String getBlog_user_email() {
        return blog_user_email;
    }

    public void setBlog_user_email(String blog_user_email) {
        this.blog_user_email = blog_user_email;
    }

    public String getBlog_photo() {
        return blog_photo;
    }

    public void setBlog_photo(String blog_photo) {
        this.blog_photo = blog_photo;
    }

    public String getBlog_title() {
        return blog_title;
    }

    public void setBlog_title(String blog_title) {
        this.blog_title = blog_title;
    }

    public String getBlog_minitext() {
        return blog_minitext;
    }

    public void setBlog_minitext(String blog_minitext) {
        this.blog_minitext = blog_minitext;
    }

    public String getBlog_text() {
        return blog_text;
    }

    public void setBlog_text(String blog_text) {
        this.blog_text = blog_text;
    }

    public String getBlog_tag() {
        return blog_tag;
    }

    public void setBlog_tag(String blog_tag) {
        this.blog_tag = blog_tag;
    }

    public Integer getComment_id() {
        return comment_id;
    }

    public void setComment_id(Integer comment_id) {
        this.comment_id = comment_id;
    }

    public String getBlog_comment_email() {
        return blog_comment_email;
    }

    public void setBlog_comment_email(String blog_comment_email) {
        this.blog_comment_email = blog_comment_email;
    }

    public String getComment_text() {
        return comment_text;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public Date getComment_date() {
        return comment_date;
    }

    public void setComment_date(Date comment_date) {
        this.comment_date = comment_date;
    }
}
