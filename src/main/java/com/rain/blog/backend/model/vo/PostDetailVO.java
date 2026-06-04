package com.rain.blog.backend.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文章详情展示数据
 *
 * @author Rain
 * @since 2026.05.29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PostDetailVO extends PostListItemVO{
    /**
     * Markdown 正文
     */
    private String contentMd;
}
