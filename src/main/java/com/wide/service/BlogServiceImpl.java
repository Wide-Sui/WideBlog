package com.wide.service;

import com.github.pagehelper.PageHelper;
import com.wide.Utils.MarkdownUtils;
import com.wide.Utils.RedisUtil;
import com.wide.bean.Blog;
import com.wide.exception.BizException;
import com.wide.exception.BlogNotExistException;
import com.wide.mapper.BlogMapper;
import com.wide.mapper.BlogTagMapper;
import com.wide.mapper.CommentMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@CacheConfig(cacheNames = "blogCache")
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired(required = false)
    BlogMapper blogMapper;

    @Autowired(required = false)
    BlogTagMapper blogTagMapper;

    @Autowired(required = false)
    CommentMapper commentMapper;

    @Autowired
    RedisUtil redisUtil;

//    @Cacheable(value = "blogCache", key = "#id")
    @Override
    public Blog getBlog(Long id) {
        Blog blog = blogMapper.getBlog(id);
        return blog;
    }

    @Override
    public List<Blog> listBlog(int pageNum, int pageSize, String title, Long typeId, boolean recommend) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("typeId", typeId);
        if(recommend == true)
            map.put("recommend", recommend);
        String orderBy = "b.create_time desc";//按照排序字段 倒序 排序
        PageHelper.startPage(pageNum,pageSize,orderBy);
        List<Blog> blogs = blogMapper.listBlog(map);
        return blogs;
    }

    // 这个一般是管理员使用，所以访问次数很少，不必要放redis
    @Override
    public List<Blog> listAllBlog(int pageNum, int pageSize) {
        String orderBy = "create_time desc";//按照排序字段 倒序 排序
        PageHelper.startPage(pageNum,pageSize,orderBy);
        List<Blog> allBlogs = blogMapper.listAllBlog();
        return allBlogs;
    }

    @Override
    public List<Blog> listRecommendBlogTop(int num) {
        List<Blog> blogs = blogMapper.listRecommendBlogTop(num);
        return blogs;
    }

    // 没必要放入redis
    @Override
    public List<Blog> listQueryBlog(int pageNum, int pageSize, String query) {
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> blogs = blogMapper.listQueryBlog(query);
        return blogs;
    }

    @Override
    public List<Blog> listBlogByType(int pageNum, int pageSize, Long typeId) {
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> blogs = blogMapper.listBlogByType(typeId);
        return blogs;
    }

    @Override
    public List<Blog> listBlogByTag(int pageNum, int pageSize, Long tagId) {
        PageHelper.startPage(pageNum, pageSize);
        List<Blog> blogs = blogMapper.listBlogByTag(tagId);
        return blogs;
    }

//    @Cacheable(value = "AllSubmitBlog", key = "'allSubmitBlog'")
    @Override
    public List<Blog> listAllSubmitBlog(int pageNum, int pageSize) {

//        使用pagehelper
//        PageHelper.startPage(pageNum, pageSize);
//        List<Blog> blogs = blogMapper.listAllSubmitBlog();
//        redisUtil.addAllSubmitBlog("allSubmitBlog", blogs);

        // 使用自己定义的redisutil
        List<Blog> blogs = redisUtil.getAllSubmitBlog("allSubmitBlog");
        if(blogs.size() != 0)
            return blogs;
        else {
            blogs = blogMapper.listAllSubmitBlog();
            redisUtil.addAllSubmitBlog("AllSubmitBlog", blogs);
            return blogs;
        }
    }

//    @Cacheable(value = "PartSubmitBlog", key = "#start+'+'+#length")
    @Override
    public List<Blog> listPartSubmitBlog(int start, int length) {
//        List<Blog> blogs = blogMapper.listPartSubmitBlog((start-1)*length, length);
        List<Blog> blogs = redisUtil.getPartSubmitBlog("AllSubmitBlog", (start - 1) * length, start * length - 1);
        return blogs;
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogMapper.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year, blogMapper.findByYear(year));
        }
        return map;
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        // 存blog到db
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        blogMapper.saveBlog(blog);

        // 存blog到redis
        redisUtil.addNewBlog("AllSubmitBlog", blog);

        // 存blog和tag对应关系
         String[] split = blog.getTagIds().split(",");
        for(int i = 0;i < split.length;++i){
            blogTagMapper.saveBlogTag(blog.getId(), Long.parseLong(split[i]));
        }
        return blog;
    }


    @CacheEvict(value = "blogMarkDownCache", beforeInvocation = true, key = "#id")
    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        redisUtil.delRedis("AllSubmitBlog");
        blog.setUpdateTime(new Date());
        blogMapper.updateBlog(id, blog);
        // 删除blogTag表中数据
        blogTagMapper.deleteOneBlog(id);
        // 插入blogTag表中新数据
        String[] split = blog.getTagIds().split(",");
        for(int i = 0;i < split.length;++i){
            blogTagMapper.saveBlogTag(id, Long.parseLong(split[i]));
        }
        return blog;
    }


    @CacheEvict(value = "blogMarkDownCache", beforeInvocation = true, key = "#id")
    @Transactional
    @Override
    public void deleteBlog(Long id) {
        redisUtil.delRedis("AllSubmitBlog");
        blogTagMapper.deleteOneBlog(id);
        commentMapper.deleteCommentsByBlogId(id);
        blogMapper.deleteBlog(id);
    }

    @Cacheable(value = "blogMarkDownCache", key = "#id")
    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogMapper.getBlog(id);
        if (blog == null) {
            throw new BizException("404", "博客走丢，正在寻找");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return b;
    }

    @Transactional
    @Override
    public void updateBlogViews(Long id)
    {
        // 有了缓存之后就不能写在更新函数里面了
        blogMapper.updateViews(id);
    }

    @Override
    public Long countBlog() {
        Long aLong = blogMapper.countBlog();
        return aLong;
    }
}
