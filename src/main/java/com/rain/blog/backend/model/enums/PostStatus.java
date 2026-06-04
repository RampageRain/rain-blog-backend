package com.rain.blog.backend.model.enums;

/**
 * 文章状态
 *
 * @author Rain
 * @since 2026.05.29
 */
public enum PostStatus {

    /**
     * 草稿
     */
    DRAFT(0),

    /**
     * 已发布
     */
    PUBLISHED(1),

    /**
     * 隐藏
     */
    HIDDEN(2);

    private final int code;

    PostStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
