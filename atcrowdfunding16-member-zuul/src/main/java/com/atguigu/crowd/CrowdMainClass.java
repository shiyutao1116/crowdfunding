package com.atguigu.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


/**
 * @author shiyutao
 * @create 2022-01-24 17:02
 */
@EnableZuulProxy
@SpringBootApplication
public class CrowdMainClass {
    public static void main(String[] args)
    {
        SpringApplication.run(CrowdMainClass.class, args);
    }
}