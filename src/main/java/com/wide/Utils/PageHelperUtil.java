package com.wide.Utils;

import com.github.pagehelper.PageInfo;
import com.wide.bean.Blog;
import com.wide.mapper.BlogMapper;
import com.wide.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PageHelperUtil {

    @Autowired(required = false)
    BlogMapper blogMapper;

    @Autowired
    BlogService blogService;

    public PageInfo allSubmitBlogPageInfo(int pageNum, int pageSize) {
        List<Blog> pageList = blogService.listAllSubmitBlog(pageNum, pageSize);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setTotal(pageList.size()); // 总量
        pageInfo.setSize(pageSize); // 每页条数
        pageInfo.setPrePage(pageNum - 1 == 0 ? 1 : pageNum - 1); // 当前页
        int pageNums = pageList.size() % pageSize == 0 ? pageList.size() / pageSize : Double.valueOf(pageList.size() / pageSize).intValue() + 1; // 总页数
        pageInfo.setPageNum(pageNum); // 当前页
        pageInfo.setPages(pageNums); // 总页数
        pageInfo.setNextPage(pageNums == pageNum ? pageNums : pageNum + 1); // 下一页
        return pageInfo;
    }


    public List<Blog> allSubmitBlogList(int pageNum, int pageSize) {
        List<Blog> list = blogService.listPartSubmitBlog(pageNum, pageSize); // 当前页面显示条数 从pageSize * (pageNum - 1)条到 (pageSize * pageNum ) - 1条
        return list;
    }

}
