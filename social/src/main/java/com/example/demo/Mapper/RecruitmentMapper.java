package com.example.demo.Mapper;

import com.example.demo.Entity.RecruitmentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;




import java.util.List;
@Mapper
public interface RecruitmentMapper {
    //首页职位城市行业类型查询框查询post
    @Select("select rec.*,com.company_name,com.company_location,com.company_industry," +
            "com.company_city,com.detail_information " +
            "from company com join recruitment rec on com.company_id=rec.company_id")
    List<RecruitmentInfo> SelectAll();

    @Select("select rec.*,com.company_name,com.company_location,com.company_industry," +
            "com.company_city,com.detail_information \n" +
            "from company com join recruitment rec on com.company_id=rec.company_id\n" +
            "where rec.job_name like #{job_name}")
    List<RecruitmentInfo> selectjob(String job_name);

    @Select("select rec.*,com.company_name,com.company_link,com.company_address," +
            "com.company_location,com.company_industry,com.company_city,com.detail_information\n" +
            "from company com join recruitment rec on com.company_id=rec.company_id\n" +
            "where com.company_industry like #{company_industry}")
    List<RecruitmentInfo> selectindu(String company_industry);

    @Select("select rec.*,com.company_name,com.company_link,com.company_address," +
            "com.company_location,com.company_industry,com.company_city,com.detail_information\n" +
            "from company com join recruitment rec on com.company_id=rec.company_id\n" +
            "where company_industry like #{company_industry}  and job_name like #{job_name}\n")
    List<RecruitmentInfo> selectinduJob(
            @Param("company_industry") String company_industry,
            @Param("job_name") String job_name);


    //首页热门类别查询
    @Select("select rec.*,com.company_name,com.company_link,com.company_address,com.company_location,com.company_industry,com.company_city,com.detail_information\n" +
        "from company com join recruitment rec on com.company_id=rec.company_id\n" +
        "where com.company_industry like #{company_industry} order by salary desc")
    List<RecruitmentInfo> seindu(String company_industry);


    //公司职位列表分页查询
    //无条件查询
    @Select("select rec.*,com.company_name,com.company_location,com.company_industry,com.company_city,com.detail_information from company com join recruitment rec on com.company_id=rec.company_id")
    List<RecruitmentInfo> userSelectAll(
            Integer pageIndex,
            Integer pageSize);

    //根据职位名查询
    @Select("select rec.*,com.company_name,com.company_location,com.company_industry,com.company_city,com.detail_information \n" +
            "from company com join recruitment rec on com.company_id=rec.company_id\n" +
            "where rec.job_name like #{job_name}")
    List<RecruitmentInfo> userselectjob(
            @Param("job_name") String job_name,
            @Param("pageIndex") Integer pageIndex,
            @Param("pageSize") Integer pageSize);

    //根据公司所在行业查询
    @Select("select rec.*,com.company_name,com.company_link,com.company_address,com.company_location,com.company_industry,com.company_city,com.detail_information\n" +
            "from company com join recruitment rec on com.company_id=rec.company_id\n" +
            "where com.company_industry like #{company_industry}")
    List<RecruitmentInfo> userselectindu(
            @Param("company_industry")  String company_industry,
            @Param("pageIndex") Integer pageIndex,
            @Param("pageSize") Integer pageSize);

    //行业和职位名模糊查询
    @Select("select rec.*,com.company_name,com.company_link,com.company_address,com.company_location,com.company_industry,com.company_city,com.detail_information\n" +
            "from company com join recruitment rec on com.company_id=rec.company_id\n" +
            "where company_industry like #{company_industry}and job_name like #{job_name}\n")
    List<RecruitmentInfo> userselectinduJob(
    @Param("company_industry") String company_industry,
    @Param("job_name") String job_name,
    @Param("pageIndex") Integer pageIndex,
    @Param("pageSize") Integer pageSize);

    //报告图表分析
    //公司所在行业的热门职位发布数量 --行业
    @Select("select rec.job_name from company com join recruitment rec on com.company_id=rec.company_id\n" +
            "where company_industry like #{company_industry}" +
            "group by rec.job_name")
    List<String> getjobname(String company_industry);

