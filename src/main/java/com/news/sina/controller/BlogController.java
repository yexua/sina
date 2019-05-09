package com.news.sina.controller;

import com.news.sina.pojo.User;
import com.news.sina.service.imp.BlogServiceImp;
import com.news.sina.util.SessionUtil;
import com.news.sina.util.XSSFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BlogController {

    @Autowired
    HttpServletRequest request;

    @Autowired
    BlogServiceImp blogServiceImp;

    @RequestMapping(value = "/publishBlog.html")
    public ModelAndView publishBlog(String blogContent) {
        User user = SessionUtil.getUserSession(request);
        int len = blogContent.length();
        final int MAX_LEN = 1000;
        if (len > 0 && len < MAX_LEN && user != null) {
            blogContent = XSSFilter.filterBrackets(blogContent);
            blogServiceImp.addBlog(user.getUserid(), blogContent);
        }
        return new ModelAndView("redirect:/index.html");
    }

    @RequestMapping(value = "/deleteBlog.html")
    @ResponseBody
    public String deleteBlog(Integer blogId) {
        User user = SessionUtil.getUserSession(request);
        if (user == null) return "error";
        blogServiceImp.deleteBlog(user.getUserid(), blogId);
        return "success";
    }
}
