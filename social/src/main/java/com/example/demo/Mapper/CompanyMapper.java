package com.example.demo.Mapper;

import com.example.demo.Entity.CompanyInfo;
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
}
