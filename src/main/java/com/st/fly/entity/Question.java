package com.st.fly.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Question {
    private Integer id;
    private String title;
    private String content;
    private Integer type;
    private Integer kiss;
    private Integer uid;
    private Integer viewsnum;
    private Integer replynum;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date createtime;

    private Integer level;
    private Integer status;

    //解析出单项一对一的映射，帖子发布者的信息
    private User user;
}
