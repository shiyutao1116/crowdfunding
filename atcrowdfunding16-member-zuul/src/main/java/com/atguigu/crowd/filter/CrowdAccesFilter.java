package com.atguigu.crowd.filter;

import com.atguigu.crowd.AccessPassResources;
import com.atguigu.crowd.CorwdConstant;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author shiyutao
 * @create 2022-01-26 20:25
 */
@Component
public class CrowdAccesFilter extends ZuulFilter{
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String servletPath = request.getServletPath();
        boolean contains = AccessPassResources.PASS_RES_SET.contains(servletPath);
        if (contains){
            return false;
        }
        boolean b = AccessPassResources.judgeCurrentServletPathINStatic(servletPath);
        if (b){
            return false;
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        HttpSession session = request.getSession();
        Object attribute = session.getAttribute(CorwdConstant.ATTR_NAME_LOGIN_MEMBER);
        if (attribute==null){
            HttpServletResponse response = currentContext.getResponse();
            session.setAttribute(CorwdConstant.ATTR_NAME_MESSAGE,CorwdConstant.MESSAGE_ACCESS_FORBIDDEN);
            try {
                response.sendRedirect("/auth/member/to/login/page");
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return null;

    }



















}
