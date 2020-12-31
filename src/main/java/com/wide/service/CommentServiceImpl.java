package com.wide.service;

import com.wide.bean.Comment;
import com.wide.mapper.CommentMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired(required = false)
    CommentMapper commentMapper;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        List<Comment> comments = commentMapper.listCommentByBlogId(blogId);
        return eachComment(comments);
    }

    @Override
    public Comment getCommentById(Long id) {
        Comment comment = commentMapper.getCommentById(id);
        return comment;
    }

    @Override
    public Comment saveComment(Comment comment) {
        comment.setCreateTime(new Date());
        // 外键约束，可以没有，但是不能插入不存在的值
        if(comment.getParentCommentId() == -1){
            comment.setParentCommentId(null);
        }
        Long aLong = commentMapper.saveComment(comment);
        return comment;
    }

    /**
     * 循环每个顶级的评论节点
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            if(comment.getParentCommentId() == null){
                Comment c = new Comment();
                BeanUtils.copyProperties(comment,c);
                commentsView.add(c);
            }
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);

        return commentsView;
    }

    /**
     *
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComments();
            for(Comment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                Comment commentById = commentMapper.getCommentById(reply1.getId());
                recursively(commentById);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();
    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        tempReplys.add(comment);//顶节点添加到临时存放集合
        if (comment.getReplyComments().size()>0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                Comment commentById = commentMapper.getCommentById(reply.getId());
                tempReplys.add(commentById);
                if (commentById.getReplyComments().size()>0) {
                    recursively(commentById);
                }
            }
        }
    }
}
