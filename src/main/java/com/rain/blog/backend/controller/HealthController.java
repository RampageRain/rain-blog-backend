package com.rain.blog.backend.controller;

import com.rain.blog.backend.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping
    public Result<String> health() {
        return Result.success("ok");
    }
}
