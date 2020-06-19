package com.example.demo.Controller;


import com.example.demo.Entity.BlogCommentInfo;
import com.example.demo.Entity.BlogInfo;
import com.example.demo.Entity.MessageInfo;
import com.example.demo.Mapper.BlogMapper;
import com.example.demo.Util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("blog")
public class blogController {
    @Autowired
    private BlogMapper blogMapper;

    @GetMapping("/add")
    public String addblogPage() {
        return "addblog";
    }

    @PostMapping("/add")
    public String addblog(BlogInfo blogInfo,
                          @RequestParam("file") MultipartFile file) {
        String contentType=file.getContentType();
        String fileName=file.getOriginalFilename();
        String filePath= FileUtil.getUpLoadFilePath();
        fileName=System.currentTimeMillis()+fileName;

        try{
            FileUtil.uploadFile(file.getBytes(),filePath,fileName);
        }catch(Exception e){
            e.printStackTrace();
        }
        blogInfo.setBlog_photo(fileName);
        int i = blogMapper.saveBlog(blogInfo);
        return "redirect:/blog/blog";
    }

   @GetMapping("/blog")
    public String getBlogPage(Model model) {
       List<BlogInfo> bloglist=blogMapper.getallblog();
       model.addAttribute("bloglist",bloglist);
        return "blog";
   }

    @GetMapping("/blogdetail/{id}")
    public String getallContent(@PathVariable("id") Integer blog_id, Model model){
        List<BlogInfo> blogdetail=blogMapper.getblogdetail(blog_id);
        model.addAttribute("blog",blogdetail);

        List<BlogCommentInfo> allblogcomment=blogMapper.getAllBlogComment(blog_id);
        model.addAttribute("list",allblogcomment);
        return "single-blog.html";
    }

    @PostMapping("/blogdetail")
    public String getComment( BlogCommentInfo blogCommentInfo) {
        int i=blogMapper.getcomment(blogCommentInfo);
        return "redirect:/blog/blog";
    }
}
