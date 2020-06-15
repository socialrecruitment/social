package com.example.demo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class atestajxaController {
    @RequestMapping(value = "/data/getStr" ,produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getStr(){
        return "true";
    }

}
