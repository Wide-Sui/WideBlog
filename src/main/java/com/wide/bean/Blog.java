package com.wide.bean;

import com.wide.bean.Comment;
import com.wide.bean.Tag;
import com.wide.bean.Type;
import com.wide.bean.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blog {

    private Long id;
    private String title;
    private String content;
    private String firstPicture;
    // 标记
    private String flag;
    // 浏览次数
    private Integer views;
    // 赞赏是否开启
    private boolean appreciation;
    // 转载声明是否开启
    private boolean shareStatement;
    // 评论是否开启
    private boolean commentabled;
    // 是否发布
    private boolean published;
    // 推荐是否开启
    private boolean recommend;

    private Date createTime;

    private Date updateTime;

    private Long typeId;

    private Long userId;

    private String tagIds;

    private String description;

    //Type
    private String typeName;

    //User
    private String nickname;
    private String avatar;

    // 其他类
    private Type type;
    private User user;
    private List<Tag> tags = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
}
