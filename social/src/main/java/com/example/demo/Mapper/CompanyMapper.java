package com.example.demo.Mapper;

import com.example.demo.Entity.CompanyInfo;
import com.example.demo.Entity.MessageInfo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CompanyMapper {
    //得到所有公司信息
    @Select("select * from company")
    List<CompanyInfo> getAllCompany();


    //分析报告页 十大热门行业发布的职位总数图表 --行业
    @Select("select company_industry from company \n" +
            "group by company_industry\n" +
            "order by count(company_industry) desc\n" +
            "limit 10")
    List<String> gettop10indu();
    @Select("select count(company_industry) from company \n" +
            "group by company_industry\n" +
            "order by count(company_industry) desc\n" +
            "limit 10")
    List<Integer> gettop10num();

    //公司详情页面信息展现
    @Select("select com.*,rec.salary,rec.job_experience,rec.education_required,\n" +
            "rec.recruiting_numbers,rec.postdate,\n" +
            "rec.welfare,rec.job_information,rec.job_id\n" +
            "from recruitment rec join company com on com.company_id=rec.company_id\n" +
            "where com.company_id=#{company_id}")
    List<CompanyInfo> companyDetail(Integer company_id);

    @Select("select com.*,rec.salary,rec.job_experience,\n" +
            "rec.education_required,rec.recruiting_numbers,rec.postdate,\n" +
            "rec.welfare,rec.job_information,rec.job_id\n" +
            "from recruitment rec join company com on com.company_id=rec.company_id\n" +
            "order by rand() limit 10;")
    List<CompanyInfo> randlist();



}
