package com.example.demo.Util;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public class ResultMap extends HashMap {

    public ResultMap(){
        this.put("status", HttpServletResponse.SC_OK);
    }

    public ResultMap(Object data){
        this.setData(data);
        this.setStatus(HttpServletResponse.SC_OK);
    }

    public ResultMap(Object data, int totalCount){
        this.init(data, totalCount, HttpServletResponse.SC_OK);
    }

    public ResultMap(Object data, int totalCount, int status){
        this.init(data, totalCount, status);
    }

    private void init(Object data, int totalCount, int status){
        this.setData(data);
        this.setTotalCount(totalCount);
        this.setStatus(status);
    }

    public void setData(Object data){
        this.put("rows", data);
    }

    public void setTotalCount(int count){
        this.put("total", count);
    }

    public void setStatus(int status){
        this.put("status", status);
    }

    public void setError(String error) {
        this.put("error", error);
    }

    public void setSuccess(String success){
    	this.put("success",success);
	}

    public void setFlag(boolean flag) { this.put("flag",flag);}

    public void setRet(int i) {this.put("ret",i);}
}
