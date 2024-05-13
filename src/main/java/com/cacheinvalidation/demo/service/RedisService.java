package com.cacheinvalidation.demo.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    public Map.Entry<String, Object> setString(Map.Entry<String, Object> kvp) {
        redisTemplate.opsForValue().set(kvp.getKey(), kvp.getValue());

        return kvp;
    }


    public Object getData(String key) {
        redisTemplate.delete(key);
        return redisTemplate.opsForHash().entries(key);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Employee implements Serializable {
        private String name;
        private Integer age;
    }
}
