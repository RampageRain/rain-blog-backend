package com.rain.blog.backend.model.dto;

import lombok.Data;

/**
 * 登录请求DTO
 *
 * @author Rain
 * @since 2026.05.07
 */
@Data
public class LoginRequest {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
