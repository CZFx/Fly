package com.st.fly.service;

import com.st.fly.entity.Question;

import java.util.List;

public interface QuestionService {

    int add(Question question);

    Question getById(Integer id);

    List<Question> getTop();

    List<Question> search(Question question, String order);

    List<Question> getByUid(Integer id);

    void updateByViewsnum(Integer id, Integer viewsnum);

    void updateByReplynum(Integer id, Integer replynum);
}
