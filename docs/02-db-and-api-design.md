# 数据库表设计与第一批接口设计

## 1. 设计原则

这一版只做“够用、清晰、便于实现”的设计。

原则：

1. 表结构尽量直观
2. 字段命名统一
3. 不追求一步到位的复杂建模
4. 先保证主流程能跑通

## 2. 数据库表设计

建议数据库名：

`interview_practice`

建议统一字段：

- `id`：主键
- `create_time`：创建时间
- `update_time`：更新时间

---

### 2.1 app_user

用途：保存用户账号信息。

| 字段名 | 类型 | 说明 |
| --- | --- | --- |
| id | bigint | 主键 |
| username | varchar(50) | 用户名，唯一 |
| password | varchar(100) | 加密后的密码 |
| nickname | varchar(50) | 昵称 |
| create_time | datetime | 创建时间 |
| update_time | datetime | 更新时间 |
| last_login_time | datetime | 上次登录时间 |

说明：

- 用户名用于登录
- 密码必须存密文，不能明文
- 第一版不做手机号、邮箱、头像

---

### 2.2 question

用途：保存题库题目。

| 字段名 | 类型 | 说明 |
| --- | --- | --- |
| id | bigint | 主键 |
| user_id | bigint | 所属用户 id |
| content | text | 题目内容 |
| category_id | bigint | 分类 id |
| difficulty | tinyint | 难度，1-简单，2-中等，3-困难 |
| source | varchar(100) | 来源，如牛客、LeetCode、手动录入 |
| create_time | datetime | 创建时间 |
| update_time | datetime | 更新时间 |

说明：

- 第一版只保留题干，不单独拆标题
- `category_id` 关联分类表，避免用户每次手输分类
- `source` 表示题目来源，和分类含义不同

---

### 2.3 category

用途：保存题目分类，供用户选择。

| 字段名 | 类型 | 说明 |
| --- | --- | --- |
| id | bigint | 主键 |
| name | varchar(50) | 分类名称，如 Java、MySQL |
| description | varchar(255) | 分类说明，可选 |
| create_time | datetime | 创建时间 |
| update_time | datetime | 更新时间 |

说明：

- 第一版分类表可以先做成全局公共分类
- 这样题目录入时直接选择分类，更规范

---

### 2.4 interview_session

用途：保存一次模拟面试的基本信息。

| 字段名 | 类型 | 说明 |
| --- | --- | --- |
| id | bigint | 主键 |
| user_id | bigint | 用户 id |
| title | varchar(100) | 面试标题 |
| status | varchar(20) | 状态：NOT_STARTED / IN_PROGRESS / FINISHED |
| question_count | int | 本场面试题目数 |
| start_time | datetime | 开始时间 |
| create_time | datetime | 创建时间 |
| update_time | datetime | 更新时间 |

说明：

- `status` 用字符串更直观，面试时也好解释
- 第一版可以不单独保存结束时间，完成状态由 `status` 表示

---

### 2.5 interview_session_question

用途：保存某场面试抽到了哪些题。

| 字段名 | 类型 | 说明 |
| --- | --- | --- |
| id | bigint | 主键 |
| session_id | bigint | 面试 id |
| question_id | bigint | 题目 id |
| question_order | int | 第几题 |
| create_time | datetime | 创建时间 |

说明：

- 这是“面试”和“题目”的关联表
- 用 `question_order` 保存出题顺序

---

### 2.6 answer_record

用途：保存用户每道题的回答记录。

| 字段名 | 类型 | 说明 |
| --- | --- | --- |
| id | bigint | 主键 |
| session_id | bigint | 面试 id |
| session_question_id | bigint | 面试题目关联 id |
| user_id | bigint | 用户 id |
| answer_text | text | 用户回答内容 |
| answer_duration_seconds | int | 作答耗时，单位秒 |
| create_time | datetime | 创建时间 |
| update_time | datetime | 更新时间 |

说明：

- 一道题对应一条答题记录
- 第一版只存文本回答，不做语音

---

### 2.7 report

用途：保存 AI 点评结果。

| 字段名 | 类型 | 说明 |
| --- | --- | --- |
| id | bigint | 主键 |
| session_id | bigint | 面试 id |
| user_id | bigint | 用户 id |
| overall_score | int | 总体分数，可选 |
| strengths | text | 优点总结 |
| weaknesses | text | 不足总结 |
| suggestions | text | 改进建议 |
| raw_ai_response | text | AI 原始返回，可选 |
| create_time | datetime | 创建时间 |
| update_time | datetime | 更新时间 |

说明：

- 第一版报告表尽量简单
- 后续如果要做更复杂的维度评分，再扩展字段

## 3. 建表 SQL

