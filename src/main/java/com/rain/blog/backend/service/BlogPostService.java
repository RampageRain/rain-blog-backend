package com.rain.blog.backend.service;

import com.rain.blog.backend.model.entity.BlogPost;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rain.blog.backend.model.vo.PostListItemVO;

/**
* @author Mova
* @description 针对表【blog_post(博客文章主表)】的数据库操作Service
* @createDate 2026-05-15 18:25:59
*/
public interface BlogPostService extends IService<BlogPost> {

    /**
     * 分页查询已发布文章列表
     */
    Page<PostListItemVO> pagePublishedPosts(long current, long pageSize);
}
