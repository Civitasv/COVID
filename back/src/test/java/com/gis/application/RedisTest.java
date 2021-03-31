package com.gis.application;

import com.gis.application.redis.RedisCacheManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisCacheManager redisCacheManager;

    @Test
    public void test() {
        redisCacheManager.set("key2", "value3");
        redisCacheManager.lSetAll("list", Arrays.asList("hello", "redis"));
        List<Object> list = redisCacheManager.lGet("list", 0, -1);
        System.out.println(redisCacheManager.get("key2"));
    }
}
