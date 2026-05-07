package com.rain.blog.backend.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rain.blog.backend.common.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 管理拦截器
 *
 * @author Rain
 * @since 2026.05.07
 */
@Component
@RequiredArgsConstructor
public class AdminAuthInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate stringRedisTemplate;

    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            writeUnauthorized(response, "请先登录");
            return false;
        }

        String token = authorization.substring(7);

        String redisKey = "rain-blog:admin:token:" + token;

        String adminId = stringRedisTemplate.opsForValue().get(redisKey);

        if (adminId == null) {
            writeUnauthorized(response, "登录已过期，请重新登录");
            return false;
        }

        return true;
    }

    private void writeUnauthorized(HttpServletResponse response, String message) throws Exception {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        Result<Void> result = Result.fail(401, message);

        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
