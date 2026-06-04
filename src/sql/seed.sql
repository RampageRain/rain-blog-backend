-- ============================================================
-- 博客文章假数据种子脚本（可重复执行）
-- 用途：清空并重新灌入演示文章，验证列表分页与详情 Markdown 渲染
-- 执行：mysql -u root -p rain_blog < seed.sql  （或在客户端里直接运行）
-- 注意：会清空 blog_post / blog_post_content 现有数据
-- ============================================================

USE rain_blog;

-- 先清空旧数据：blog_post_content 通过外键引用 blog_post，
-- 按"先子表、后主表"的顺序 DELETE 天然满足外键约束，
-- 无需关闭外键检查，也不会触发 TRUNCATE 对"被引用表"的限制。
-- 带上 WHERE id > 0（主键恒正，等于匹配全部）以绕过客户端"DELETE 无 WHERE"安全警告。
DELETE FROM blog_post_content WHERE id > 0;
DELETE FROM blog_post WHERE id > 0;

-- ------------------------------------------------------------
-- 文章主表：显式指定 id，方便正文表按 post_id 关联
-- cover_key 仅可用前端已映射的 home-bg-1 ~ home-bg-6
-- status=1 已发布；is_top=1 置顶
-- ------------------------------------------------------------
INSERT INTO blog_post
(id, title, summary, slug, cover_key, category_name, status, is_top, view_count, like_count, publish_time)
VALUES
(1,  'Spring Boot + MyBatis-Plus 实现文章分页查询',
     '从零搭一个分页接口：条件构造器、置顶排序、VO 转换，再到统一返回结构，一篇讲透后端列表接口怎么写。',
     'spring-boot-mybatis-plus-pagination', 'home-bg-1', '后端开发', 1, 1, 1280, 36, '2026-05-20 09:30:00'),

(2,  'Vue3 文章详情页：Markdown 渲染与目录联动',
     '详情页如何把后端的 contentMd 渲染成带代码高亮、图片、表格的页面，并实现滚动时目录自动高亮。',
     'vue3-post-detail-markdown', 'home-bg-2', '前端开发', 1, 0, 964, 28, '2026-05-18 14:10:00'),

(3,  'MySQL 索引优化：从慢查询到毫秒响应',
     '一条 3 秒的查询如何优化到 5 毫秒？聊聊最左前缀、覆盖索引、回表，以及 EXPLAIN 怎么看。',
     'mysql-index-optimization', 'home-bg-3', '数据库', 1, 0, 2103, 75, '2026-05-15 20:45:00'),

(4,  '一次线上 OOM 排查记录',
     '半夜服务频繁重启，从 GC 日志到堆转储，一步步定位到一个被忽略的本地缓存无界增长问题。',
     'online-oom-troubleshooting', 'home-bg-4', '后端开发', 1, 0, 1567, 52, '2026-05-12 11:05:00'),

(5,  'Redis 缓存设计的三个坑',
     '缓存穿透、缓存击穿、缓存雪崩到底有什么区别？分别用什么方案兜底，附简单代码示例。',
     'redis-cache-pitfalls', 'home-bg-5', '后端开发', 1, 0, 1842, 61, '2026-05-10 16:20:00'),

(6,  'TypeScript 类型体操入门',
     '泛型、条件类型、映射类型、infer……用几个小例子带你看懂那些"看起来很唬人"的类型写法。',
     'typescript-type-gymnastics', 'home-bg-6', '前端开发', 1, 0, 733, 19, '2026-05-08 10:00:00'),

(7,  'Docker 部署 Spring Boot 全流程',
     '从写 Dockerfile、构建镜像，到用 docker compose 把后端 + MySQL + Redis 一起拉起来。',
     'docker-deploy-spring-boot', 'home-bg-1', '运维部署', 1, 0, 1099, 33, '2026-05-05 19:30:00'),

(8,  'RESTful API 设计规范小结',
     'URL 怎么取名、状态码怎么用、分页和错误结构怎么统一，整理一份团队可落地的接口规范。',
     'restful-api-guidelines', 'home-bg-2', '架构设计', 1, 0, 880, 24, '2026-05-02 09:15:00'),

