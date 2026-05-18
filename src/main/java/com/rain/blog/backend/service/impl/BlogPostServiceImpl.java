package com.rain.blog.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rain.blog.backend.model.entity.BlogPost;
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

    private static final int POST_STATUS_PUBLISHED = 1;

    @Override
    public Page<PostListItemVO> pagePublishedPosts(long current, long pageSize) {
        LambdaQueryWrapper<BlogPost> queryWrapper = new LambdaQueryWrapper<BlogPost>()
                .eq(BlogPost::getStatus, POST_STATUS_PUBLISHED)
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

    private PostListItemVO toPostListItemVO(BlogPost post) {
        PostListItemVO postListItemVO = new PostListItemVO();
        postListItemVO.setId(post.getId());
        postListItemVO.setTitle(post.getTitle());
        postListItemVO.setSummary(post.getSummary());
        postListItemVO.setCategory(post.getCategoryName());
        postListItemVO.setCoverKey(post.getCoverKey());
        postListItemVO.setDate(formatDate(getDisplayTime(post)));
        postListItemVO.setViews(post.getViewCount() == null ? 0L : post.getViewCount());
        return postListItemVO;
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



