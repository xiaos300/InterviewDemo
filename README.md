## 开发日志
2026.5.11  
创建项目，添加：Sring-web、lombok、mysql-driver、Spring-data-jpa依赖
进行数据库设计、流程分析  
2026.5.12  
创建实体类（Answer、Category、InterviewSession、InterviewSessionQuestion、Question、Report、User）
创建Repository接口  
todo:
- [x] 通用返回类Result
- [x] 业务异常类BusinessException  
- [x] 全局日志配置log4j
- [ ] 使用Spring-security（BCryptPasswordEncoder加密） + JWT 做校验  
  - [x] UserRepository 增加 existsByUsername 和 findByUsername
  - [x] 写用户模块DTO
  - [x] 处理密码加密
  - [ ] 登录后存储用户id


