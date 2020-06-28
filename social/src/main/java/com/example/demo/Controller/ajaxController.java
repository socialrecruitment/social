package com.example.demo.Controller;

import com.example.demo.Entity.RecruitmentInfo;
import com.example.demo.Entity.UserInfo;
import com.example.demo.Mapper.RecruitmentMapper;
import com.example.demo.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;


@Controller
@RequestMapping("ajax")
public class ajaxController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RecruitmentMapper recruitmentMapper;

    private Timer currentTimer = null;

    @PostMapping("/registercheck")
    @ResponseBody()
    public String CheckRegister(@RequestParam("user_email") String user_email,
                                @RequestParam("user_password") String user_password, UserInfo userInfo, HttpSession session) {
        int i = userMapper.saveUserAccount(userInfo);
        userInfo = userMapper.userLogin(user_email, user_password);
        if (userInfo != null) {
            session.setAttribute("user", userInfo);
            return "200";
        } else {
            return "300";
        }


    }

    @PostMapping("/logincheck")
    @ResponseBody()
    public String CheckLogin(@RequestParam("user_email") String user_email,
                             @RequestParam("user_password") String user_password, HttpSession session) {
        session.setAttribute("test", 123456);
        UserInfo userInfo = userMapper.userLogin(user_email, user_password);
        if (userInfo != null) {
            session.setAttribute("user", userInfo);
            if (currentTimer == null) {
                this.addTestData(1000);
            }
            return "200";
        } else {
            return "300";
        }

    }

    private void addTestData(int timeInterval) {
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                int max = 25000;
                int min = 1;
                Random random = new Random();
                RecruitmentInfo recruitmentInfo = new RecruitmentInfo();
                recruitmentInfo.setJob_name("测试工程师");
                recruitmentInfo.setSalary(10000);
                recruitmentInfo.setJob_experience("2-3年经验");
                recruitmentInfo.setEducation_required("硕士");
                recruitmentInfo.setRecruiting_numbers("5");
                recruitmentInfo.setWelfare("五险一金 补充医疗保险 定期体检 加班补助 年终奖 股票期权 带薪年假 员工旅游 餐补 通讯补贴 节日福利 年假递增");
                recruitmentInfo.setJob_information("任职资格： " +
                        "1. 计算机相关专业，1~3年以上测试经验。有大数据平台测试、安全产品测试、网络设备测试经验者优先。\n" +
                        "2. 熟悉项目开发过程、熟悉软件测试方法、熟悉用例设计方法、熟悉常用测试工具。\n" +
                        "3. 熟悉Linux操作系统、数据库，能在linux中搭建产品测试环境、进行功能、性能、稳定性等测试。\n" +
                        "4. 熟悉各种常见网络协议（TCP、HTTP、DNS等）、网络设备应用\n" +
                        "工作职责： 负责大数据平台的测试工作");
                recruitmentInfo.setCompany_id(random.nextInt(max) % (max - min + 1) + min);
                int i = recruitmentMapper.saveRec(recruitmentInfo);
                recruitmentInfo = null;
            }
        }, time, timeInterval);

        currentTimer = timer;
    }
}
