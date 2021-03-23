package com.st.fly.controller;

import com.st.fly.entity.JSONResult;
import com.st.fly.util.UploadUtil;
import com.st.fly.util.VerCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 生成验证码
     */
    @RequestMapping("/vercode")
    public void vercode (HttpSession session, HttpServletResponse response) {
        //生成一张图片，通过response对象向客户端浏览器返回
        //与图片对应的字符串，保存到session中
        String vercode = VerCodeUtil.createVerCode(response);
        session.setAttribute("vercode", vercode);
    }

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    @ResponseBody
    public Object upload(MultipartFile file) {
        // 将上传的图片保存至服务器中，E:/training/upload/
        String fileName = UploadUtil.save(file, "E:/training/upload/");
        JSONResult json = new JSONResult();
        json.setData(fileName);

        return json;
    }
}
