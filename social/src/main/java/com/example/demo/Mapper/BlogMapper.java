package com.example.demo.Mapper;

import com.example.demo.Entity.BlogCommentInfo;
import com.example.demo.Entity.BlogInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogMapper {
    @Insert("INSERT INTO `blog` (`blog_date`, `blog_user_email`, `blog_photo`, `blog_title`, `blog_minitext`, `blog_text`, `blog_tag`) " +
            "VALUES (sysdate(),#{blog_user_email},#{blog_photo},#{blog_title},#{blog_minitext},#{blog_text},#{blog_tag})")
    Integer saveBlog(BlogInfo blogInfo);

    @Select("SELECT * FROM social_work.blog;")
    List<BlogInfo> getallblog();

    @Select("select * from blog where blog_id=#{blog_id}")
    List<BlogInfo> getblogdetail(Integer blog_id);

    @Select("select * from blogcomment where blog_id=#{blog_id}")
    List<BlogCommentInfo> getAllBlogComment(Integer blog_id);

    @Insert("INSERT INTO `social_work`.`blogcomment` (`comment_text`, `comment_date`, `blog_comment_email`, `blog_id`) " +
            "VALUES (#{comment_text},sysdate(),#{blog_comment_email},#{blog_id});\n")
    Integer getcomment(BlogCommentInfo blogCommentInfo);

}
