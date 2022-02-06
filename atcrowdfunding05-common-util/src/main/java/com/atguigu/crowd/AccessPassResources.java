package com.atguigu.crowd;

import jdk.jfr.events.ThrowablesEvent;

import java.util.HashSet;
import java.util.Set;

/**
 * @author shiyutao
 * @create 2022-01-26 19:43
 */
public class AccessPassResources {
    public static final Set<String> PASS_RES_SET=new HashSet<>();

    static {
        PASS_RES_SET.add("/");
        PASS_RES_SET.add("/auth/member/to/reg/page.html");
        PASS_RES_SET.add("/auth/member/to/login/page");
        PASS_RES_SET.add("/auth/member/logout");
        PASS_RES_SET.add("/auth/member/do/login");
        PASS_RES_SET.add("/auth/do/member/register");
        PASS_RES_SET.add("/auth/member/send/short/message.json");
       // PASS_RES_SET.add("http://localhost/project/get/project/detail/*");


    }
    public static final Set<String> STATIC_RES_SET=new HashSet<>();
    static {
        STATIC_RES_SET.add("bootstrap");
        STATIC_RES_SET.add("css");
        STATIC_RES_SET.add("fonts");
        STATIC_RES_SET.add("img");
        STATIC_RES_SET.add("jquery");
        STATIC_RES_SET.add("layer");
        STATIC_RES_SET.add("script");
        STATIC_RES_SET.add("ztree");
    }
    public static boolean judgeCurrentServletPathINStatic(String servletPath){
        if (servletPath==null||servletPath.length()==0){
            throw new RuntimeException();
        }
        String[] split = servletPath.split("/");
        String string=split[1];
        return STATIC_RES_SET.contains(string);
    }





}
