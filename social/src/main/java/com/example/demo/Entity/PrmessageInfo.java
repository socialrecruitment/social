package com.example.demo.Entity;

import java.sql.Timestamp;

public class PrmessageInfo {
    private Integer prmessage_id;
    private String user_email;
    private String user_name;
    private String prcontent;
    private Timestamp prposttime;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Integer getPrmessage_id() {
        return prmessage_id;
    }

    public void setPrmessage_id(Integer prmessage_id) {
        this.prmessage_id = prmessage_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getPrcontent() {
        return prcontent;
    }

    public void setPrcontent(String prcontent) {
        this.prcontent = prcontent;
    }

    public Timestamp getPrposttime() {
        return prposttime;
    }

    public void setPrposttime(Timestamp prposttime) {
        this.prposttime = prposttime;
    }
}
