package com.cacheinvalidation.demo.controller;

import com.cacheinvalidation.demo.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/redis")
@RequiredArgsConstructor
public class RedisController {

    private final RedisService redisService;

    @GetMapping("/all-data")
    public ResponseEntity<?> getAllData(@RequestHeader String key) {
        return new ResponseEntity<>(redisService.getData(key), HttpStatus.OK);
    }

    @PostMapping("/add-data")
    public ResponseEntity<?> addData(@RequestBody Map.Entry<String, Object> key) {
        return new ResponseEntity<>(redisService.setString(key), HttpStatus.OK);
    }
}
