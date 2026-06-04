package com.rain.blog.backend.service;

import com.rain.blog.backend.model.entity.BlogPostContent;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rain.blog.backend.model.vo.PostDetailVO;

/**
 * @author Rain
 * @description 针对表【blog_post_content(博客文章Markdown内容表)】的数据库操作Service
 * @createDate 2026-05-29 12:02:24
 */
public interface BlogPostContentService extends IService<BlogPostContent> {
    PostDetailVO getPostDetail(Long id);
}
