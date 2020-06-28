package com.example.demo.Controller;


import com.example.demo.Entity.*;
import com.example.demo.Mapper.*;
import com.example.demo.Util.FileUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Timer;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private RecruitmentMapper recruitmentMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private CollectMapper collectMapper;

    private Timer currentTimer = null;

    /**
     * 用于login页面功能，主要用于显示用户登录页面
     *
     * @return 返回login页面，即登录页面
     */
    @GetMapping("/login")
    public String pageLogin() {
        return "login.html";
    }

    /**
     * 用于获取用户邮箱和密码 主要用于接收用户登录的信息
     *
     * @param user_email    用户邮箱
     * @param user_password 用户密码
     * @param session       用户session对象，用于识别是否为同一对象
     * @return 返回index页面或login页面
     */
    @PostMapping("/login")
    public String UserLogin(@RequestParam("user_email") String user_email,
                            @RequestParam("user_password") String user_password,
                            HttpSession session) {
        session.setAttribute("test", 123456);
        UserInfo userInfo = userMapper.userLogin(user_email, user_password);
        if (userInfo != null) {
            session.setAttribute("user", userInfo);
            return "redirect:/user/index";
        }
        return "login.html";
    }


    /**
     * 用于register页面功能，主要用于显示注册页面
     *
     * @return 返回register页面，即注册页面
     */
    @GetMapping("/register")
    public String pageRegister() {
        return "register.html";
    }

    /**
     * 用于register页面功能，主要用于接收用户注册的信息并向数据库插入数据
     *
     * @param userInfo userInfo对象，用于存放用户注册信息
     * @return 返回login页面
     */
    @PostMapping("/register")
    public String UserRegister(UserInfo userInfo) {
        int i = userMapper.saveUserAccount(userInfo);
        return "login.html";
    }

    /**
     * 用于index页面功能，主要用于显示首页信息
     *
     * @param session 用户session对象，用于识别是否为同一对象
     * @return 返回index页面，即首页
     */
    @GetMapping("/index")
    public String pageAdmin(HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        return "index.html";
    }

    /**
     * 用于index页面功能，主要用于用户查询职位和行业信息
     *
     * @param job_name         职位名
     * @param company_industry 行业名
     * @param model            调用model传值
     * @return 返回job-list-sidebar页面，即公司职位列表页面
     */
    @PostMapping("/index")
    public String pageIndex(@RequestParam(value = "job_name", defaultValue = "") String job_name,
                            @RequestParam(value = "company_industry", defaultValue = "") String company_industry,
                            Model model) {
        List<RecruitmentInfo> lists = null;
        if (job_name.isEmpty() && company_industry.isEmpty()) {    //无条件查询
            lists = recruitmentMapper.SelectAll();
        } else if (!job_name.isEmpty() && company_industry.isEmpty()) {    //职位查询
            lists = recruitmentMapper.selectjob('%' + job_name);
        } else if (job_name.isEmpty() && !company_industry.isEmpty()) {    //行业模糊查询
            lists = recruitmentMapper.selectindu('%' + company_industry);
        } else {    //职位、行业模糊查询
            lists = recruitmentMapper.selectinduJob('%' + company_industry, '%' + job_name);
        }
        List<CompanyInfo> coms = companyMapper.getAllCompany();
        model.addAttribute("coms", coms);
        model.addAttribute("selects", lists);
        model.addAttribute("job_name", job_name);
        model.addAttribute("company_industry", company_industry);

        return "redirect:/user/joblist";
    }


    /**
     * 用于首页功能，主要用于行业信息显示功能
     *
     * @param id    行业id，1-8
     * @param model 调用model传值
     * @return 返回joblistdata页面，即行业内所有职位信息页面
     */
    @GetMapping("/industry/{id}")
    public String getJrong(@PathVariable(value = "id") Integer id, Model model) {
        List<RecruitmentInfo> lists = null;
        String company_industry;
        if (id == 1) {
            company_industry = "金融";
            lists = recruitmentMapper.seindu(company_industry);
        } else if (id == 2) {
            company_industry = "媒体";
            lists = recruitmentMapper.seindu(company_industry);
        } else if (id == 3) {
            company_industry = "运营";

            lists = recruitmentMapper.seindu('%' + company_industry);
        } else if (id == 4) {
            company_industry = "技术";
            lists = recruitmentMapper.seindu(company_industry);
        } else if (id == 5) {
            company_industry = "设计";
            lists = recruitmentMapper.seindu(company_industry);

        } else if (id == 6) {
            company_industry = "产品";
            lists = recruitmentMapper.seindu(company_industry);
        } else if (id == 7) {
            company_industry = "教育培训";
            lists = recruitmentMapper.seindu(company_industry);
        } else {
            company_industry = "销售";
            lists = recruitmentMapper.seindu(company_industry);
        }
        System.out.println(company_industry);
        model.addAttribute("company_industry", company_industry);
        model.addAttribute("selects", lists);
        return "joblistdata.html";
    }


    /**
     * 用于job-list-sidebar页面功能，主要用于查询职位和行业
     *
     * @param pageIndex        分页页数,id
     * @param pageSize         每一页有多少条数据
     * @param job_name         职位名
     * @param company_industry 行业名
     * @param model            调用model传值
     * @return 返回job-list-sidebar页面，即公司职位列表页面
     */
    @RequestMapping("/joblist")
    public String getStudentByIDName(@RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
                                     @RequestParam(value = "pageSize", defaultValue = "7") Integer pageSize,
                                     @RequestParam(value = "job_name", defaultValue = "") String job_name,
                                     @RequestParam(value = "company_industry", defaultValue = "") String company_industry,
                                     Model model) {
        PageHelper.startPage(pageIndex, pageSize);
        List<RecruitmentInfo> lists = null;
        PageInfo<RecruitmentInfo> pages = null;
        if (job_name.isEmpty() && company_industry.isEmpty()) {    //无条件查询
            lists = recruitmentMapper.userSelectAll(pageIndex, pageSize);
            pages = new PageInfo<>(lists);
        } else if (job_name.isEmpty() && !company_industry.isEmpty()) {    //行业模糊查询
            lists = recruitmentMapper.userselectindu('%' + company_industry + '%', pageIndex, pageSize);
            pages = new PageInfo<>(lists);
        } else if (!job_name.isEmpty() && company_industry.isEmpty()) {    //职位模糊查询
            lists = recruitmentMapper.userselectjob('%' + job_name + '%', pageIndex, pageSize);
            pages = new PageInfo<>(lists);
        } else {    //职位模糊查询
            lists = recruitmentMapper.userselectinduJob('%' + company_industry + '%', job_name, pageIndex, pageSize);
            pages = new PageInfo<>(lists);
        }

        model.addAttribute("selects", pages);
        System.out.println(job_name);
        String jn = "测试工程师";
        model.addAttribute("job_name", job_name.equals("") ? jn : job_name);
        model.addAttribute("company_industry", company_industry);
        return "job-list-sidebar.html";
    }


    /**
     * 用于report页面功能，主要用于根据用户选择的一条数据来获取相应的报告信息
     *
     * @param job_name 职位名
     * @param model    调用model传值
     * @return 返回report页面，即分析报告页
     */
    @GetMapping("/report/{job_name}")
    public String pageReport(@PathVariable("job_name") String job_name, Model model) {
        if (!job_name.equals("")) {
            //热门职位按发布时间分组的发布数量
            int xs1 = recruitmentMapper.xs1();
            model.addAttribute("xs1", xs1);
            int xs2 = recruitmentMapper.xs2();
            model.addAttribute("xs2", xs2);
            int xs3 = recruitmentMapper.xs3();
            model.addAttribute("xs3", xs3);
            int xs4 = recruitmentMapper.xs4();
            model.addAttribute("xs4", xs4);
            int xs5 = recruitmentMapper.xs5();
            model.addAttribute("xs5", xs5);
            int xs6 = recruitmentMapper.xs6();
            model.addAttribute("xs6", xs6);
            int xs7 = recruitmentMapper.xs7();
            model.addAttribute("xs7", xs7);


            int sj1 = recruitmentMapper.sj1();
            model.addAttribute("sj1", sj1);
            int sj2 = recruitmentMapper.sj2();
            model.addAttribute("sj2", sj2);
            int sj3 = recruitmentMapper.sj3();
            model.addAttribute("sj3", sj3);
            int sj4 = recruitmentMapper.sj4();
            model.addAttribute("sj4", sj4);
            int sj5 = recruitmentMapper.sj5();
            model.addAttribute("sj5", sj5);
            int sj6 = recruitmentMapper.sj6();
            model.addAttribute("sj6", sj6);
            int sj7 = recruitmentMapper.sj7();
            model.addAttribute("sj7", sj7);

            int kf1 = recruitmentMapper.kf1();
            model.addAttribute("kf1", kf1);
            int kf2 = recruitmentMapper.kf2();
            model.addAttribute("kf2", kf2);
            int kf3 = recruitmentMapper.kf3();
            model.addAttribute("kf3", kf3);
            int kf4 = recruitmentMapper.kf4();
            model.addAttribute("kf4", kf4);
            int kf5 = recruitmentMapper.kf5();
            model.addAttribute("kf5", kf5);
            int kf6 = recruitmentMapper.kf6();
            model.addAttribute("kf6", kf6);
            int kf7 = recruitmentMapper.kf7();
            model.addAttribute("kf7", kf7);

            int ui1 = recruitmentMapper.ui1();
            model.addAttribute("ui1", ui1);
            int ui2 = recruitmentMapper.ui2();
            model.addAttribute("ui2", ui2);
            int ui3 = recruitmentMapper.ui3();
            model.addAttribute("ui3", ui3);
            int ui4 = recruitmentMapper.ui4();
            model.addAttribute("ui4", ui4);
            int ui5 = recruitmentMapper.ui5();
            model.addAttribute("ui5", ui5);
            int ui6 = recruitmentMapper.ui6();
            model.addAttribute("ui6", ui6);
            int ui7 = recruitmentMapper.ui7();
            model.addAttribute("ui7", ui7);

            int soft1 = recruitmentMapper.soft1();
            model.addAttribute("soft1", soft1);
            int soft2 = recruitmentMapper.soft2();
            model.addAttribute("soft2", soft2);
            int soft3 = recruitmentMapper.soft3();
            model.addAttribute("soft3", soft3);
            int soft4 = recruitmentMapper.soft4();
            model.addAttribute("soft4", soft4);
            int soft5 = recruitmentMapper.soft5();
            model.addAttribute("soft5", soft5);
            int soft6 = recruitmentMapper.soft6();
            model.addAttribute("soft6", soft6);
            int soft7 = recruitmentMapper.soft7();
            model.addAttribute("soft7", soft7);


            int java1 = recruitmentMapper.java1();
            model.addAttribute("java1", java1);
            int java2 = recruitmentMapper.java2();
            model.addAttribute("java2", java2);
            int java3 = recruitmentMapper.java3();
            model.addAttribute("java3", java3);
            int java4 = recruitmentMapper.java4();
            model.addAttribute("java4", java4);
            int java5 = recruitmentMapper.java5();
            model.addAttribute("java5", java5);
            int java6 = recruitmentMapper.java6();
            model.addAttribute("java6", java6);
            int java7 = recruitmentMapper.java7();
            model.addAttribute("java7", java7);

            List<String> jname = recruitmentMapper.getname(job_name);
            model.addAttribute("job_name", jname);

            //公司所在职位的不同城市的发布数量
            List<String> city = recruitmentMapper.getjobcity(job_name);
            model.addAttribute("city", city);
            List<Integer> jobnum = recruitmentMapper.getjnumber(job_name);
            model.addAttribute("jobnum", jobnum);

            //获取近七天排名前六的职位的发布数量
            List<String> topjobname = recruitmentMapper.topjobname();
            model.addAttribute("topjobname", topjobname);
            List<String> postdate = recruitmentMapper.postdate();
            model.addAttribute("postdate", postdate);

            //表格
            List<RecruitmentInfo> list1 = recruitmentMapper.jobname(job_name);
            model.addAttribute("list1", list1);


            //学历要求
            List<String> Edunum1 = recruitmentMapper.getedunum1('%' + job_name + '%');
            model.addAttribute("Edunum1", Edunum1);
            List<String> Edunum2 = recruitmentMapper.getedunum2('%' + job_name + '%');
            model.addAttribute("Edunum2", Edunum2);
            List<String> Edunum3 = recruitmentMapper.getedunum3('%' + job_name + '%');
            model.addAttribute("Edunum3", Edunum3);
            List<String> Edunum4 = recruitmentMapper.getedunum4('%' + job_name + '%');
            model.addAttribute("Edunum4", Edunum4);
            List<String> Edunum5 = recruitmentMapper.getedunum5('%' + job_name + '%');
            model.addAttribute("Edunum5", Edunum5);
            List<String> Edunum6 = recruitmentMapper.getedunum6('%' + job_name + '%');
            model.addAttribute("Edunum6", Edunum6);
            List<String> Edunum7 = recruitmentMapper.getedunum7('%' + job_name + '%');
            model.addAttribute("Edunum7", Edunum7);
            List<String> Edu = recruitmentMapper.getedu('%' + job_name + '%');
            model.addAttribute("Edu", Edu);

            //工作经验要求
            List<String> expn1 = recruitmentMapper.getexpnum1('%' + job_name + '%');
            model.addAttribute("expn1", expn1);
            List<String> expn2 = recruitmentMapper.getexpnum2('%' + job_name + '%');
            model.addAttribute("expn2", expn2);
            List<String> expn3 = recruitmentMapper.getexpnum3('%' + job_name + '%');
            model.addAttribute("expn3", expn3);
            List<String> expn4 = recruitmentMapper.getexpnum4('%' + job_name + '%');
            model.addAttribute("expn4", expn4);
            List<String> expn5 = recruitmentMapper.getexpnum5('%' + job_name + '%');
            model.addAttribute("expn5", expn5);
            List<String> expn6 = recruitmentMapper.getexpnum6('%' + job_name + '%');
            model.addAttribute("expn6", expn6);
            List<String> expn7 = recruitmentMapper.getexpnum7('%' + job_name + '%');
            model.addAttribute("expn7", expn7);
            List<String> expn8 = recruitmentMapper.getexpnum8('%' + job_name + '%');
            model.addAttribute("expn8", expn8);
            List<String> exp = recruitmentMapper.getexp(job_name);
            model.addAttribute("exp", exp);
        }
        return "report.html";
    }


    /**
     * 用于userinformationindex页面，主要用于获取登录的用户的个人信息
     *
     * @param session 用户sesseion对象，用于识别是否为同一对象
     * @param model   调用model传值
     * @return 返回userinformationindex页面，即个人信息首页
     */
    @GetMapping("/userinformation")
    public String pageUserInformation(HttpSession session, Model model) {
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        List<UserInfo> listu = userMapper.getalluserinfo(userInfo.getUser_email());
        model.addAttribute("user", listu);
        return "userinformationindex.html";
    }

    /**
     * 用于submit-resume页面功能，主要用于获取和修改个人信息
     *
     * @param user_email 用户邮箱
     * @param model      调用model传值
     * @return 返回submit-resume页面，即用户修改信息页面
     */
    @GetMapping("/submit/{email}")
    public String updatePage(@PathVariable("email") String user_email, Model model) {
        UserInfo userInfo = userMapper.getUserByid(user_email);
        model.addAttribute("user", userInfo);
        return "submit-resume.html";
    }

    /**
     * 用于submit-resume页面功能，主要用于更新数据库数据
     *
     * @param userInfo userInfo对象，用于存放用户个人信息
     * @return 返回userinformationindex页面，即用户个人信息首页
     */
    @PostMapping("/submit")
    public String updateUser(UserInfo userInfo, @RequestParam("filepic") MultipartFile file1) {

        //1.保存文件至硬盘
        String fileName = file1.getOriginalFilename();
        String filePath = FileUtil.getUpLoadFilePath();
        fileName = System.currentTimeMillis() + fileName;//保存在磁盘上的文件名称

        try {
            FileUtil.uploadFile(file1.getBytes(), filePath, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //2.保存文件到数据库
        userInfo.setUser_images(fileName);
        int i = userMapper.updateUserByid(userInfo);
        return "redirect:/user/userinformation";
    }


    /**
     * 用于user-change-password页面功能，主要用于显示用户修改密码页面
     *
     * @param id         id，判断是否修改成功
     * @param user_email 用户邮箱
     * @param model      调用model传值
     * @return 返回user-change-password页面，即用户修改密码页面
     */
    @GetMapping("/useruppwd")
    public String pageUserPwd(@RequestParam(value = "id", defaultValue = "0") Integer id, String user_email, Model model) {
        UserInfo userInfo = userMapper.getUserByid1(user_email);
        String info = "";
        if (id == 0) {
            info = "";
        }
        if (id == 1) {
            info = "修改成功！";
        }
        model.addAttribute("user", userInfo);
        model.addAttribute("info", info);
        return "user-change-password.html";
    }

    /**
     * 用于user-change-password页面功能，主要用于更新数据库用户密码数据
     *
     * @param userInfo userInfo对象，用于存放修改的数据
     * @param session  用户session对象，用于识别是否为同一对象
     * @return 返回user-change-password页面，即用户修改密码页面
     */
    @PostMapping("/useruppwd")
    public String userpwdupdate(UserInfo userInfo, HttpSession session) {
        UserInfo userInfo1 = (UserInfo) session.getAttribute("user");
        userInfo.setUser_email(userInfo1.getUser_email());
        int i = userMapper.updateUserByid1(userInfo);
        return "redirect:/user/useruppwd?id=" + i;
    }


    /**
     * 用于about页面，主要用于显示关于我们信息
     *
     * @return 返回about页面，即关于我们信息页面
     */
    @GetMapping("/about")
    public String pageAbout() {
        return "about.html";
    }


    /**
     * 用于post-job页面，主要用于显示发布工作页面
     *
     * @return 返回post-job页面，即发布工作页面
     */
    @GetMapping("/postjob")
    public String pagePostJob() {
        return "post-job.html";
    }

    /**
     * 用于post-job页面，主要用于向数据库插入职位和公司信息
     *
     * @param companyInfo companyInfo对象，用于存放用户发布工作信息的数据
     * @return 返回post-job页面，即发布工作页面
     */
    @PostMapping("/postjob")
    public String GetPostJob(CompanyInfo companyInfo) {
        int i1 = userMapper.savePostJob1(companyInfo);
        int i2 = userMapper.savePostJob2(companyInfo);
        return "post-job.html";
    }

    /**
     * 用于test页面，主要用于显示职位数量地区分布图
     *
     * @param model 调用model传值
     * @return 返回test页面，即地区分布页面
     */
    @GetMapping("/test")
    public String GetTest(Model model) {
        int dq1 = recruitmentMapper.dq1();
        model.addAttribute("dq1", dq1);
        int dq2 = recruitmentMapper.dq2();
        model.addAttribute("dq2", dq2);
        int dq3 = recruitmentMapper.dq3();
        model.addAttribute("dq3", dq3);
        int dq4 = recruitmentMapper.dq4();
        model.addAttribute("dq4", dq4);
        int dq5 = recruitmentMapper.dq5();
        model.addAttribute("dq5", dq5);
        int dq6 = recruitmentMapper.dq6();
        model.addAttribute("dq6", dq6);
        int dq7 = recruitmentMapper.dq7();
        model.addAttribute("dq7", dq7);
        return "test.html";
    }

    @GetMapping("/report1/{company_industry}")
    public String report1(@PathVariable("company_industry") String company_industry, Model model) {
        if (!company_industry.equals("")) {
            int ed1 = recruitmentMapper.ed1();
            model.addAttribute("ed1", ed1);
            int ed2 = recruitmentMapper.ed2();
            model.addAttribute("ed2", ed2);
            int ed3 = recruitmentMapper.ed3();
            model.addAttribute("ed3", ed3);
            int ed4 = recruitmentMapper.ed4();
            model.addAttribute("ed4", ed4);
            int ed5 = recruitmentMapper.ed5();
            model.addAttribute("ed5", ed5);
            int ed6 = recruitmentMapper.ed6();
            model.addAttribute("ed6", ed6);


            int aed1 = recruitmentMapper.aed1();
            model.addAttribute("aed1", aed1);
            int aed2 = recruitmentMapper.aed2();
            model.addAttribute("aed2", aed2);
            int aed3 = recruitmentMapper.aed3();
            model.addAttribute("aed3", aed3);
            int aed4 = recruitmentMapper.aed4();
            model.addAttribute("aed4", aed4);
            int aed5 = recruitmentMapper.aed5();
            model.addAttribute("aed5", aed5);
            int aed6 = recruitmentMapper.aed6();
            model.addAttribute("aed6", aed6);


            int bed1 = recruitmentMapper.bed1();
            model.addAttribute("bed1", bed1);
            int bed2 = recruitmentMapper.bed2();
            model.addAttribute("bed2", bed2);
            int bed3 = recruitmentMapper.bed3();
            model.addAttribute("bed3", bed3);
            int bed4 = recruitmentMapper.bed4();
            model.addAttribute("bed4", bed4);
            int bed5 = recruitmentMapper.bed5();
            model.addAttribute("bde5", bed5);
            int bed6 = recruitmentMapper.bed6();
            model.addAttribute("bed6", bed6);


            int ced1 = recruitmentMapper.ced1();
            model.addAttribute("ced1", ced1);
            int ced2 = recruitmentMapper.ced2();
            model.addAttribute("ced2", ced2);
            int ced3 = recruitmentMapper.ced3();
            model.addAttribute("ced3", ced3);
            int ced4 = recruitmentMapper.ced4();
            model.addAttribute("ced4", ced4);
            int ced5 = recruitmentMapper.ced5();
            model.addAttribute("ced5", ced5);
            int ced6 = recruitmentMapper.ced6();
            model.addAttribute("ced6", ced6);


            int ded1 = recruitmentMapper.ded1();
            model.addAttribute("ded1", ded1);
            int ded2 = recruitmentMapper.ded2();
            model.addAttribute("ded2", ded2);
            int ded3 = recruitmentMapper.ded3();
            model.addAttribute("ded3", ded3);
            int ded4 = recruitmentMapper.ded4();
            model.addAttribute("ded4", ded4);
            int ded5 = recruitmentMapper.ded5();
            model.addAttribute("ded5", ded5);
            int ded6 = recruitmentMapper.ded6();
            model.addAttribute("ded6", ded6);


            int eed1 = recruitmentMapper.eed1();
            model.addAttribute("eed1", eed1);
            int eed2 = recruitmentMapper.eed2();
            model.addAttribute("eed2", eed2);
            int eed3 = recruitmentMapper.eed3();
            model.addAttribute("eed3", eed3);
            int eed4 = recruitmentMapper.eed4();
            model.addAttribute("eed4", eed4);
            int eed5 = recruitmentMapper.eed5();
            model.addAttribute("eed5", eed5);
            int eed6 = recruitmentMapper.eed6();
            model.addAttribute("eed6", eed6);

            int fed1 = recruitmentMapper.fed1();
            model.addAttribute("fed1", fed1);
            int fed2 = recruitmentMapper.ed2();
            model.addAttribute("fed2", fed2);
            int fed3 = recruitmentMapper.fed3();
            model.addAttribute("fed3", fed3);
            int fed4 = recruitmentMapper.fed4();
            model.addAttribute("ed4", fed4);
            int fed5 = recruitmentMapper.fed5();
            model.addAttribute("fed5", fed5);
            int fed6 = recruitmentMapper.fed6();
            model.addAttribute("fed6", fed6);

            List<String> comindu = recruitmentMapper.getindustry(company_industry);
            model.addAttribute("company_industry", comindu);

            List<String> exper = recruitmentMapper.getexper(company_industry);
            model.addAttribute("exper", exper);


            List<RecruitmentInfo> lists = recruitmentMapper.industry(company_industry);
            model.addAttribute("lists", lists);

            //十大热门行业发布的职位数量
            List<String> topindu = companyMapper.gettop10indu();
            model.addAttribute("topindu", topindu);
            List<Integer> top = companyMapper.gettop10num();
            model.addAttribute("top", top);

            //行业内热门职位的总发布数量
            //该职位在该行业内的排序
            List<String> Jname = recruitmentMapper.getjobname(company_industry);
            model.addAttribute("Jname", Jname);
            List<Integer> Jnum = recruitmentMapper.getjobnumber(company_industry);
            model.addAttribute("Jnum", Jnum);

            int count1 = recruitmentMapper.count1();
            model.addAttribute("count1", count1);
            int count2 = recruitmentMapper.count2();
            model.addAttribute("count2", count2);
            int count3 = recruitmentMapper.count3();
            model.addAttribute("count3", count3);
        }
        return "report1.html";
    }


    //公司详情
    @GetMapping("/companydetail/{id}")
    public String singleCompany(@PathVariable("id") Integer company_id, Model model) {
        List<CompanyInfo> lists = companyMapper.companyDetail(company_id);
        model.addAttribute("company", lists);
        return "single-company.html";
    }


    @GetMapping("/randlist")
    public String randList(Model model) {
        List<CompanyInfo> randlist = companyMapper.randlist();
        model.addAttribute("randlist", randlist);
        return "randlist.html";
    }

    @PostMapping("/addcollect")
    public String postshopping(HttpSession session,
                               @RequestParam("company_id") Integer company_id) {
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        String user_email = userInfo.getUser_email();
        Integer i = collectMapper.addshopping(user_email, company_id);
        return "redirect:/user/collect";
    }

    /**
     * 用于collect页面功能，主要用于进入收藏页面
     *
     * @param model   调用model传值
     * @param session 设立session值
     * @return 返回collect页面，即收藏页面
     */
    @GetMapping("/collect")
    public String shoppingcarPage(Model model, HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute("user");
        List<CollectInfo> list1 = userMapper.getAllcomputer(userInfo.getUser_email());
        model.addAttribute("user", list1);
        return "collect";
    }

    /**
     * 用于collect页面，主要用于收藏页面的删除
     *
     * @param c_id 收藏单信息id
     * @return 返回collect页面，即收藏页面
     */
    @GetMapping("/dell/{id}")
    public String delshopping(@PathVariable("id") Integer c_id) {
        Integer i = userMapper.dell(c_id);
        return "redirect:/user/collect";
    }

    @RequestMapping("/advice")
    public String adviceinfoPage(Model model) {
        List<AdviceInfo> list = userMapper.getart();
        List<AdviceInfo> list1 = userMapper.getresume();
        List<AdviceInfo> list2 = userMapper.getmodel();
        model.addAttribute("art", list);
        model.addAttribute("resume", list1);
        model.addAttribute("model", list2);
        return "advice";
    }


}

