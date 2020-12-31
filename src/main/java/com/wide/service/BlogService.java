package com.wide.service;

import com.wide.bean.Blog;

import java.util.List;
import java.util.Map;

public interface BlogService {

    Blog getBlog(Long id);

    List<Blog> listBlog(int pageNum, int pageSize, String title, Long typeId, boolean recommend);

    List<Blog> listAllBlog(int pageNum, int pageSize);

    List<Blog> listRecommendBlogTop(int num);

    List<Blog> listQueryBlog(int pageNum, int pageSize, String query);

    List<Blog> listBlogByType(int pageNum, int pageSize, Long typeId);

    List<Blog> listBlogByTag(int pageNum, int pageSize, Long tagId);

    List<Blog> listAllSubmitBlog(int pageNum, int pageSize);

    List<Blog> listPartSubmitBlog(int start, int length);

    Map<String, List<Blog>> archiveBlog();

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);

    Blog getAndConvert(Long id);

    void updateBlogViews(Long id);

    Long countBlog();
}
