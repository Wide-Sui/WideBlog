package com.wide.controller;

import com.github.pagehelper.PageInfo;
import com.wide.Utils.PageHelperUtil;
import com.wide.bean.Blog;
import com.wide.bean.Tag;
import com.wide.bean.Type;
import com.wide.service.BlogService;
import com.wide.service.TagService;
import com.wide.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private PageHelperUtil pageHelperUtil;

    @GetMapping({"/", "/index"})
    public String index(@RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize,
                        Model model) {
        //使用pageHelper
//        List<Blog> blogs = blogService.listAllSubmitBlog(pageNum, pageSize);
//        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs);
//        model.addAttribute("blogs", blogs);
//        model.addAttribute("page", blogPageInfo);

        PageInfo blogPageInfo = pageHelperUtil.allSubmitBlogPageInfo(pageNum, pageSize);
        List<Blog> blogs = pageHelperUtil.allSubmitBlogList(pageNum, pageSize);
        model.addAttribute("blogs", blogs);
        model.addAttribute("page", blogPageInfo);

        List<Type> types = typeService.listTypeTop(6);
        model.addAttribute("types", types);

        List<Tag> tags = tagService.listTagTop(10);
        model.addAttribute("tags", tags);
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        return "index";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        blogService.updateBlogViews(id);
        Blog blog = blogService.getAndConvert(id);
        model.addAttribute("blog", blog);
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        return "blog";
    }

    @PostMapping("/search")
    public String search(@RequestParam(defaultValue = "1") Integer pageNum,
                         @RequestParam(defaultValue = "10") Integer pageSize,
                         @RequestParam String query,
                         Model model) {
        List<Blog> blogs = blogService.listQueryBlog(pageNum, pageSize, query);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs);
        model.addAttribute("blogs", blogs);
        model.addAttribute("page", blogPageInfo);
        model.addAttribute("query", query);
        return "search";
    }
}
