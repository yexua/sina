package com.news.sina.controller;

import com.news.sina.pojo.BlogDetail;
import com.news.sina.pojo.User;
import com.news.sina.service.imp.BlogServiceImp;
import com.news.sina.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private BlogServiceImp blogServiceImp;

    @Autowired
    HttpServletRequest request;

    @RequestMapping(value = {"/", "/index.html"})
    public ModelAndView showIndexPage() {
        User user = SessionUtil.getUserSession(request);
        if (user == null) {
            return new ModelAndView("redirect:/login.html");
        }
        ModelAndView modelAndView = new ModelAndView("index");
        List<BlogDetail> allBlog = blogServiceImp.getAllBlogOfHome(user.getUserid());
        //List<BlogDetail> allBlog = blogServiceImp.getAllBlogOfHome(user.getUserid());
        modelAndView.addObject("blogList", allBlog);
        modelAndView.addObject("user", user);
        return modelAndView;
    }
}
