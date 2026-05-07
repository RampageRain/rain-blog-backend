package com.rain.blog.backend.service;

import com.rain.blog.backend.common.Result;
import com.rain.blog.backend.model.dto.LoginRequest;
import com.rain.blog.backend.model.entity.BlogAdmin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rain.blog.backend.model.vo.LoginResponse;

import java.util.Map;

/**
 * 精简登录服务
 *
 * @author Rain
 * @since 2026.05.07
 */
public interface AuthService extends IService<BlogAdmin> {

    Result<LoginResponse> login(LoginRequest request);
}
