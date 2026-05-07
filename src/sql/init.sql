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
