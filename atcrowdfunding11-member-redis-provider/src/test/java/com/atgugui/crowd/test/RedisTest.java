package com.atgugui.crowd.test;

import com.atguigu.crowd.CrowdMainClass;
import javafx.application.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author shiyutao
 * @create 2022-01-24 15:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CrowdMainClass.class)
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test(){
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set("apple","red");

    }


}
