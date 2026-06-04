package com.rain.blog.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rain.blog.backend.model.entity.BlogPost;
import com.rain.blog.backend.model.enums.PostStatus;
import com.rain.blog.backend.model.vo.PostDetailVO;
import com.rain.blog.backend.model.vo.PostListItemVO;
import com.rain.blog.backend.service.BlogPostService;
import com.rain.blog.backend.mapper.BlogPostMapper;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 博客文章展示服务
 *
 * @author Rain
 * @description 针对表【blog_post(博客文章主表)】的数据库操作Service实现
 * @createDate 2026-05-15 18:25:59
 */
@Service
public class BlogPostServiceImpl extends ServiceImpl<BlogPostMapper, BlogPost>
        implements BlogPostService {

    @Override
    public Page<PostListItemVO> pagePublishedPosts(long current, long pageSize) {
        LambdaQueryWrapper<BlogPost> queryWrapper = new LambdaQueryWrapper<BlogPost>()
                .eq(BlogPost::getStatus, PostStatus.PUBLISHED.getCode())
                .orderByDesc(BlogPost::getIsTop)
                .orderByDesc(BlogPost::getPublishTime)
                .orderByDesc(BlogPost::getCreateTime);

        Page<BlogPost> postPage = page(new Page<>(current, pageSize), queryWrapper);
        Page<PostListItemVO> postListItemVOPage = new Page<>(
                postPage.getCurrent(),
                postPage.getSize(),
                postPage.getTotal()
        );

        postListItemVOPage.setRecords(postPage.getRecords().stream()
                .map(this::toPostListItemVO)
                .toList());
        return postListItemVOPage;
    }

    @Override
    public BlogPost getPublishedPostById(Long id) {
        // 仅查已发布；逻辑删除 deleted=0 由实体上的 @TableLogic 自动过滤
        return lambdaQuery()
                .eq(BlogPost::getId, id)
                .eq(BlogPost::getStatus, PostStatus.PUBLISHED.getCode())
                .one();
    }

    @Override
    public void increaseViewCount(Long id) {
        // 用 setSql 让数据库自增，避免"读出再写回"的并发覆盖
        lambdaUpdate()
                .eq(BlogPost::getId, id)
                .setSql("view_count = view_count + 1")
                .update();
    }

    @Override
    public PostDetailVO toPostDetailVO(BlogPost post) {
        PostDetailVO vo = new PostDetailVO();
        fillCardFields(vo, post);
        return vo;
    }

    private PostListItemVO toPostListItemVO(BlogPost post) {
        PostListItemVO vo = new PostListItemVO();
        fillCardFields(vo, post);
        return vo;
    }

    /**
     * 填充列表卡片公共字段；PostDetailVO 继承自 PostListItemVO，列表与详情共用
     */
    private void fillCardFields(PostListItemVO vo, BlogPost post) {
        vo.setId(post.getId());
        vo.setTitle(post.getTitle());
        vo.setSummary(post.getSummary());
        vo.setCategory(post.getCategoryName());
        vo.setCoverKey(post.getCoverKey());
        vo.setDate(formatDate(getDisplayTime(post)));
        vo.setViews(post.getViewCount() == null ? 0L : post.getViewCount());
    }

    private Date getDisplayTime(BlogPost post) {
        if (post.getPublishTime() != null) {
            return post.getPublishTime();
        }
        return post.getCreateTime();
    }

    private String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
}
