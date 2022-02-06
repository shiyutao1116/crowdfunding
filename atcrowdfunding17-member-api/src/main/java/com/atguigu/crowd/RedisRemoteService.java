package com.atguigu.crowd;

import com.atguigu.crowd.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.TimeUnit;

/**
 * @author shiyutao
 * @create 2022-01-24 16:15
 */
@FeignClient("atguigu-crowd-redis")
public interface RedisRemoteService {
    @RequestMapping("set/redis/key/value/remote")
    ResultEntity<String> setRedisKeyValueRemote(@RequestParam("key") String key,@RequestParam("value") String value);
    @RequestMapping("set/redis/key/value/remote/with/timeout")
    ResultEntity<String> setRedisKeyValueRemoteWithTimeout
    (@RequestParam("key") String key,@RequestParam("value") String value,@RequestParam("time") long time,@RequestParam("timeUnit") TimeUnit timeUnit);//timeunit注意
    @RequestMapping("get/redis/string/key/value/remote")
    ResultEntity<String> getRedisKeyStringValueRemote(@RequestParam("key") String key);
    @RequestMapping("remove/redis/string/key/value/remote")
    ResultEntity<String> removeRedisKeyStringValueRemote(@RequestParam("key") String key);

}
