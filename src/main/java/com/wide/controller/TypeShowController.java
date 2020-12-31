package com.wide.controller;

import com.github.pagehelper.PageInfo;
import com.wide.bean.Blog;
import com.wide.bean.Type;
import com.wide.service.BlogService;
import com.wide.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize,
                        @PathVariable Long id,
                        Model model) {
        List<Type> types = typeService.listTypeNoPage();
//        System.out.println("types: " + types);
        if (id == -1) {
            List<Blog> blogs = blogService.listAllSubmitBlog(pageNum, pageSize);
            PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs);
            model.addAttribute("types", types);
            model.addAttribute("blogs", blogs);
            model.addAttribute("page", blogPageInfo);
            model.addAttribute("activeTypeId", -1);
            return "types";
        }
        else
        {
            List<Blog> blogs = blogService.listBlogByType(pageNum, pageSize, id);
//            System.out.println("blogs:::: " + blogs);
            PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs);
            model.addAttribute("types", types);
            model.addAttribute("blogs", blogs);
            model.addAttribute("page", blogPageInfo);
            model.addAttribute("activeTypeId", id);
            return "types";
        }
    }

}
