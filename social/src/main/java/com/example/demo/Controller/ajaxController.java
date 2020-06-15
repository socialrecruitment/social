package com.example.demo.Controller;

import com.example.demo.Entity.UserInfo;
import com.example.demo.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("ajax")
public class ajaxController {
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/registercheck")
    @ResponseBody()
    private String CheckRegister(@RequestParam("user_email") String user_email,
                                 @RequestParam("user_password") String user_password,UserInfo userInfo,HttpSession session){
        int i=userMapper.saveUserAccount(userInfo);
        userInfo=userMapper.userLogin(user_email,user_password);
        if(userInfo!=null){
            session.setAttribute("user",userInfo);
            return "200";
        }else {
            return "300";
        }
    }

    @PostMapping("/logincheck")
    @ResponseBody()
    private String CheckLogin(@RequestParam("user_email") String user_email,
                                 @RequestParam("user_password") String user_password,HttpSession session){
        session.setAttribute("test",123456);
        UserInfo userInfo=userMapper.userLogin(user_email,user_password);
        if(userInfo!=null){
            session.setAttribute("user",userInfo);
            return "200";
        }else {
            return "300";
        }
    }
}
