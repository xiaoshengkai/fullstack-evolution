# MySQL 学习指南 - Todo 应用数据库操作

## 1. 连接数据库

### 基本连接
```sql
-- 无需密码连接（需配置）
mysql -u root

-- 需要密码连接
mysql -u root -p
```

## 2. 数据库操作

### 创建数据库
```sql
-- 创建数据库（推荐使用 utf8mb4 字符集支持中文）
CREATE DATABASE java_learning 
    CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE java_learning;
```

### 查看数据库
```sql
-- 查看所有数据库
SHOW DATABASES;
```

## 3. 表操作

### 创建 todo_item 表
```sql
CREATE TABLE todo_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    priority VARCHAR(10) NOT NULL,
    deadline DATE,
    done TINYINT(1) DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP 
        ON UPDATE CURRENT_TIMESTAMP
);
```

### 查看表信息
```sql
-- 查看所有表
SHOW TABLES;

-- 查看表详细信息
SHOW TABLE STATUS;
```

### 插入测试数据
```sql
INSERT INTO todo_item (title, priority, deadline) VALUES
('学习 MySQL 基础', 'HIGH', '2026-06-10'),
('完成 Spring Boot 环境搭建', 'HIGH', '2026-06-05'),
('阅读 MyBatis-Plus 文档', 'MEDIUM', '2026-06-15');
```

## 4. SELECT 查询

### 基本查询
```sql
-- 查询所有字段
SELECT * FROM todo_item;
```

**查询结果：**

| id | title | priority | deadline | done | create_time | update_time |
|----|-------|----------|----------|------|-------------|-------------|
| 1 | 学习 MySQL 基础 | HIGH | 2026-06-10 | 0 | 2026-06-02 11:18:12 | 2026-06-02 11:18:12 |
| 2 | 完成 Spring Boot 环境搭建 | HIGH | 2026-06-05 | 0 | 2026-06-02 11:18:12 | 2026-06-02 11:18:12 |
| 3 | 阅读 MyBatis-Plus 文档 | MEDIUM | 2026-06-15 | 0 | 2026-06-02 11:18:12 | 2026-06-02 11:18:12 |

### 指定字段查询
```sql
-- 查询特定字段
SELECT title, priority FROM todo_item;
```

**查询结果：**

| title | priority |
|-------|----------|
| 学习 MySQL 基础 | HIGH |
| 完成 Spring Boot 环境搭建 | HIGH |
| 阅读 MyBatis-Plus 文档 | MEDIUM |

### 去重查询
```sql
-- DISTINCT 去重
SELECT DISTINCT priority, title, done, deadline FROM todo_item;
```

**查询结果：**

| priority | title | done | deadline |
|----------|-------|------|----------|
| HIGH | 学习 MySQL 基础 | 0 | 2026-06-10 |
| HIGH | 完成 Spring Boot 玎境搭建 | 0 | 2026-06-05 |
| MEDIUM | 阅读 MyBatis-Plus 文档 | 0 | 2026-06-15 |

## 5. WHERE 条件查询

### 基本条件
```sql
-- 等值查询
SELECT * FROM todo_item WHERE priority = 'HIGH';
```

**查询结果：**

| id | title | priority | deadline | done | create_time | update_time |
|----|-------|----------|----------|------|-------------|-------------|
| 1 | 学习 MySQL 基础 | HIGH | 2026-06-10 | 0 | 2026-06-02 11:18:12 | 2026-06-02 11:18:12 |
| 2 | 完成 Spring Boot 环境搭建 | HIGH | 2026-06-05 | 0 | 2026-06-02 11:18:12 | 2026-06-02 11:18:12 |

### 多条件查询
```sql
-- AND 条件
SELECT * FROM todo_item 
WHERE priority = 'HIGH' AND title = '学习 MySQL 基础';
```

**查询结果：**

| id | title | priority | deadline | done | create_time | update_time |
|----|-------|----------|----------|------|-------------|-------------|
| 1 | 学习 MySQL 基础 | HIGH | 2026-06-10 | 0 | 2026-06-02 11:18:12 | 2026-06-02 11:18:12 |

### IN 条件
```sql
-- IN 查询
SELECT * FROM todo_item 
WHERE priority IN ('HIGH', 'MEDIUM');
```

**查询结果：**

| id | title | priority | deadline | done | create_time | update_time |
|----|-------|----------|----------|------|-------------|-------------|
| 1 | 学习 MySQL 基础 | HIGH | 2026-06-10 | 0 | 2026-06-02 11:18:12 | 2026-06-02 11:18:12 |
| 2 | 完成 Spring Boot 环境搭建 | HIGH | 2026-06-05 | 0 | 2026-06-02 11:18:12 | 2026-06-02 11:18:12 |
| 3 | 阅读 MyBatis-Plus 文档 | MEDIUM | 2026-06-15 | 0 | 2026-06-02 11:18:12 | 2026-06-02 11:18:12 |

