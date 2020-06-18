package com.example.demo.Mapper;

import com.example.demo.Entity.CollectInfo;
import com.example.demo.Entity.CompanyInfo;
import com.example.demo.Entity.MessageInfo;
import com.example.demo.Entity.UserInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserMapper {
    //登录查询语句
    @Select("SELECT * FROM social_work.user where user_email=#{user_email} and user_password=#{user_password}")
    UserInfo userLogin(String user_email,String user_password);
    //注册插入语句
    @Insert("INSERT INTO user(`user_email`,`user_password`,`user_name`)"+
            "VALUES (#{user_email}, #{user_password},#{user_name});")
    Integer saveUserAccount(UserInfo userInfo);


    //修改个人信息
    @Update("UPDATE `social_work`.`user` SET `user_email` = #{user_email}, `user_name` = #{user_name}, `user_phone` =#{user_phone}, `user_sex` = #{user_sex}, `user_birthday` = #{user_birthday}, `user_address` = #{user_address}, `user_education` = #{user_education}, `user_major` =#{user_major}, `user_school` =#{user_school}, `user_expection` =#{user_expection}, `user_experience` = #{user_experience} WHERE (`user_email` = #{user_email});\n")
    Integer updateUserByid(UserInfo userInfo);
    //获取登录用户的个人信息
    @Select("SELECT * FROM social_work.user where `user_email` = #{user_email}")
    UserInfo getUserByid(String user_email);

    @Select("SELECT * FROM social_work.user where `user_email` = #{user_email}")
    List<UserInfo> getalluserinfo(String user_email);

    //发布工作
    //插入recruitment表
    @Insert("INSERT INTO `social_work`.`recruitment` (`job_name`, `job_experience`, `education_required`, `job_information`)" +
            "VALUES (#{job_name},#{job_experience},#{education_required},#{job_information});")
    Integer savePostJob1(CompanyInfo companyInfo);

    //插入company表
    @Insert("INSERT INTO `social_work`.`company` (`company_location`, `detail_information`, `company_type`, `company_industry`, `company_city`) " +
            "VALUES (#{company_location},#{detail_information},#{company_type},#{company_industry},#{company_city}); ")
    Integer savePostJob2(CompanyInfo companyInfo);


    //密码修改功能
    @Update("UPDATE `social_work`.`user` SET `user_password`= #{user_password} WHERE `user_email`= #{user_email}")
    Integer updateUserByid1(UserInfo userInfo);

    @Select("SELECT * FROM social_work.user where `user_email` = #{user_email}")
    UserInfo getUserByid1(String user_email);


    //留言板查看
    @Select("select * from user u join message m on u.user_email=m.user_email order by posttime desc")
    List<MessageInfo> getallmessage();

    @Select("select * from user u join message m on u.user_email=m.user_email where message_id=#{message_id};")
    List<MessageInfo> getmessage(Integer message_id);
    //留言板添加
    @Insert(" INSERT INTO `social_work`.`message` (`content`, `message_id`, `user_email`, `posttime`, `theme`)" +
            " VALUES (#{content}, #{message_id},#{user_email},sysdate(), #{theme});")
    Integer saveMessage(MessageInfo messageInfo);

    @Select( "SELECT company.job_name,company.company_name,company.company_id,company.company_link,company.company_address,company.company_industry,c_id\n" +
            "FROM user join collect join company\n" +
            "on user.user_email=collect.user_email and collect.company_id=company.company_id and user.user_email=#{user_email} ;" )
    List<CollectInfo> getAllcomputer(String user_email);

    @Delete("delete from collect where c_id=#{c_id}")
    Integer dell(Integer c_id);


}
