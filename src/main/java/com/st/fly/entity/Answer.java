package com.st.fly.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Answer {
    private Integer id;
    private String content;
    private Integer qid;
    private Integer uid;
    private Integer like;
    private Date createtime;
    private Integer status;
    private User user;
    private Question question;
}