(9,  'Git 工作流：从 feature 到发布',
     '一个人也值得用好分支：feature 分支、提交规范、合并策略，附常用命令速查。',
     'git-workflow-feature-to-release', 'home-bg-3', '开发工具', 1, 0, 654, 17, '2026-04-28 21:40:00'),

(10, '读《重构》后的一些笔记',
     '坏味道、小步前进、测试先行——把书里的原则落到日常写代码的几个具体习惯上。',
     'refactoring-book-notes', 'home-bg-4', '读书随笔', 1, 0, 421, 12, '2026-04-25 13:25:00'),

(11, 'CSS Grid 布局实战',
     '用 Grid 重写一个响应式卡片列表，告别一堆 float 和 flex 嵌套，代码更短也更好懂。',
     'css-grid-in-practice', 'home-bg-5', '前端开发', 1, 0, 568, 15, '2026-04-20 15:50:00'),

(12, '聊聊我的博客技术选型',
     '为什么前端选 Vue3 + Vite、后端选 Spring Boot + MyBatis-Plus，以及踩过的一些选型坑。',
     'my-blog-tech-stack', 'home-bg-6', '生活随笔', 1, 0, 990, 41, '2026-04-15 08:00:00');

-- ------------------------------------------------------------
-- 文章正文表：Markdown 内容（含标题/代码/表格/图片/列表/引用）
-- 字符串内换行直接写真实换行；遇到单引号请用两个单引号转义
-- ------------------------------------------------------------
INSERT INTO blog_post_content (post_id, content_md) VALUES
(1, '# Spring Boot + MyBatis-Plus 实现文章分页查询

做博客列表第一步，就是一个稳定好用的分页接口。这篇记录一下我的实现思路。

## 1. 接口设计

前端只需要传两个参数：当前页 `current` 和每页条数 `pageSize`，后端返回统一结构。

| 参数 | 类型 | 说明 |
| --- | --- | --- |
| current | long | 当前页码，从 1 开始 |
| pageSize | long | 每页条数，默认 9 |

## 2. 核心代码

用 MyBatis-Plus 的 `LambdaQueryWrapper` 拼条件，置顶文章排在最前：

```java
LambdaQueryWrapper<BlogPost> wrapper = new LambdaQueryWrapper<BlogPost>()
        .eq(BlogPost::getStatus, PostStatus.PUBLISHED.getCode())
        .orderByDesc(BlogPost::getIsTop)
        .orderByDesc(BlogPost::getPublishTime);

Page<BlogPost> page = page(new Page<>(current, pageSize), wrapper);
```

> 小提示：用方法引用 `BlogPost::getStatus` 而不是字符串列名，改字段名时编译期就能发现问题。

## 3. 流程图

![分页查询流程](https://picsum.photos/seed/post1/800/400)

## 4. 小结

1. 实体只暴露给数据库，对外一律转成 VO
2. 列表不返回正文，正文单独拆表，列表查询更轻
3. 返回结构统一为 `code / message / data`

这样一个分页接口就既好用又好扩展了。'),

(2, '# Vue3 文章详情页：Markdown 渲染与目录联动

后端把正文以 `contentMd` 返回，前端要做两件事：渲染 Markdown、生成可联动的目录。

## Markdown 渲染

选一个解析库把字符串转成 HTML，再做代码高亮即可：

```ts
import MarkdownIt from ''markdown-it''

const md = new MarkdownIt({ html: false, linkify: true })
const html = md.render(post.contentMd)
```

注意要对输出做一次 XSS 过滤，避免正文里被注入脚本。

## 目录联动

滚动时高亮当前章节，核心是 `IntersectionObserver`：

```ts
const observer = new IntersectionObserver((entries) => {
  entries.forEach((e) => {
    if (e.isIntersecting) activeId.value = e.target.id
  })
})
```

## 效果

![详情页截图](https://picsum.photos/seed/post2/800/420)

| 功能 | 是否完成 |
| --- | --- |
| 代码高亮 | 是 |
| 图片自适应 | 是 |
| 目录高亮 | 是 |

整体体验和掘金、语雀的阅读页就比较接近了。'),

(3, '# MySQL 索引优化：从慢查询到毫秒响应

线上一条列表查询要 3 秒，优化后只要 5 毫秒。记录一下排查过程。

## 先看执行计划

```sql
EXPLAIN SELECT id, title FROM blog_post
WHERE status = 1 AND deleted = 0
ORDER BY publish_time DESC
LIMIT 9;
```

重点看 `type` 和 `key` 两列：`type=ALL` 说明全表扫描，必须优化。

## 加联合索引

```sql
ALTER TABLE blog_post
ADD INDEX idx_status_deleted_publish (status, deleted, publish_time);
```

## 关键概念对比

| 概念 | 说明 |
| --- | --- |
| 最左前缀 | 联合索引要从最左列开始用才生效 |
| 覆盖索引 | 查询字段都在索引里，不用回表 |
| 回表 | 通过索引找到主键，再去主键索引取整行 |

> 优化不是盲目加索引，先用 EXPLAIN 找瓶颈，再针对性地建。

![执行计划对比](https://picsum.photos/seed/post3/800/400)

最终 `type` 从 `ALL` 变成 `range`，响应时间直接降到毫秒级。'),

(4, '# 一次线上 OOM 排查记录

某天凌晨服务频繁重启，监控显示堆内存持续上涨直到爆掉。

## 排查步骤

1. 先看 GC 日志，发现 Full GC 越来越频繁但回收不掉
2. 导出堆转储 `jmap -dump:live,format=b,file=heap.hprof <pid>`
3. 用 MAT 打开，按对象占用排序

## 根因

一个本地 `HashMap` 当缓存用，只往里放从不清理，时间一长就把堆撑满了。

```java
// 反例：无界缓存
private static final Map<Long, Post> CACHE = new HashMap<>();
```

改成有容量上限、带过期的缓存后问题消失：

```java
Cache<Long, Post> cache = Caffeine.newBuilder()
        .maximumSize(1000)
        .expireAfterWrite(Duration.ofMinutes(10))
        .build();
```

> 教训：任何"全局缓存"都要问一句——它会不会无限增长？

排查 OOM 的关键是拿到堆转储，剩下的就是顺着大对象找引用链。'),

(5, '# Redis 缓存设计的三个坑

缓存用不好，反而会把数据库压垮。这里说三个最常见的问题。

## 缓存穿透

查一个根本不存在的 key，每次都打到数据库。解决：把空结果也缓存一小段时间，或用布隆过滤器。

## 缓存击穿

热点 key 突然过期，瞬间大量请求涌向数据库。解决：加互斥锁，只放一个请求去重建缓存。

```java
if (redis.setIfAbsent(lockKey, "1", Duration.ofSeconds(10))) {
    // 抢到锁，去查库并回写缓存
}
```

## 缓存雪崩

大量 key 在同一时刻过期。解决：给过期时间加随机抖动。

| 问题 | 现象 | 方案 |
| --- | --- | --- |
| 穿透 | 查不存在的数据 | 空值缓存 / 布隆过滤器 |
| 击穿 | 单个热点失效 | 互斥锁重建 |
| 雪崩 | 大批同时失效 | 过期时间加随机值 |

> 三者经常被搞混，记住区别在于"失效的 key 有几个、存不存在"。'),

(6, '# TypeScript 类型体操入门

类型体操听起来玄乎，其实就是用类型表达逻辑。几个例子就懂了。

## 泛型

```ts
function identity<T>(value: T): T {
  return value
}
```

## 条件类型 + infer

从函数类型里把返回值类型"抠"出来：

```ts
type ReturnTypeOf<T> = T extends (...args: any[]) => infer R ? R : never
```

## 映射类型

把一个类型的所有字段变成可选：

```ts
type Partial<T> = { [K in keyof T]?: T[K] }
```

| 工具类型 | 作用 |
| --- | --- |
| Partial | 全部变可选 |
| Required | 全部变必填 |
| Pick | 挑几个字段 |
| Omit | 去掉几个字段 |

多写几次就会发现，类型体操的本质是"在类型层面做 if/else 和遍历"。'),

(7, '# Docker 部署 Spring Boot 全流程

本地跑得好好的，部署到服务器又是另一回事。用 Docker 能省很多环境的麻烦。

## 写 Dockerfile

```dockerfile
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

## 一键拉起依赖

用 compose 把后端、MySQL、Redis 编排在一起：

```yaml
services:
  app:
    build: .
    ports:
      - "8080:8080"
  mysql:
    image: mysql:8
  redis:
    image: redis:7
```

> 镜像构建建议用多阶段构建，把编译和运行分开，最终镜像更小。

部署流程跑通后，发布就只剩 `docker compose up -d` 一条命令。'),

(8, '# RESTful API 设计规范小结

接口风格统一，前后端协作才顺畅。整理一份能落地的规范。

## URL 命名

- 用名词复数：`/posts`、`/posts/{id}`
- 不要把动词塞进 URL：用 HTTP 方法表达动作

## 状态码

| 场景 | 状态码 |
| --- | --- |
| 成功 | 200 |
| 创建成功 | 201 |
| 参数错误 | 400 |
| 未认证 | 401 |
| 找不到 | 404 |

## 统一返回结构

```json
{
  "code": 200,
  "message": "success",
  "data": {}
}
```

> 业务错误可以放在 body 的 code 里，HTTP 状态码保持语义清晰。

规范的价值在于"可预测"，新人看一眼就知道接口长什么样。'),

(9, '# Git 工作流：从 feature 到发布

一个人写项目也值得有规矩，分支清楚了回滚和排查都轻松。

## 基本流程

1. 从主分支切出 `feature/xxx`
2. 小步提交，提交信息遵循 Conventional Commits
3. 完成后合并回主分支

```bash
git switch -c feature/post-detail
git add .
git commit -m "feat: 新增文章详情接口"
git switch main
git merge --no-ff feature/post-detail
```

## 常用命令速查

| 命令 | 作用 |
| --- | --- |
| git status | 看当前改动 |
| git switch -c | 新建并切换分支 |
| git log --oneline | 简洁查看历史 |

> 提交信息写清楚"为什么改"，比"改了什么"更有价值。'),

(10, '# 读《重构》后的一些笔记

《重构》这本书改变了我看待代码的方式，记几条印象最深的。

## 坏味道

代码里那些让你皱眉的地方都有名字：重复代码、过长函数、过大的类……识别它们是重构的第一步。

## 小步前进

> 重构是一系列小到不会出错的改动，每改一步都跑测试。

不要一次性大改，那样出了问题根本不知道是哪一步。

## 几个落地习惯

1. 函数尽量短，一个函数只做一件事
2. 命名要能"读出意图"，别怕名字长
3. 改之前先补测试，给自己一张安全网

读完最大的感受是：重构不是大动干戈，而是日常的小习惯。'),

(11, '# CSS Grid 布局实战

用 Grid 重写卡片列表，比一堆 flex 嵌套清爽太多。

## 三列自适应

```css
.list {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 24px;
}
```

## 响应式

配合 `auto-fill` 和 `minmax`，列数随容器宽度自动变化：

```css
.list {
  grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
}
```

| 属性 | 作用 |
| --- | --- |
| grid-template-columns | 定义列 |
| gap | 行列间距 |
| minmax | 设定最小/最大尺寸 |

> 一维布局用 flex，二维布局用 grid，按场景选就好。

写完发现，原来要几十行的布局，现在几行就搞定了。'),

(12, '# 聊聊我的博客技术选型

经常被问为什么这么选，干脆写一篇说清楚。

## 前端：Vue3 + Vite

- 组合式 API 写复杂逻辑更顺手
- Vite 启动快，开发体验好
- 生态成熟，组件库随便挑

## 后端：Spring Boot + MyBatis-Plus

- Spring Boot 上手快，约定优于配置
- MyBatis-Plus 把单表 CRUD 几乎全包了

```java
// 单表查询基本不用写 SQL
List<BlogPost> list = blogPostService.list();
```

## 踩过的坑

| 坑 | 教训 |
| --- | --- |
| 字符集用了 utf8 | emoji 存不进去，要用 utf8mb4 |
| 图片塞进数据库 | 行变大查询慢，应存 URL |

> 选型没有最好，只有最适合当前阶段的。

折腾的过程本身，也是这个博客最有意思的部分。');
