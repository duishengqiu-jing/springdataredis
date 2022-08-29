package com.liujing.spring.redis.springdataredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.MalformedURLException;
import java.net.URL;

@SpringBootApplication
public class SpringdataredisApplication {

    public static void main(String[] args) throws MalformedURLException {
        // 基于功能和代码开发的方式，这一行代码是基于容器的代码
        // 因为外界拿不到这个对象就没办法获取bean所以需要拿到对象
        // 如果是基于web开发有tomcat的话，是tomcat完成对这个的调用，拿出了相应的请求的url匹配找到
        // 调起方法，spring容器把要用的对象扔进去，这个是基本知识
        // 即便是spring boot也是容器
        ConfigurableApplicationContext context = SpringApplication.run(SpringdataredisApplication.class, args);
        TestRedis redis = context.getBean(TestRedis.class);
        redis.testRedis();
//        Example example = context.getBean(Example.class);
//        example.addLink("111",new URL("aaa", "bbb", 8000, "fff"));
    }

}
