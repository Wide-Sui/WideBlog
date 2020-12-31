package com.wide.service;

import com.wide.bean.Comment;
import java.util.List;

public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);

    Comment getCommentById(Long id);
}
