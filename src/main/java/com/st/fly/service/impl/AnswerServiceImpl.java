package com.st.fly.service.impl;

import com.st.fly.dao.AnswerDao;
import com.st.fly.entity.Answer;
import com.st.fly.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerDao answerDao;

    @Override
    public int add(Answer answer) {
        try {
            answerDao.insert(answer);
        } catch (Exception e) {
            return -1;
        }
        return 1;
    }

    @Override
    public List<Answer> getByQid(Integer id) {
        return answerDao.selectByQid(id);
    }

    @Override
    public List top() {
        //获取七天前的时间
        Date date = new Date(System.currentTimeMillis()/1000*1000 - 6*12*60*60*1000);
        //清空时分秒
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        return answerDao.selectCountByUser(date);
    }

    @Override
    public List<Answer> getByUid(Integer uid) {
        return answerDao.selectByUid(uid);
    }
}