### BETWEEN 范围查询
```sql
-- 范围查询
SELECT * FROM todo_item 
WHERE deadline BETWEEN '2026-06-01' AND '2026-06-10';
```

**查询结果：**

| id | title | priority | deadline | done | create_time | update_time |
|----|-------|----------|----------|------|-------------|-------------|
| 1 | 学习 MySQL 基础 | HIGH | 2026-06-10 | 0 | 2026-06-02 11:18:12 | 2026-06-02 11:18:12 |
| 2 | 完成 Spring Boot 环境搭建 | HIGH | 2026-06-05 | 0 | 2026-06-02 11:18:12 | 2026-06-02 11:18:12 |

### LIKE 模糊查询
```sql
-- 模糊查询
SELECT * FROM todo_item 
WHERE title LIKE '%学习%';
```

**查询结果：**

| id | title | priority | deadline | done | create_time | update_time |
|----|-------|----------|----------|------|-------------|-------------|
| 1 | 学习 MySQL 基础 | HIGH | 2026-06-10 | 0 | 2026-06-02 11:18:12 | 2026-06-02 11:18:12 |

## 6. 排序与分页

### 排序查询
```sql
-- 升序排序
SELECT * FROM todo_item 
ORDER BY deadline ASC;
```

**查询结果：**

| id | title | priority | deadline | done | create_time | update_time |
|----|-------|----------|----------|------|-------------|-------------|
| 2 | 完成 Spring Boot 环境搭建 | HIGH | 2026-06-05 | 0 | 2026-06-02 11:18:12 | 2026-06-02 11:18:12 |
| 1 | 学习 MySQL 基础 | HIGH | 2026-06-10 | 0 | 2026-06-02 11:18:12 | 2026-06-02 11:18:12 |
| 3 | 阅读 MyBatis-Plus 文档 | MEDIUM | 2026-06-15 | 0 | 2026-06-02 11:18:12 | 2026-06-02 11:18:12 |

```sql
-- 降序排序
SELECT * FROM todo_item 
ORDER BY deadline DESC;
```

**查询结果：**

| id | title | priority | deadline | done | create_time | update_time |
|----|-------|----------|----------|------|-------------|-------------|
| 3 | 阅读 MyBatis-Plus 文档 | MEDIUM | 2026-06-15 | 0 | 2026-06-02 11:18:12 | 2026-06-02 11:18:12 |
| 1 | 学习 MySQL 基础 | HIGH | 2026-06-10 | 0 | 2026-06-02 11:18:12 | 2026-06-02 11:18:12 |
| 2 | 完成 Spring Boot 环境搭建 | HIGH | 2026-06-05 | 0 | 2026-06-02 11:18:12 | 2026-06-02 11:18:12 |

```sql
-- 多列排序
SELECT * FROM todo_item 
ORDER BY deadline DESC, priority ASC;
```

**查询结果：**

| id | title | priority | deadline | done | create_time | update_time |
|----|-------|----------|----------|------|-------------|-------------|
| 3 | 阅读 MyBatis-Plus 文档 | MEDIUM | 2026-06-15 | 0 | 2026-06-02 11:18:12 | 2026-06-02 11:18:12 |
| 1 | 学习 MySQL 基础 | HIGH | 2026-06-10 | 0 | 2026-06-02 11:18:12 | 2026-06-02 11:18:12 |
| 2 | 完成 Spring Boot 环境搭建 | HIGH | 2026-06-05 | 0 | 2026-06-02 11:18:12 | 2026-06-02 11:18:12 |

### 分页查询
```sql
-- 前 N 条
SELECT * FROM todo_item LIMIT 2;
```

**查询结果：**

| id | title | priority | deadline | done | create_time | update_time |
|----|-------|----------|----------|------|-------------|-------------|
| 1 | 学习 MySQL 基础 | HIGH | 2026-06-10 | 0 | 2026-06-02 11:18:12 | 2026-06-02 11:18:12 |
| 2 | 完成 Spring Boot 环境搭建 | HIGH | 2026-06-05 | 0 | 2026-06-02 11:18:12 | 2026-06-02 11:18:12 |

```sql
-- 分页查询（跳过 offset，取 count 条）
SELECT * FROM todo_item LIMIT 1, 2;
```

**查询结果：**

| id | title | priority | deadline | done | create_time | update_time |
|----|-------|----------|----------|------|-------------|-------------|
| 2 | 完成 Spring Boot 环境搭建 | HIGH | 2026-06-05 | 0 | 2026-06-02 11:18:12 | 2026-06-02 11:18:12 |
| 3 | 阅读 MyBatis-Plus 文档 | MEDIUM | 2026-06-15 | 0 | 2026-06-02 11:18:12 | 2026-06-02 11:18:12 |

