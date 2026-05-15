package com.rain.blog.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rain.blog.backend.model.entity.BlogPost;
import com.rain.blog.backend.service.BlogPostService;
import com.rain.blog.backend.mapper.BlogPostMapper;
import org.springframework.stereotype.Service;

/**
* @author Mova
* @description 针对表【blog_post(博客文章主表)】的数据库操作Service实现
* @createDate 2026-05-15 18:25:59
*/
@Service
public class BlogPostServiceImpl extends ServiceImpl<BlogPostMapper, BlogPost>
    implements BlogPostService{

}




