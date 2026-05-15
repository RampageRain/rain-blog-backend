CREATE DATABASE rain_blog
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE rain_blog;

CREATE TABLE blog_admin (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',

                            username VARCHAR(50) NOT NULL UNIQUE COMMENT '管理员用户名',
                            password_hash VARCHAR(100) NOT NULL COMMENT '密码哈希',
                            nickname VARCHAR(50) DEFAULT 'Rain' COMMENT '昵称',
                            avatar VARCHAR(255) DEFAULT NULL COMMENT '头像',

                            status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1启用，0禁用',

                            last_login_time DATETIME DEFAULT NULL COMMENT '最后登录时间',
                            create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci
    COMMENT='博客后台管理员表';

CREATE TABLE blog_post (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '文章ID',

                           title VARCHAR(120) NOT NULL COMMENT '文章标题',
                           summary VARCHAR(500) NOT NULL DEFAULT '' COMMENT '文章摘要，用于文章列表卡片展示',

                           slug VARCHAR(160) DEFAULT NULL COMMENT '文章访问别名，预留字段，后续可用于更美观的URL',

                           cover_key VARCHAR(80) NOT NULL DEFAULT 'home-bg-1' COMMENT '封面图标识，后端保存key，前端根据key映射到本地图片',

                           category_id BIGINT DEFAULT NULL COMMENT '分类ID，预留字段，后续关联分类表',
                           category_name VARCHAR(60) NOT NULL DEFAULT '未分类' COMMENT '分类名称快照，第一阶段可直接用于前端展示',

                           status TINYINT NOT NULL DEFAULT 0 COMMENT '文章状态：0草稿，1已发布，2隐藏',
                           is_top TINYINT NOT NULL DEFAULT 0 COMMENT '是否置顶：0否，1是',

                           view_count BIGINT NOT NULL DEFAULT 0 COMMENT '阅读量',
                           like_count BIGINT NOT NULL DEFAULT 0 COMMENT '点赞数，预留字段',

                           publish_time DATETIME DEFAULT NULL COMMENT '发布时间，文章发布后才有值',
                           create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

                           deleted TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0未删除，1已删除',

                           UNIQUE KEY uk_blog_post_slug (slug),
                           KEY idx_blog_post_status_deleted_publish_time (status, deleted, publish_time),
                           KEY idx_blog_post_is_top_publish_time (is_top, publish_time),
                           KEY idx_blog_post_category_id (category_id)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci
    COMMENT='博客文章主表';


CREATE TABLE blog_post_content (
                                   id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '文章内容ID',

                                   post_id BIGINT NOT NULL COMMENT '文章ID，对应blog_post.id',
                                   content_md LONGTEXT NOT NULL COMMENT 'Markdown格式的文章正文',

                                   create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

                                   UNIQUE KEY uk_blog_post_content_post_id (post_id),
                                   CONSTRAINT fk_blog_post_content_post_id
                                       FOREIGN KEY (post_id) REFERENCES blog_post (id)
                                           ON DELETE CASCADE
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci
    COMMENT='博客文章Markdown内容表';