## 7. 聚合函数与分组

### 基本聚合函数
```sql
-- 统计总数
SELECT COUNT(*) FROM todo_item;
```

**查询结果：**

| COUNT(*) |
|----------|
| 3 |

```sql
-- 带条件统计
SELECT COUNT(*) FROM todo_item WHERE done = 0;
```

**查询结果：**

| COUNT(*) |
|----------|
| 3 |

```sql
-- 最大值
SELECT MAX(deadline) FROM todo_item;
```

**查询结果：**

| MAX(deadline) |
|---------------|
| 2026-06-15 |

```sql
-- 最小值
SELECT MIN(deadline) FROM todo_item;
```

**查询结果：**

| MIN(deadline) |
|---------------|
| 2026-06-05 |

### 分组统计
```sql
-- 按优先级分组统计
SELECT priority, COUNT(*) AS count 
FROM todo_item 
GROUP BY priority;
```

**查询结果：**

| priority | count |
|----------|-------|
| HIGH | 2 |
| MEDIUM | 1 |

```sql
-- 分组后筛选
SELECT priority, COUNT(*) AS count 
FROM todo_item 
GROUP BY priority 
HAVING count >= 2;
```

**查询结果：**

| priority | count |
|----------|-------|
| HIGH | 2 |

## 8. 多表连接

### 创建配置表
```sql
-- 创建优先级配置表
CREATE TABLE priority_config (
    priority VARCHAR(10) PRIMARY KEY,
    level_name VARCHAR(20),
    sort_order INT
);

-- 插入配置数据
INSERT INTO priority_config VALUES 
('HIGH', '紧急', 3), 
('MEDIUM', '中等', 2), 
('LOW', '低优', 1);
```

### 内连接
```sql
-- 内连接（取交集）
SELECT t.title, t.priority, c.level_name 
FROM todo_item t 
INNER JOIN priority_config c ON t.priority = c.priority;
```

**查询结果：**

| title | priority | level_name |
|-------|----------|------------|
| 学习 MySQL 基础 | HIGH | 紧急 |
| 完成 Spring Boot 环境搭建 | HIGH | 紧急 |
| 阅读 MyBatis-Plus 文档 | MEDIUM | 中等 |

### 左连接
```sql
-- 左连接（保留左表全部）
SELECT t.title, t.priority, c.level_name 
FROM todo_item t 
LEFT JOIN priority_config c ON t.priority = c.priority;
```

**查询结果：**

| title | priority | level_name |
|-------|----------|------------|
| 学习 MySQL 基础 | HIGH | 紧急 |
| 完成 Spring Boot 环境搭建 | HIGH | 紧急 |
| 阅读 MyBatis-Plus 文档 | MEDIUM | 中等 |

## 9. 子查询

### WHERE 子查询
```sql
-- 查询完成数量最多的优先级
SELECT priority, COUNT(*) AS cnt 
FROM todo_item 
GROUP BY priority 
HAVING cnt = (
    SELECT MAX(cnt) 
    FROM (
        SELECT COUNT(*) AS cnt 
        FROM todo_item 
        GROUP BY priority
    ) t
);
```

**查询结果：**

| priority | cnt |
|----------|-----|
| HIGH | 2 |

### FROM 子查询
```sql
-- FROM 子查询
SELECT * 
FROM (
    SELECT title, priority 
    FROM todo_item 
    WHERE done = 0
) undone;
```

**查询结果：**

| title | priority |
|-------|----------|
| 学习 MySQL 基础 | HIGH |
| 完成 Spring Boot 环境搭建 | HIGH |
| 阅读 MyBatis-Plus 文档 | MEDIUM |

## 📊 快速参考

| 操作类型 | 示例 | 说明 |
|---------|------|------|
| **创建数据库** | `CREATE DATABASE db_name` | 创建数据库 |
| **创建表** | `CREATE TABLE table_name (...)` | 创建表结构 |
| **插入数据** | `INSERT INTO table VALUES (...)` | 插入记录 |
| **基本查询** | `SELECT * FROM table` | 查询所有数据 |
| **条件查询** | `SELECT ... WHERE condition` | 条件筛选 |
| **排序** | `ORDER BY column ASC/DESC` | 排序结果 |
| **分页** | `LIMIT offset, count` | 分页查询 |
| **聚合** | `COUNT(), MAX(), MIN()` | 聚合计算 |
| **分组** | `GROUP BY column` | 分组统计 |
| **连接** | `INNER/LEFT JOIN` | 多表关联 |
| **子查询** | `SELECT ... (SELECT ...)` | 嵌套查询 |

## 💡 使用建议
1. 使用 `utf8mb4` 字符集支持中文和表情符号
2. 为常用查询字段创建索引提高性能
3. 使用事务保证数据一致性
4. 定期备份重要数据
