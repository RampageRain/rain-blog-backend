package com.rain.blog.backend.controller;

import com.rain.blog.backend.common.Result;
import com.rain.blog.backend.model.dto.LoginRequest;
import com.rain.blog.backend.model.vo.LoginResponse;
import com.rain.blog.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 管理页面
 *
 * @author Rain
 * @since 2026.05.07
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
