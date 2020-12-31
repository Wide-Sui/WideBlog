package com.wide.controller;

import com.wide.bean.Comment;
import com.wide.bean.User;
import com.wide.service.BlogService;
import com.wide.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    private BlogService blogService;

//    @Value("${comment.avatar}")
//    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId,
                           Model model) {
        List<Comment> comments = commentService.listCommentByBlogId(blogId);
//        System.out.println("这是跳转：" + comments.toString());
        model.addAttribute("comments", comments);
        model.addAttribute("avatar", blogService.getBlog(blogId).getUser().getAvatar());
        return "blog :: commentList";
    }


    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        Long blogId = comment.getBlogId();
//        comment.setBlog(blogService.getBlog(blogId));
        User user = (User) session.getAttribute("user");
        if (user != null) {
            comment.setAdminComment(true);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }
}
