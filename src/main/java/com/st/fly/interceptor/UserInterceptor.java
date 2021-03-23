package com.st.fly.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * HandlerInterceptor，在SpringMVC项目中，作为处理器拦截器，
 * 在执行处理时，可以在执行前，执行后，页面渲染后进行拦截处理
 */
@Component
public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断用户是否登录
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            //如果没有登录，跳转到登录页面
            response.sendRedirect("/user/login");
            return false;
        }
        //如果登录，放行
        return true;
    }
}