    @Select("select count(rec.job_name) from company com join recruitment rec on com.company_id=rec.company_id\n" +
            "where company_industry like #{company_industry}" +
            "group by rec.job_name")
    List<Integer> getjobnumber(String company_industry);

    //获取职位列表选中的职位名
    @Select("select job_name from recruitment where job_name like #{job_name} limit 1")
    List<String>  getname(String job_name);


    //表格 获取公司列表选中的公司所在行业的所有职位按工资排序的的top1信息  --行业
    @Select("select * from company com join recruitment rec on com.company_id=rec.company_id\n" +
            "where company_industry like #{company_industry}" +
            "group by rec.job_name\n" +
            "order by salary desc")
    List<RecruitmentInfo>  industry(String company_industry );

    //表格 获取职位列表选中的职位名按工资排序的top10信息  --职位
    @Select("select rec.job_name,company_name,salary,company_link,education_required,company_city,company_location,company_type,postdate from company com join recruitment rec on com.company_id=rec.company_id\n" +
            "where rec.job_name like #{job_name} " +
            "order by salary desc\n" +
            "limit 10")
    List<RecruitmentInfo>  jobname(String job_name);


    //公司对应的职位在不同城市的发布数量  --职位
    @Select("select company_city from company com join recruitment rec on com.company_id=rec.company_id\n" +
            "where com.job_name like #{job_name}" +
            "group by company_city")
    List<String> getjobcity(String job_name);
    @Select("select count(rec.job_name) from company com join recruitment rec on com.company_id=rec.company_id\n" +
            "where com.job_name like #{job_name}" +
            "group by company_city")
    List<Integer> getjnumber(String job_name);

    //获取近七天排名前六的职位的发布数量  --职位
    @Select("select job_name from recruitment group by job_name order by count(job_name) desc limit 6")
    List<String> topjobname();
    @Select("select postdate from recruitment group by postdate")
    List<String> postdate();


    //学历要求  --职位
    @Select("select count(job_id) from recruitment\n" +
            "where job_name like  #{job_name} and education_required in ('高中')\n" +
            "group by education_required")
    List<String> getedunum1(String job_name);
    @Select("select count(job_id) from recruitment\n" +
            "where job_name like #{job_name} and education_required in ('中专')\n" +
            "group by education_required")
    List<String> getedunum2(String job_name);
    @Select("select count(job_id) from recruitment\n" +
            "where job_name like #{job_name} and education_required in ('大专')\n" +
            "group by education_required")
    List<String> getedunum3(String job_name);
    @Select("select count(job_id) from recruitment\n" +
            "where job_name like #{job_name} and education_required in ('本科')\n" +
            "group by education_required")
    List<String> getedunum4(String job_name);
    @Select("select count(job_id) from recruitment\n" +
            "where job_name like #{job_name} and education_required in ('硕士')\n" +
            "group by education_required")
    List<String> getedunum5(String job_name);
    @Select("select count(job_id) from recruitment\n" +
            "where job_name like #{job_name} and education_required in ('博士')\n" +
            "group by education_required")
    List<String> getedunum6(String job_name);
    @Select("select count(job_id) from recruitment\n" +
            "where job_name like #{job_name} and education_required not in ('高中','大专','中专','本科','硕士','博士')\n" +
            "group by education_required")
    List<String> getedunum7(String job_name);
    @Select("select education_required from recruitment\n" +
            "where job_name like #{job_name}" +
            "group by education_required\n")
    List<String> getedu(String job_name);