```sql
CREATE TABLE app_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50) NOT NULL,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE question (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    category_id BIGINT NOT NULL,
    difficulty TINYINT NOT NULL,
    source VARCHAR(100),
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255),
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE interview_session (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    title VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL,
    question_count INT NOT NULL,
    start_time DATETIME,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE interview_session_question (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    session_id BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    question_order INT NOT NULL,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE answer_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    session_id BIGINT NOT NULL,
    session_question_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    answer_text TEXT NOT NULL,
    answer_duration_seconds INT DEFAULT 0,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE report (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    session_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    overall_score INT,
    strengths TEXT,
    weaknesses TEXT,
    suggestions TEXT,
    raw_ai_response TEXT,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

## 4. 第一批接口设计

补充说明：

1. 第一版可以先不加数据库外键约束，先保证开发顺畅
2. 但要在业务代码里检查数据归属关系
3. 常用查询字段后续建议加索引，如 `username`、`user_id`、`session_id`、`create_time`

接口风格建议统一：

- 前缀：`/api`
- 返回 JSON
- 登录后通过 JWT 传 token

---

### 4.1 用户模块

#### 1. 注册

- `POST /api/auth/register`

请求体：

```json
{
  "username": "zhangsan",
  "password": "123456",
  "nickname": "张三"
}
```

#### 2. 登录

- `POST /api/auth/login`

请求体：

```json
{
  "username": "zhangsan",
  "password": "123456"
}
```

返回示例：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "token": "jwt-token",
    "userInfo": {
      "id": 1,
      "username": "zhangsan",
      "nickname": "张三"
    }
  }
}
```

#### 3. 获取当前用户信息

- `GET /api/auth/me`

#### 4. 登出

- `POST /api/auth/logout`

说明：

- JWT 项目里登出通常是前端删 token
- 第一版后端可以先返回成功，便于流程完整

---

### 4.2 题库模块

#### 1. 新增题目

- `POST /api/questions`

#### 2. 分页查询题目

- `GET /api/questions`

建议查询参数：

- `keyword`
- `categoryId`
- `difficulty`
- `source`
- `page`
- `size`

示例：

`GET /api/questions?keyword=线程&categoryId=1&difficulty=2&page=1&size=10`

#### 3. 查询题目详情

- `GET /api/questions/{id}`

#### 4. 修改题目

- `PUT /api/questions/{id}`

#### 5. 删除题目

- `DELETE /api/questions/{id}`

---

### 4.3 模拟面试模块

#### 1. 创建面试

- `POST /api/interviews`

请求体建议：

```json
{
  "title": "Java 基础模拟面试",
  "questionCount": 5,
  "categoryId": 1,
  "difficulty": 2,
  "source": "牛客"
}
```

说明：

- 创建时根据筛选条件从题库抽题
- 第一版可以先做“随机抽题”

#### 2. 获取面试详情

- `GET /api/interviews/{id}`

返回：

- 面试基本信息
- 本场抽到的题目列表

#### 3. 提交某题回答

- `POST /api/interviews/{id}/answers`

请求体：

```json
{
  "sessionQuestionId": 1,
  "answerText": "我的回答内容",
  "answerDurationSeconds": 180
}
```

#### 4. 完成面试

- `POST /api/interviews/{id}/finish`

---

### 4.4 分类模块

#### 1. 查询分类列表

- `GET /api/categories`

#### 2. 新增分类

- `POST /api/categories`

#### 3. 修改分类

- `PUT /api/categories/{id}`

#### 4. 删除分类

- `DELETE /api/categories/{id}`

说明：

- 如果你想再简化一点，第一版也可以只做“查询分类列表”
- 新增、修改、删除可以先留给题库管理员能力，后面再补

---

### 4.5 历史记录模块

#### 1. 分页查询历史面试记录

- `GET /api/history/interviews`

建议查询参数：

- `page`
- `size`
- `startDate`
- `endDate`

#### 2. 查询某次历史面试详情

- `GET /api/history/interviews/{id}`

返回内容建议包括：

1. 面试基本信息
2. 题目列表
3. 每题回答记录
4. AI 报告

---

### 4.6 点评报告模块

#### 1. 生成 AI 点评报告

- `POST /api/reports/generate/{sessionId}`

说明：

- 根据该场面试所有答题记录拼接 prompt
- 调用 AI 接口生成点评
- 结果落库到 `report`

#### 2. 获取报告详情

- `GET /api/reports/{sessionId}`

## 5. 推荐先做的接口顺序

不要并行做太多，建议按这个顺序：

1. 注册
2. 登录
3. 获取当前用户信息
4. 新增题目
5. 分页查询题目
6. 创建面试
7. 提交回答
8. 完成面试
9. 查看历史
10. 生成报告

## 6. 这一阶段面试时可以怎么讲

可以这样讲：

“在项目设计阶段，我先把核心业务抽象成 6 张主表，分别覆盖用户、题库、面试流程、答题记录和 AI 报告。表设计上我刻意避免过度复杂，比如标签先用字符串存储，报告先保存结构化总结字段，优先保证主流程可落地。接口设计上，我按照真实业务顺序拆成用户、题库、模拟面试、历史记录、报告五个模块，便于后续分阶段实现和测试。”
