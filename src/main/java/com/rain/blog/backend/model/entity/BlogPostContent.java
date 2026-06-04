package com.rain.blog.backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 博客文章Markdown内容表
 *
 * @TableName blog_post_content
 */
@TableName(value = "blog_post_content")
@Data
public class BlogPostContent implements Serializable {
    /**
     * 文章内容ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 文章ID，对应blog_post.id
     */
    private Long postId;

    /**
     * Markdown格式的文章正文
     */
    private String contentMd;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}