    //经验要求  --职位
    @Select("select job_experience from recruitment\n" +
            "where job_name like #{job_name}" +
            "group by job_experience")
    List<String> getexp(String job_name);
    @Select("select count(job_id) from recruitment\n" +
            "where job_name like #{job_name} and job_experience in ('1年经验')\n" +
            "group by job_experience")
    List<String> getexpnum1(String job_name);
    @Select("select count(job_id) from recruitment\n" +
            "where job_name like #{job_name} and job_experience in ('2年经验')\n" +
            "group by job_experience")
    List<String> getexpnum2(String job_name);
    @Select("select count(job_id) from recruitment\n" +
            "where job_name like #{job_name} and job_experience in ('3-4年经验')\n" +
            "group by job_experience")
    List<String> getexpnum3(String job_name);
    @Select("select count(job_id) from recruitment\n" +
            "where job_name like #{job_name} and job_experience in ('5-7年经验')\n" +
            "group by job_experience")
    List<String> getexpnum4(String job_name);
    @Select("select count(job_id) from recruitment\n" +
            "where job_name like #{job_name} and job_experience in ('8-9年经验')\n" +
            "group by job_experience")
    List<String> getexpnum5(String job_name);
    @Select("select count(job_id) from recruitment\n" +
            "where job_name like #{job_name}and job_experience in ('10年以上经验')\n" +
            "group by job_experience")
    List<String> getexpnum6(String job_name);
    @Select("select count(job_id) from recruitment\n" +
            "where job_name like #{job_name} and job_experience in ('无工作经验')\n" +
            "group by job_experience")
    List<String> getexpnum7(String job_name);

    @Select("select count(job_id) from recruitment\n" +
            "where job_name like #{job_name} and job_experience not in ('无工作经验','10年以上经验','8-9年经验','5-7年经验','3-4年经验','2年经验','1年经验')\n" +
            "group by job_experience")
    List<String> getexpnum8(String job_name);

    //近七天部分职位发布数量变动趋势  --职位
    @Select("select count(job_id) from recruitment where job_name like (\"%记者%\") and postdate='2019-12-12'")
   Integer kf1();
    @Select("select count(job_id) from recruitment where job_name like (\"%记者%\") and postdate='2019-12-13'")
    Integer kf2();
    @Select("select count(job_id) from recruitment where job_name like (\"%记者%\") and postdate='2019-12-14'")
    Integer kf3();
    @Select("select count(job_id) from recruitment where job_name like (\"%记者%\") and postdate='2019-12-15'")
    Integer kf4();
    @Select("select count(job_id) from recruitment where job_name like (\"%记者%\") and postdate='2019-12-16'")
    Integer kf5();
    @Select("select count(job_id) from recruitment where job_name like (\"%记者%\") and postdate='2019-12-17'")
    Integer kf6();
    @Select("select count(job_id) from recruitment where job_name like (\"%记者%\") and postdate='2019-12-18'")
    Integer kf7();

    @Select("select count(job_id) from recruitment where job_name like (\"%销售经理%\") and postdate='2019-12-12'\n")
    Integer xs1();
    @Select("select count(job_id) from recruitment where job_name like (\"%销售经理%\") and postdate='2019-12-13'\n")
    Integer xs2();
    @Select("select count(job_id) from recruitment where job_name like (\"%销售经理%\") and postdate='2019-12-14'\n")
    Integer xs3();
    @Select("select count(job_id) from recruitment where job_name like (\"%销售经理%\") and postdate='2019-12-15'\n")
    Integer xs4();
    @Select("select count(job_id) from recruitment where job_name like (\"%销售经理%\") and postdate='2019-12-16'\n")
    Integer xs5();
    @Select("select count(job_id) from recruitment where job_name like (\"%销售经理%\") and postdate='2019-12-17'\n")
    Integer xs6();
    @Select("select count(job_id) from recruitment where job_name like (\"%销售经理%\") and postdate='2019-12-18'\n")
    Integer xs7();

    @Select("select count(job_id) from recruitment where job_name like (\"%微信运营%\") and postdate='2019-12-12'\n")
    Integer sj1();
    @Select("select count(job_id) from recruitment where job_name like (\"%微信运营%\") and postdate='2019-12-13'\n")
    Integer sj2();
    @Select("select count(job_id) from recruitment where job_name like (\"%微信运营%\") and postdate='2019-12-14'\n")
    Integer sj3();
    @Select("select count(job_id) from recruitment where job_name like (\"%微信运营%\") and postdate='2019-12-15'\n")
    Integer sj4();
    @Select("select count(job_id) from recruitment where job_name like (\"%微信运营%\") and postdate='2019-12-16'\n")
    Integer sj5();
    @Select("select count(job_id) from recruitment where job_name like (\"%微信运营%\") and postdate='2019-12-17'\n")
    Integer sj6();
    @Select("select count(job_id) from recruitment where job_name like (\"%微信运营%\") and postdate='2019-12-18'\n")
    Integer sj7();

