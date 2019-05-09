package com.news.sina.service;

import com.news.sina.pojo.Blog;
import com.news.sina.pojo.BlogDetail;

import java.util.List;

public interface BlogService {

    List<BlogDetail> getAllBlogOfHome(int userId);

    List<BlogDetail> getAllBlogByUserId(int userId);

    Blog getBlogByBlogId(int id);

    void addBlog(int userId, String content);

    void editBlog(int blogId, String content);

    void deleteBlog(int userId,  int blogId);
}
