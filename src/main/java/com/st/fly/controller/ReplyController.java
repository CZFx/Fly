package com.st.fly.controller;

import com.st.fly.entity.JSONResult;
import com.st.fly.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    private AnswerService answerService;

    @PostMapping("/top")
    @ResponseBody
    public Object top() {
        JSONResult json = new JSONResult();
        List tops = answerService.top();

        json.setData(tops);


        return json;
    }
}
