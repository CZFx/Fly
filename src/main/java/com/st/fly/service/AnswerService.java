package com.st.fly.service;

import com.st.fly.entity.Answer;

import java.util.List;

public interface AnswerService {

    int add(Answer answer);

    List<Answer> getByQid(Integer id);

    /**
     * 获取最近一周的回复榜
     */
    List top();

    List<Answer> getByUid(Integer uid);
}
