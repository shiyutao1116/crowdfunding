package com.atguigu.crowd.mvc.interceptor;
import com.atguigu.crowd.AccessForbiddenException;
import com.atguigu.crowd.CorwdConstant;
import com.atguigu.crowd.entity.Admin;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author shiyutao
 * @create 2022-01-10 16:15
 */
public class LoginInterceptor extends HandlerInterceptorAdapter{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
       Admin admin = (Admin)session.getAttribute(CorwdConstant.ATTR_NAME_LOGIN_ADMIN);
       if (admin==null){
        throw new AccessForbiddenException(CorwdConstant.MESSAGE_ACCESS_FORBIDDEN);


       }
        return true;
    }
}
