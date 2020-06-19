package com.example.demo.Entity;

import java.util.Date;

public class BlogCommentInfo {
    private Integer comment_id;
    private String blog_comment_email;
    private String comment_text;
    private Date comment_date;
    private Integer blog_id;

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

    public Integer getBlog_id() {
        return blog_id;
    }

    public void setBlog_id(Integer blog_id) {
        this.blog_id = blog_id;
    }
}
