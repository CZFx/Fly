package com.st.fly.service.impl;

import com.st.fly.dao.QuestionDao;
import com.st.fly.entity.Question;
import com.st.fly.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionDao questionDao;

    @Override
    public int add(Question question) {
        questionDao.insert(question);
        return 1;
    }

    @Override
    public Question getById(Integer id) {
        return questionDao.selectById(id);
    }

    @Override
    public List<Question> getTop() {
        return questionDao.selectTop();
    }

    @Override
    public List<Question> search(Question question, String order) {
        return questionDao.select(question, order);
    }

    @Override
    public List<Question> getByUid(Integer uid) {
        return questionDao.selectByUid(uid);
    }

    @Override
    public void updateByViewsnum(Integer id, Integer viewsnum) {
        questionDao.updateByViewsnum(id, viewsnum);
    }

    @Override
    public void updateByReplynum(Integer id, Integer replynum) {
        questionDao.updateByReplynum(id, replynum);
    }
}
