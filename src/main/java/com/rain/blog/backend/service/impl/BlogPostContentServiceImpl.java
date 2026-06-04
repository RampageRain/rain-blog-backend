package com.rain.blog.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rain.blog.backend.mapper.BlogPostContentMapper;
import com.rain.blog.backend.model.entity.BlogPost;
import com.rain.blog.backend.model.entity.BlogPostContent;
import com.rain.blog.backend.model.vo.PostDetailVO;
import com.rain.blog.backend.service.BlogPostContentService;
import com.rain.blog.backend.service.BlogPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 文章详情数据展示
 *
 * @author Rain
 * @description 针对表【blog_post_content(博客文章Markdown内容表)】的数据库操作Service实现
 * @createDate 2026-05-29 12:02:24
 */
@Service
@RequiredArgsConstructor
public class BlogPostContentServiceImpl extends ServiceImpl<BlogPostContentMapper, BlogPostContent>
        implements BlogPostContentService {

    private final BlogPostService blogPostService;

    @Override
    public PostDetailVO getPostDetail(Long id) {
        // 1. 取已发布文章主信息（状态/逻辑删除过滤在 BlogPostService 内完成），不存在则返回 null
        BlogPost post = blogPostService.getPublishedPostById(id);
        if (post == null) {
            return null;
        }

        // 2. 阅读量 +1（如不需要可删除此行）
        blogPostService.increaseViewCount(id);

        // 3. 主信息映射为详情 VO
        PostDetailVO vo = blogPostService.toPostDetailVO(post);

        // 4. 补充正文
        BlogPostContent content = lambdaQuery()
                .eq(BlogPostContent::getPostId, id)
                .one();
        vo.setContentMd(content == null ? "" : content.getContentMd());

        return vo;
    }
}
