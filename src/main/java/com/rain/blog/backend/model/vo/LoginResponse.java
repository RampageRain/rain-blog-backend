package com.rain.blog.backend.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录响应
 *
 * @author Rain
 * @since 2026.05.07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String token;

    private String username;

    private String nickname;

    private String avatar;
}