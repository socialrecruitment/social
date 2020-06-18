package com.example.demo.Mapper;

import com.example.demo.Entity.MessageInfo;
import com.example.demo.Entity.PrmessageInfo;
import com.example.demo.Entity.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InformationMapper {
    //查询功能
    @Select("select * from user u join prmessage p on u.user_email=p.user_email order by prposttime desc")
    List<PrmessageInfo> getallprmessage();

    @Select("select * from user u join prmessage m on u.user_email=p.user_email where prmessage_id=#{prmessage_id};")
    List<PrmessageInfo> getprmessage(Integer prmessage_id);
    //留言板添加

    @Insert("INSERT INTO `social_work`.`prmessage` (`prmessage_id`, `prcontent`, `user_email`, `prposttime`) " +
            "VALUES (#{prmessage_id}, #{prcontent}, #{user_email}, #{prposttime});")

    Integer savePrmessage(PrmessageInfo prmessageInfo);
}
