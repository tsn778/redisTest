package com.tsn.controller;

import com.tsn.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class StudentHandler {
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/set")
    public void set(@RequestBody Student student){
        redisTemplate.opsForValue().set("student",student);
    }
    @GetMapping("/get/{key}")
    public Student get(@PathVariable("key") String key){
        return (Student) redisTemplate.opsForValue().get(key);
    }
    @DeleteMapping("/delete/{key}")
    public boolean delete(@PathVariable("key") String key){
        redisTemplate.delete(key);
        return redisTemplate.hasKey(key);
    }
    @GetMapping("/string")
    public String StringTest(){
        redisTemplate.opsForValue().set("str","Hello World");
        String str= (String)redisTemplate.opsForValue().get("str");
        return str;

    }
    @GetMapping("/list")
    public List<String> ListTest(){
       ListOperations<String,String> listOperations= redisTemplate.opsForList();
       listOperations.leftPush("list","Hello");
        listOperations.leftPush("list","World");
        listOperations.leftPush("list","Java");
        List<String> list = listOperations.range("list", 0, 2);
        return list;



    }
}
