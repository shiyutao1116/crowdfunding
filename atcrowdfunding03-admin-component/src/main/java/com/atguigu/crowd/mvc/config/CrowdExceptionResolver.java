package com.atguigu.crowd.mvc.config;
import com.atguigu.crowd.*;
import com.google.gson.Gson;
import com.sun.deploy.net.HttpResponse;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.atguigu.crowd.CorwdConstant.ATTR_NAME_EXCEPTION;

/**
 * @author shiyutao
 * @create 2022-01-09 14:10
 */
@ControllerAdvice
public class CrowdExceptionResolver {
    @ExceptionHandler(value = LoginFailedException.class)
    public ModelAndView resolveLoginFailedException(LoginFailedException exception, HttpServletRequest request , HttpServletResponse response) throws IOException {
        String viewName = "admin-login";
        return commonresolve( exception, request, response,viewName);
    }
    @ExceptionHandler(value = NullPointerException.class)
    public ModelAndView resolveNullpointException(NullPointerException exception, HttpServletRequest request , HttpServletResponse response) throws IOException {
            String viewName = "system-error";
            return commonresolve( exception, request, response,viewName);
        }
    @ExceptionHandler(value =  LoginAcctAlreadyInUserException.class)
    public ModelAndView  LoginAcctAlreadyInUserException(LoginAcctAlreadyInUserException exception, HttpServletRequest request , HttpServletResponse response) throws IOException {
        String viewName = "admin-add";
        return commonresolve( exception, request, response,viewName);
    }
    @ExceptionHandler(value =  LoginAcctAlreadyInUserException2.class)
    public ModelAndView  LoginAcctAlreadyInUserException2(LoginAcctAlreadyInUserException exception, HttpServletRequest request , HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonresolve( exception, request, response,viewName);
    }


    private ModelAndView commonresolve(Exception exception, HttpServletRequest request , HttpServletResponse response ,String viewName) throws IOException {
        boolean isajax = CrowdUtil.judgeRequestType(request);
        if (isajax){
            ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());
            Gson gson = new Gson();
            String json = gson.toJson(resultEntity);
            response.getWriter().write(json);
            return null;
        }
        else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject(ATTR_NAME_EXCEPTION,exception);
            modelAndView.setViewName(viewName);
            return  modelAndView;

        }
    }
}
