package com.rain.blog.backend.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 博客文章主表
 *
 * @TableName blog_post
 */
@TableName(value = "blog_post")
@Data
public class BlogPost implements Serializable {
    /**
     * 文章ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章摘要，用于文章列表卡片展示
     */
    private String summary;

    /**
     * 文章访问别名，预留字段，后续可用于更美观的URL
     */
    private String slug;

    /**
     * 封面图标识，后端保存key，前端根据key映射到本地图片
     */
    private String coverKey;

    /**
     * 分类ID，预留字段，后续关联分类表
     */
    private Long categoryId;

    /**
     * 分类名称快照，第一阶段可直接用于前端展示
     */
    private String categoryName;

    /**
     * 文章状态：0草稿，1已发布，2隐藏
     */
    private Integer status;

    /**
     * 是否置顶：0否，1是
     */
    private Integer isTop;

    /**
     * 阅读量
     */
    private Long viewCount;

    /**
     * 点赞数，预留字段
     */
    private Long likeCount;

    /**
     * 发布时间，文章发布后才有值
     */
    private Date publishTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除：0未删除，1已删除
     */
    @TableLogic
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}