package com.st.fly.controller;

import com.st.fly.entity.Answer;
import com.st.fly.entity.JSONResult;
import com.st.fly.entity.Question;
import com.st.fly.entity.User;
import com.st.fly.service.AnswerService;
import com.st.fly.service.QuestionService;
import com.st.fly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Jie = 解答
 * 这种写法很不符合规则
 * 在设计数据库的时候，对应的是question表
 */
@Controller
@RequestMapping("/jie")
public class JieController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @Autowired
    private AnswerService answerService;

    /**
     * 发布新帖的页面
     */
    @GetMapping("/add")
    public String add() {
        return "jie/add";
    }

    @PostMapping("/add")
    public String add(Question question, String vercode,
                        Model model, HttpSession session) {

        //验证码验证
        String oldVercode = (String) session.getAttribute("vercode");
        if (!vercode.toUpperCase().equals(oldVercode.toUpperCase())) {
            //验证码错误
            model.addAttribute("error", "验证码错误");
            return "jie/add";
        }

        //1. 接收参数，从session中获取uid
        User user = (User) session.getAttribute("user");
        question.setUid(user.getId());
        //2. 调用Service处理业务
        int code = questionService.add(question);
        //3. 根据情况进行跳转
        if (code > 0) {
            //发布成功
            return "redirect:/jie/detail?id=" + question.getId();
        } else {
            //提示错误信息，返回发布页面
            model.addAttribute("error", "发布失败");
            return "jie/add";
        }
    }

    @GetMapping("/detail")
    public String detail(Integer id, Model model) {
        //获取提问
        Question question = questionService.getById(id);
        model.addAttribute("question", question);
        //获取作者
        // 1. 再次调用Service层的方法，查询数据库
        /*User user = userService.getById(question.getUid());
        model.addAttribute("user", user);*/

        // 2. 在查询question时，用uid映射出user到question的user属性中

        // 3. 将回复数据也一同查询出来
        List<Answer> answers = answerService.getByQid(id);

        // 4. 增加浏览量
        questionService.updateByViewsnum(question.getId(), question.getViewsnum()+1);

        model.addAttribute("answers", answers);

        return "jie/detail";
    }

    @PostMapping("/reply")
    public String reply(Answer answer, HttpSession session, Model model) {
        // 1. 接收参数
        // 当前登陆的用户
        User user = (User) session.getAttribute("user");
        answer.setUid(user.getId());
        // 2. 调用Service层
        // 可能出现异常的处理情况
        int code = answerService.add(answer);
        if (code < 0) {
            model.addAttribute("error", "服务器异常，请稍后再试");
            return "jie/detail?id=" + answer.getQid();
        }

        // 3. 更新评论数量
        questionService.updateByReplynum(answer.getQuestion().getId(), answer.getQuestion().getReplynum()+1);

        // 4. 页面跳转
        return "redirect:/jie/detail?id=" + answer.getQid();
    }

    /*
     * 回复的操作
     */
    /*@PostMapping("/reply")
    @ResponseBody
    public Object reply(Answer answer, HttpSession session) {
        // 登录用户
        User user = (User) session.getAttribute("user");
        answer.setUid(user.getId());

        // 调用Service层进行处理
        int res = answerService.add(answer);

        Map<String, Object> result = new HashMap<>();

        if (res > 0) {
            // 插入成功
            result.put("status", 0);
            result.put("msg", "回复成功");
            result.put("user", user);
        } else {
            // 插入失败
            result.put("status", -1);
            result.put("msg", "回复失败");
        }

        return result;
    }*/

    @RequestMapping("/top")
    @ResponseBody   //不会进行视图解析，返回的结果会自动封装成json格式
    public Object top() {
        //睡眠进行loading图片测试
        /*try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        List<Question> questions = questionService.getTop();

        JSONResult result = new JSONResult();
        result.setStatus(0);
        result.setData(questions);

        return result;
    }

    @RequestMapping("/search")
    @ResponseBody   //不会进行视图解析，返回的结果会自动封装成json格式
    public Object search(Question question, String order) {
        List<Question> questions = questionService.search(question, order);

        JSONResult result = new JSONResult();
        result.setStatus(0);
        result.setData(questions);

        return result;
    }

}
