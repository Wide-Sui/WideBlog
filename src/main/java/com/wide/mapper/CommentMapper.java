package com.wide.mapper;

import com.wide.bean.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {

    public List<Comment> listCommentByBlogId(Long blogId);

    public Comment getCommentById(Long id);

    public Long saveComment(@Param("comment") Comment comment);

    public void deleteCommentsByBlogId(Long blogId);
}
