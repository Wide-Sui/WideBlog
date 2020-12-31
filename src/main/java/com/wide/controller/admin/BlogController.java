package com.wide.controller.admin;

import com.github.pagehelper.PageInfo;
import com.wide.bean.Blog;
import com.wide.bean.Type;
import com.wide.bean.User;
import com.wide.service.BlogService;
import com.wide.service.TagService;
import com.wide.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blogs-input";
    private static final String REDIRECT_ALLLIST = "redirect:/admin/allBlogs";

    @Autowired
    BlogService blogService;
    @Autowired
    TypeService typeService;
    @Autowired
    TagService tagService;

    @GetMapping("/blogs")
    public String list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       @RequestParam(required = false) String title,
                       @RequestParam(required = false) Long typeId,
                       @RequestParam(required = false) boolean recommend,
                       Model model){
        List<Blog> blogs = blogService.listBlog(pageNum, pageSize, title, typeId, recommend);
        System.out.println("这是blogs" + title + ";" + typeId + ";" + recommend);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs);
        model.addAttribute("blogs", blogs);
        model.addAttribute("page", blogPageInfo);
        model.addAttribute("numOfBlogs", "search");
        model.addAttribute("types", typeService.listTypeNoPage());
        return "admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String searchlist(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       @RequestParam(required = false) String title,
                       @RequestParam(required = false) Long typeId,
                       @RequestParam(required = false) boolean recommend,
                       Model model){
        List<Blog> blogs = blogService.listBlog(pageNum, pageSize, title, typeId, recommend);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs);
        model.addAttribute("blogs", blogs);
        model.addAttribute("page", blogPageInfo);
        model.addAttribute("numOfBlogs", "search");
        return "admin/blogs :: blogList";
    }

    @GetMapping("/allBlogs")
    public String listAll(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          Model model){
        List<Blog> blogs = blogService.listAllBlog(pageNum, pageSize);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs);
        model.addAttribute("blogs", blogs);
        model.addAttribute("page", blogPageInfo);
        model.addAttribute("numOfBlogs", "all");
        model.addAttribute("types", typeService.listTypeNoPage());

        return "admin/blogs";
    }

    @PostMapping("/allBlogs/cp")
    public String listAllCp(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          Model model){
        List<Blog> blogs = blogService.listAllBlog(pageNum, pageSize);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs);
        model.addAttribute("blogs", blogs);
        model.addAttribute("page", blogPageInfo);
        model.addAttribute("numOfBlogs", "all");
        return "admin/blogs :: blogList";
    }

    /*----新增博客--------*/
    private void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.listTypeNoPage());
        model.addAttribute("tags", tagService.listTagNoPage());

    }

    @GetMapping("/blogs/input")
    public String input(Model model) {
        setTypeAndTag(model);
        model.addAttribute("blog", new Blog());
        return INPUT;
    }

    /*-----修改博客-----*/
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        model.addAttribute("blog",blog);
        return INPUT;
    }

    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {
        User user = (User) session.getAttribute("user");
        blog.setUserId(user.getId());
//        blog.setType(typeService.getType(blog.getType().getId()));
//        blog.setTags(tagService.listTag(blog.getTagIds()));
//        System.out.println("flag:" + blog.getFlag());
        Blog b;

        System.out.println(blog.toString());

        if (blog.getId() == null) {
            b =  blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }

        if (b == null ) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return REDIRECT_ALLLIST;
    }

    /*-------删除博客--------*/
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return REDIRECT_ALLLIST;
    }



}
