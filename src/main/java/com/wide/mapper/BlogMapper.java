package com.wide.mapper;

import com.wide.bean.Blog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BlogMapper {

    public Blog getBlog(Long id);

    public List<Blog> listBlog(Map map);

    public List<Blog> listAllBlog();

    public List<Blog> listAllSubmitBlog();

    public List<Blog> listPartSubmitBlog(int start, int length);

    public List<Blog> listRecommendBlogTop(int num);

    public List<Blog> listQueryBlog(String query);

    public List<Blog> listBlogByType(Long typeId);

    public List<Blog> listBlogByTag(Long tagId);

    public long saveBlog(@Param("blog") Blog blog);

    public int updateBlog(@Param("id") Long id, @Param("blog") Blog blog);

    public void deleteBlog(Long id);

    // 更新浏览次数
    public int updateViews(Long id);

    // 归档
    public List<String> findGroupYear();
    public List<Blog> findByYear(String year);

    // 计数
    public Long countBlog();

    // 删除类型时对应的blog类型要改变不然就会出现找不到该类型的错误
    public void deleteBlogType(Long typeId);
}
