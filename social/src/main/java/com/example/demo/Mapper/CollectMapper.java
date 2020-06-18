package com.example.demo.Mapper;
import com.example.demo.Entity.CollectInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface CollectMapper {
    /**
     *
     * @param user_email 用户邮箱
     * @param company_id 职位编号
     *
     */
    @Insert( "INSERT INTO collect" +
            "(user_email, company_id) " +
            "VALUES( #{user_email}, #{company_id})" )
    Integer addshopping(String user_email,Integer company_id);




}
