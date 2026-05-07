package com.rain.blog.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rain.blog.backend.common.Result;
import com.rain.blog.backend.model.dto.LoginRequest;
import com.rain.blog.backend.model.entity.BlogAdmin;
import com.rain.blog.backend.mapper.BlogAdminMapper;
import com.rain.blog.backend.model.vo.LoginResponse;
import com.rain.blog.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 精简登录服务实现
 *
 * @author Rain
 * @since 2026.05.07
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl extends ServiceImpl<BlogAdminMapper, BlogAdmin>
        implements AuthService {

    private final BlogAdminMapper blogAdminMapper;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public Result<LoginResponse> login(LoginRequest request) {
        if (request == null) {
            return Result.fail(400, "请求参数不能为空");
        }

        String username = request.getUsername();
        String password = request.getPassword();

        if (username == null || username.trim().isEmpty()) {
            return Result.fail(400, "请输入用户名");
        }

        if (password == null || password.trim().isEmpty()) {
            return Result.fail(400, "请输入密码");
        }

        BlogAdmin admin = blogAdminMapper.selectOne(
                new LambdaQueryWrapper<BlogAdmin>()
                        .eq(BlogAdmin::getUsername, username.trim())
                        .eq(BlogAdmin::getStatus, 1)
                        .last("limit 1")
        );

        if (admin == null) {
            return Result.fail(401, "用户名或密码错误");
        }

        boolean matched = passwordEncoder.matches(password, admin.getPasswordHash());
        if (!matched) {
            return Result.fail(401, "用户名或密码错误");
        }

        String token = UUID.randomUUID().toString().replace("-", "");
        String redisKey = "rain-blog:admin:token:" + token;

        stringRedisTemplate.opsForValue().set(
                redisKey,
                String.valueOf(admin.getId()),
                Duration.ofHours(2)
        );

        admin.setLastLoginTime(new Date());
        blogAdminMapper.updateById(admin);

        LoginResponse data = new LoginResponse();
        data.setToken(token);
        data.setUsername(username);
        data.setNickname(admin.getNickname());
        data.setAvatar(admin.getAvatar());

        return Result.success(data);
    }
}
