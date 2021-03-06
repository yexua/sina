package com.news.sina.service.imp;


import com.news.sina.dao.*;
import com.news.sina.pojo.Blog;
import com.news.sina.pojo.BlogDetail;
import com.news.sina.pojo.User;
import com.news.sina.service.BlogService;
import com.news.sina.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BlogServiceImp implements BlogService {
    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BlogCacheDao blogCacheDao;

    @Autowired
    private CacheHitDao cacheHitDao;

    @Autowired
    private UserTimeLineDao userTimeLineDao;

    @Autowired
    private UserRelationDao userRelationDao;

    @Override
    public List<BlogDetail> getAllBlog() {
        return null;
    }

    @Override
    public List<BlogDetail> getAllBlogOfHome(int userId) {

        Set<Integer> users = userRelationDao.getFollowings(userId);
        users.add(userId);
        List<Integer> blogs = userTimeLineDao.get(userId, users);
        List<Blog> blogIdList = new ArrayList<>();
        for (int b : blogs) {
            Blog blog = new Blog();
            blog.setBlogid(b);
            blogIdList.add(blog);
        }
        return convert(blogIdList);
    }

    @Override
    public List<BlogDetail> getAllBlogByUserId(int userId) {
        List<BlogDetail> list = convert(blogMapper.selectByUserId(userId));
        Collections.reverse(list);
        return list;
    }

    private List<BlogDetail> convert(List<Blog> blogIdList) {
        List<BlogDetail> blogDetailList = new ArrayList<>();
        BlogDetail blogDetail;
        for (Blog blogId : blogIdList) {
            Blog blog = getBlogByBlogId(blogId.getBlogid());
            blogDetail = new BlogDetail();
            blogDetail.setBlogid(blog.getBlogid());
            blogDetail.setContent(blog.getContent());
            blogDetail.setPublishtime(DateUtil.formatDate(blog.getPublishtime()));
            blogDetail.setUserid(blog.getUserid());
            User user = userMapper.selectByPrimaryKey(blog.getUserid());
            blogDetail.setUsername(user.getUsername());
            blogDetail.setHeadpic(user.getHeadpic());
            blogDetailList.add(blogDetail);
        }
        return blogDetailList;
    }

    @Override
    public Blog getBlogByBlogId(int blogId) {
        Blog blog = blogCacheDao.getBlog(blogId);
        if (blog != null) {
            cacheHitDao.hit();           /* 缓存命中 */
        } else {
            blog = blogMapper.selectByPrimaryKey(blogId);
            cacheHitDao.miss();          /* 缓存未命中 */
            blogCacheDao.addBlog(blog);  /* 放入缓存 */
        }
        return blog;
    }

    @Override
    public void addBlog(int userId, String content) {
        Blog blog = new Blog();
        blog.setUserid(userId);
        blog.setContent(content);
        blog.setPublishtime(new Date());
        blogMapper.insert(blog);
        blogCacheDao.addBlog(blog);
        userTimeLineDao.add(userId, blog.getBlogid());
    }

    @Override
    public void editBlog(int blogId, String content) {
        Blog blog = getBlogByBlogId(blogId);
        blog.setContent(content);
        blogMapper.updateByPrimaryKeyWithBLOBs(blog);
    }

    @Override
    public void deleteBlog(int userId, int blogId) {
        blogMapper.deleteByPrimaryKey(blogId);
        userTimeLineDao.delete(userId, blogId);
    }
}
