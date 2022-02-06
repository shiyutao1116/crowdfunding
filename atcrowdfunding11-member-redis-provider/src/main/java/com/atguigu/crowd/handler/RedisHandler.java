package com.atguigu.crowd.handler;

import com.atguigu.crowd.ResultEntity;
import com.atguigu.crowd.entity.po.MemberPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author shiyutao
 * @create 2022-01-24 16:32
 */
@RestController
public class RedisHandler {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @RequestMapping("set/redis/key/value/remote")
    ResultEntity<String> setRedisKeyValueRemote(@RequestParam("key") String key, @RequestParam("value") String value) {
        try {
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(key, value);
            return ResultEntity.successwithoutdata();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }
    @RequestMapping("set/redis/key/value/remote/with/timeout")
    ResultEntity<String> setRedisKeyValueRemoteWithTimeout
            (@RequestParam("key") String key,@RequestParam("value") String value,@RequestParam("time") long time,@RequestParam("timeUnit") TimeUnit timeUnit){
        try {
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(key,value,time,timeUnit);
            return ResultEntity.successwithoutdata();
        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }//timeunit注意
    @RequestMapping("get/redis/string/key/value/remote")
    ResultEntity<String> getRedisKeyStringValueRemote(@RequestParam("key") String key){
        try {
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            String string = operations.get(key);
            return ResultEntity.successwithdata(string);
        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }
    @RequestMapping("remove/redis/string/key/value/remote")
    ResultEntity<String> removeRedisKeyStringValueRemote(@RequestParam("key") String key){
        try {
            stringRedisTemplate.delete(key);
            return ResultEntity.successwithoutdata();
        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }

    }
}
