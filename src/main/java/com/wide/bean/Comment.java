package com.wide.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    private Long id;
    private String nickname;
    private String email;
    private String content;
    private String avatar;
    private Date createTime;
    // 是否是作者的回复
    private boolean adminComment;

    // Blog
    private Long blogId;
    private Blog blog;

    // 有多个子对象
    private List<Comment> replyComments = new ArrayList<>();

    // 有一个亲父对象
    private Long parentCommentId;
    private Comment parentComment;
}
