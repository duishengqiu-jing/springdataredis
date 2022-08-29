package com.liujing.spring.redis.springdataredis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.Subscription;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TestRedis {
    // 高阶的redis是面向java序列化的，会加一些东西而不是字面的
    // 更多场景是基于string方式，除了通用的template还有一个template
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    @Qualifier("xxx")
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    ObjectMapper objectMapper;

    public void testRedis() {
        //high-level  api
//        redisTemplate.opsForValue().set("hello", "china");
//        stringRedisTemplate.opsForValue().set("hello", "china");
//        System.out.println(stringRedisTemplate.opsForValue().get("hello"));
        // low-level api
//        RedisConnection conn = redisTemplate.getConnectionFactory().getConnection();
        // 所有在redis中有的命令都可以找到
//        conn.set("hello02".getBytes(), "mashibing".getBytes());
//        System.out.println(new String(conn.get("hello02".getBytes())));

        // redis 分为两种：
        // 1、单值 string
        // 2、多值 hash
//        HashOperations<String, Object, Object> hash = stringRedisTemplate.opsForHash();
//        hash.put("sean", "name", "liujing");
//        hash.put("sean", "age", "30");
//        System.out.println(hash.entries("sean"));

        // 对象操作
//        Person person = new Person();
//        person.setName("liuchang");
//        person.setAge(20);

//        Jackson2HashMapper jm = new Jackson2HashMapper(objectMapper, false);
        // 如果直接用那么将person变成一个hashmap，但是需要完成对象到hashmap的转换过程
        // 怎么样把hashmap存到redis里面去呢？直接做二进制序列化呢还是json呢？
        // 别人取的时候到底是不是java，能不能反序列化呢

//        stringRedisTemplate.setHashValueSerializer(new     Jackson2JsonRedisSerializer<Object>(Object.class));
//        stringRedisTemplate.opsForHash().putAll("sean01", jm.toHash(person));
//        // 取回来的map怎么处理？
//        Map map = stringRedisTemplate.opsForHash().entries("sean01");
//
//        Person person1 = objectMapper.convertValue(map, Person.class);
//        System.out.println(person1.getName()+person1.getAge());

        // 发布消息
//        stringRedisTemplate.convertAndSend("channel1", "hello");

        // 订阅消息接收消息
        RedisConnection cc = stringRedisTemplate.getConnectionFactory().getConnection();
        cc.subscribe(new MessageListener() {
            @Override
            public void onMessage(Message message, byte[] pattern) {
                System.out.println(new String(message.getBody()));
            }
        }, "channel1".getBytes());
        for (int i = 0; i < 20; i++) {
            stringRedisTemplate.convertAndSend("channel1", "hello"+i);
        }

        while (true) {

        }
    }
}
