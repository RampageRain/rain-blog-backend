CREATE DATABASE IF NOT EXISTS rain_blog
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE rain_blog;

CREATE TABLE IF NOT EXISTS blog_admin (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL UNIQUE,
  password_hash VARCHAR(100) NOT NULL,
  nickname VARCHAR(50) DEFAULT 'Rain',
  avatar VARCHAR(255) DEFAULT NULL,
  status TINYINT NOT NULL DEFAULT 1,
  last_login_time DATETIME DEFAULT NULL,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS blog_post (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(120) NOT NULL,
  summary VARCHAR(500) NOT NULL DEFAULT '',
  slug VARCHAR(160) DEFAULT NULL,
  cover_key VARCHAR(80) NOT NULL DEFAULT 'home-bg-1',
  category_id BIGINT DEFAULT NULL,
  category_name VARCHAR(60) NOT NULL DEFAULT 'uncategorized',
  status TINYINT NOT NULL DEFAULT 0,
  is_top TINYINT NOT NULL DEFAULT 0,
  view_count BIGINT NOT NULL DEFAULT 0,
  like_count BIGINT NOT NULL DEFAULT 0,
  publish_time DATETIME DEFAULT NULL,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  deleted TINYINT NOT NULL DEFAULT 0,
  UNIQUE KEY uk_blog_post_slug (slug),
  KEY idx_blog_post_status_deleted_publish_time (status, deleted, publish_time),
  KEY idx_blog_post_is_top_publish_time (is_top, publish_time),
  KEY idx_blog_post_category_id (category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS blog_post_content (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  post_id BIGINT NOT NULL,
  content_md LONGTEXT NOT NULL,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uk_blog_post_content_post_id (post_id),
  CONSTRAINT fk_blog_post_content_post_id
    FOREIGN KEY (post_id) REFERENCES blog_post (id)
    ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
