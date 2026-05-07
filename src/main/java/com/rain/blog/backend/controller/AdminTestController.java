package com.rain.blog.backend.controller;

import com.rain.blog.backend.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理员测试接口
 *
 * @author Rain
 * @since 2026.05.07
 */
@RestController
public class AdminTestController {

    @GetMapping("/api/admin/hello")
    public Result<String> hello() {
        return Result.success("后台接口访问成功");
    }
}
