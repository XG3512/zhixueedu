package com.xvls.alexander;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xvls.alexander.entity.User;
import com.xvls.alexander.utils.JacksonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AlexanderApplication.class)
public class RedisTest {
    @Autowired
    private User user;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void test() throws JsonProcessingException {
        //从redis缓存中获得指定的数据
        String userListData = redisTemplate.boundValueOps("user.selectAll").get();
        //如果redis中没有数据的话
        if (null == userListData) {
            //查询数据库获得数据
            List<User> all = user.selectAll();
            //转换成json格式字符串
            ObjectMapper om = new ObjectMapper();
            userListData = om.writeValueAsString(all);
            //将数据存储到redis中，下次在查询直接从redis中获得数据，不用在查询数据库
            redisTemplate.boundValueOps("user.findAll").set(userListData);
            System.out.println("===============从数据库获得数据===============");
        } else {
            System.out.println("===============从redis缓存中获得数据===============");
        }
        System.out.println(userListData);
    }

    @Test
    public void testSet(){

        stringRedisTemplate.opsForValue().set("1","testRedis1");
        System.out.println("测试set Redis中的值");
        System.out.println("获取redis中的值："+stringRedisTemplate.opsForValue().get("1"));

        User user2=new User();
        user2.setId(10L);
        user2.setUName("Glitter");

        redisTemplate.opsForValue().set("2", JacksonUtil.to(user2));
        System.out.println("输出redis中的User对象："+ redisTemplate.opsForValue().get("2"));
    }
}
