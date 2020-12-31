package com.wide.controller;

import com.github.pagehelper.PageInfo;
import com.wide.bean.Blog;
import com.wide.bean.Tag;
import com.wide.service.BlogService;
import com.wide.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{id}")
    public String tags(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       @PathVariable Long id,
                       Model model) {
        if (id == -1) {
            List<Tag> tags = tagService.listTagNoPage();
            List<Blog> blogs = blogService.listAllSubmitBlog(pageNum, pageSize);
            PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs);
            model.addAttribute("tags", tags);
            model.addAttribute("blogs", blogs);
            model.addAttribute("page", blogPageInfo);
            model.addAttribute("activeTagId", id);
            return "tags";
        }
        else{
            List<Tag> tags = tagService.listTagNoPage();
            List<Blog> blogs = blogService.listBlogByTag(pageNum, pageSize, id);
            PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs);
            model.addAttribute("tags", tags);
            model.addAttribute("blogs", blogs);
            model.addAttribute("page", blogPageInfo);
            model.addAttribute("activeTagId", id);
            return "tags";
        }
    }
}
