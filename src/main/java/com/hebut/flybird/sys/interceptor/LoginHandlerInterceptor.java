package com.hebut.flybird.sys.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by WangJL on 2017/5/6.
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {
    //进入Hander方法之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String url = request.getRequestURI();
        Set<String> anoUrls = new HashSet<String>();
        //需要改成配置文件
        anoUrls.add("/sys/user/register");
        anoUrls.add("/sys/login");
        if(anoUrls.contains(url)){
            return true;
        }
        //判断session
        HttpSession session = request.getSession();
        String account = (String) session.getAttribute("account");
        if(account!=null){
            return true;
        }
        //request.getRequestDispatcher("/public/login.html").forward(request,response);
        response.sendRedirect("/public/login.html");
        return false;
    }
    //进入Handler方法后，返回modelAndView之前
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }
    //执行Hander完成执行此方法
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
