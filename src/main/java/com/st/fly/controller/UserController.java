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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public Object login(String username, String password, String vercode,
                        HttpSession session) {

        JSONResult json = new JSONResult();

        //验证码验证
        String oldVercode = (String) session.getAttribute("vercode");
        if (!vercode.toUpperCase().equals(oldVercode.toUpperCase())) {
            //验证码错误
            /*model.addAttribute("error", "验证码错误");
            return "user/login";*/
            json.setData(1);
            json.setMsg("验证码错误，请重新输入");
            return json;
        }

        try {
            User user = userService.login(username, password);
            if (user != null) {
                session.setAttribute("user", user);
                //return "redirect:/";
                json.setData(0);

            } else {
                //model.addAttribute("error", "账号或密码不正确");
                json.setMsg("账号密码不正确");
                json.setData(2);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 1. 传递错误信息
            //model.addAttribute("error", e.getMessage());
            json.setMsg(e.getMessage());
        }
        //return "user/login";
        return json;
    }

    /**
     * 退出登陆
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        //清除保存的登陆信息
        //session.removeAttribute("user");
        session.invalidate();//销毁session对象  //token
        //返回主页
        return "redirect:/";
    }

    @GetMapping("/reg")
    public String reg() {
        return "user/reg";
    }

    /**
     * 处理注册请求
     */
    @PostMapping("/reg")
    @ResponseBody
    public Object reg(String username, String password, String nickname,
                      String repassword, String vercode,
                      HttpSession session) {

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname(nickname);

        JSONResult json = new JSONResult();

        //验证码验证
        String oldVercode = (String) session.getAttribute("vercode");
        if (!vercode.toUpperCase().equals(oldVercode.toUpperCase())) {
            json.setData(1);
            json.setMsg("验证码错误，请重新输入");
            return json;
        }

        int code = userService.reg(user, repassword);

        System.out.println(code);
        if (code > 0) {
            //注册成功，跳转登陆页面
            json.setData(code);
            return json;
        } else {
            //注册失败
            switch (code) {
                case -1 : json.setMsg("两次密码不一致");break;
                case -2 : json.setMsg("账号和密码不能少于3位，昵称不能少于2位");break;
                case -3 : json.setMsg("账号已存在，请重新输入");break;
                case -4 : json.setMsg("昵称已存在，请重新输入");break;
                default : json.setMsg("未知错误，请联系管理员");break;
            }
            json.setData(code);
            return json;
        }
    }

    /**
     * 基本设置页面
     */
    @GetMapping("/set")
    public String set() {
        return "user/set";
    }

    /**
     * 我的消息页面
     */
    @GetMapping("/message")
    public String message() {
        return "user/message";
    }

    /**
     * 我的主页页面
     */
    @GetMapping("/home")
    public String home(Integer id, Model model) {
        //获取当前被访问的用户的信息
        User user = userService.getById(id);
        model.addAttribute("user", user);
        //获取对应用户的question和answer
        List<Question> questions = questionService.getByUid(id);
        List<Answer> answers = answerService.getByUid(id);
        model.addAttribute("questions", questions);
        model.addAttribute("answers", answers);
        return "user/home";
    }

    /**
     * 处理上传成功后的请求（头像）
     */
    @PostMapping("/set")
    @ResponseBody
    public void set(User user, HttpSession session) {
        // 获取当前登录的用户id
        User loginUser = (User) session.getAttribute("user");
        user.setId(loginUser.getId());
        // 调用业务层更新user数据
        userService.set(user);
        // 将session中的user数据更新
        loginUser.setHead(user.getHead());
    }

    /**
     * 用户中心页面
     */
    @GetMapping("/index")
    public String index(){
        return "user/index";
    }



}
