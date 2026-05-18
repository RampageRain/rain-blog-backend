package com.rain.blog.backend.model.vo;

import lombok.Data;

/**
 * 文章列表卡片展示数据
 *
 * @author Rain
 * @since 2026.05.18
 */
@Data
public class PostListItemVO {

    /**
     * 文章ID
     */
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章摘要
     */
    private String summary;

    /**
     * 分类名称
     */
    private String category;

    /**
     * 封面图标识
     */
    private String coverKey;

    /**
     * 发布时间，格式 yyyy-MM-dd
     */
    private String date;

    /**
     * 阅读量
     */
    private Long views;
}