    @Select("select count(job_id) from recruitment where job_name like (\"%电话客服%\") and postdate='2019-12-12'\n")
    Integer ui1();
    @Select("select count(job_id) from recruitment where job_name like (\"%电话客服%\") and postdate='2019-12-13'\n")
    Integer ui2();
    @Select("select count(job_id) from recruitment where job_name like (\"%电话客服%\") and postdate='2019-12-14'\n")
    Integer ui3();
    @Select("select count(job_id) from recruitment where job_name like (\"%电话客服%\") and postdate='2019-12-15'\n")
    Integer ui4();
    @Select("select count(job_id) from recruitment where job_name like (\"%电话客服%\") and postdate='2019-12-16'\n")
    Integer ui5();
    @Select("select count(job_id) from recruitment where job_name like (\"%电话客服%\") and postdate='2019-12-17'\n")
    Integer ui6();
    @Select("select count(job_id) from recruitment where job_name like (\"%电话客服%\") and postdate='2019-12-18'\n")
    Integer ui7();

    @Select("select count(job_id) from recruitment where job_name like (\"%新媒体运营%\") and postdate='2019-12-12'\n")
   Integer soft1();
    @Select("select count(job_id) from recruitment where job_name like (\"%新媒体运营%\") and postdate='2019-12-13'\n")
    Integer soft2();
    @Select("select count(job_id) from recruitment where job_name like (\"%新媒体运营%\") and postdate='2019-12-14'\n")
    Integer soft3();
    @Select("select count(job_id) from recruitment where job_name like (\"%新媒体运营%\") and postdate='2019-12-15'\n")
    Integer soft4();
    @Select("select count(job_id) from recruitment where job_name like (\"%新媒体运营%\") and postdate='2019-12-16'\n")
    Integer soft5();
    @Select("select count(job_id) from recruitment where job_name like (\"%新媒体运营%\") and postdate='2019-12-17'\n")
    Integer soft6();
    @Select("select count(job_id) from recruitment where job_name like (\"%新媒体运营%\") and postdate='2019-12-18'\n")
    Integer soft7();

    @Select("select count(job_id) from recruitment where job_name like (\"%市场营销%\") and postdate='2019-12-12'\n")
    Integer java1();
    @Select("select count(job_id) from recruitment where job_name like (\"%市场营销%\") and postdate='2019-12-13'\n")
    Integer java2();
    @Select("select count(job_id) from recruitment where job_name like (\"%市场营销%\") and postdate='2019-12-14'\n")
    Integer java3();
    @Select("select count(job_id) from recruitment where job_name like (\"%市场营销%\") and postdate='2019-12-15'\n")
    Integer java4();
    @Select("select count(job_id) from recruitment where job_name like (\"%市场营销%\") and postdate='2019-12-16'\n")
    Integer java5();
    @Select("select count(job_id) from recruitment where job_name like (\"%市场营销%\") and postdate='2019-12-17'\n")
    Integer java6();
    @Select("select count(job_id) from recruitment where job_name like (\"%市场营销%\") and postdate='2019-12-18'\n")
    Integer java7();



    //地区分布职位发布数
    @Select("SELECT count(company_id)  from social_work.company where company_city='上海'")
    Integer dq1();
    @Select("SELECT count(company_id)  from social_work.company where company_city='北京'")
    Integer dq2();
    @Select("SELECT count(company_id)  from social_work.company where company_city='广州'")
    Integer dq3();
    @Select("SELECT count(company_id)  from social_work.company where company_city='杭州'")
    Integer dq4();
    @Select("SELECT count(company_id)  from social_work.company where company_city='武汉'")
    Integer dq5();
    @Select("SELECT count(company_id)  from social_work.company where company_city='深圳'")
    Integer dq6();
    @Select("SELECT count(company_id)  from social_work.company where company_city='重庆'")
    Integer dq7();
}
