# rain-blog-backend

Rain Blog 后端服务，基于 Spring Boot 3、MyBatis-Plus、MySQL 和 Redis。

## 技术栈

- Java 17
- Spring Boot 3.5
- MyBatis-Plus 3.5
- MySQL 8
- Redis
- Lombok
- Knife4j

## 功能概览

- 管理员登录
- 基于 Redis 的 token 登录态存储
- MyBatis-Plus 数据访问
- Knife4j 接口文档

## 目录结构

```text
src/main/java/com/rain/blog/backend
├─ common
├─ config
├─ controller
├─ interceptor
├─ mapper
├─ model
│  ├─ dto
│  ├─ entity
│  └─ vo
└─ service
```

## 环境要求

- JDK 17
- Maven 3.9+ 或使用项目自带 `mvnw`
- MySQL 8+
- Redis 6+

## 本地运行

1. 创建数据库 `rain_blog`
2. 执行初始化脚本 [src/sql/init.sql](/D:/Blog/rain-blog-backend/src/sql/init.sql:1)
3. 修改 [application.yaml](/D:/Blog/rain-blog-backend/src/main/resources/application.yaml:1) 中的数据库和 Redis 配置
4. 启动应用

```bash
./mvnw spring-boot:run
```

Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

默认端口：

```text
http://localhost:8080
```

## 配置说明

核心配置文件：

- [application.yaml](/D:/Blog/rain-blog-backend/src/main/resources/application.yaml:1)

默认配置包含：

- MySQL：`localhost:3306/rain_blog`
- Redis：`localhost:6379`
- 服务端口：`8080`

提交到公开仓库前，建议替换默认数据库账号密码。

## 接口说明

登录接口：

```http
POST /api/auth/login
Content-Type: application/json
```

请求示例：

```json
{
  "username": "admin",
  "password": "123456"
}
```

成功响应示例：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "token": "xxxx",
    "username": "admin",
    "nickname": "Rain",
    "avatar": null
  }
}
```

## 接口文档

项目集成了 Knife4j，启动后可访问：

```text
http://localhost:8080/doc.html
```

## 当前说明

- 登录成功后，token 会写入 Redis，默认过期时间为 2 小时
- Mapper XML 位于 `src/main/resources/generator/mapper`
- 若前端联调，默认允许 `http://localhost:5173` 跨域访问

## 后续建议

- 使用环境变量或独立配置文件管理数据库密码
- 增加统一异常处理和日志脱敏
- 增加更多业务接口和测试用例
