package com.rain.blog.backend.controller;

import com.rain.blog.backend.common.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rain.blog.backend.model.vo.PostDetailVO;
import com.rain.blog.backend.model.vo.PostListItemVO;
import com.rain.blog.backend.service.BlogPostContentService;
import com.rain.blog.backend.service.BlogPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 博客文章公开接口
 *
 * @author Rain
 * @since 2026.05.18
 */
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final BlogPostService blogPostService;
    private final BlogPostContentService blogPostContentService;

    @GetMapping
    public Result<Page<PostListItemVO>> pagePublishedPosts(
            @RequestParam(defaultValue = "1") long current,
            @RequestParam(defaultValue = "9") long pageSize
    ) {
        return Result.success(blogPostService.pagePublishedPosts(current, pageSize));
    }

    @GetMapping("/{id}")
    public Result<PostDetailVO> getPostDetail(@PathVariable Long id) {
        PostDetailVO detail = blogPostContentService.getPostDetail(id);
        if (detail == null) {
            return Result.fail(404, "文章不存在");
        }
        return Result.success(detail);
    }
}
