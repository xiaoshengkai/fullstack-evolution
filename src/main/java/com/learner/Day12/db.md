# MySQL 多表关联与复杂 SQL 练习

## 数据库结构设计

### Todo 系统表结构
```
todo_item        tag              todo_tag (中间表)
┌──────────┐    ┌──────────┐    ┌─────────────────┐
│ id       │    │ id       │    │ todo_item_id    │
│ title    │    │ name     │    │ tag_id          │
│ ...      │    └──────────┘    └─────────────────┘
└──────────┘
todo_item (id) -> todo_tag (tag_id) -> tag
```

## 1. 创建表结构

```sql
-- 创建标签表
CREATE TABLE tag (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);

-- 创建中间表（多对多关系）
CREATE TABLE todo_tag (
    todo_item_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY (todo_item_id, tag_id)
);
```

## 2. 插入测试数据

```sql
-- 插入标签数据
INSERT INTO tag (name) VALUES ('学习'), ('工作'), ('紧急'), ('长期');

-- 插入关联数据
INSERT INTO todo_tag VALUES (1, 1), (1, 3), (2, 2), (3, 1), (3, 2);
```

## 3. 多表查询练习

### 3.1 查询每个待办事项及其标签（内连接）

```sql
SELECT t.title, tg.name AS tagName
FROM todo_item t
INNER JOIN todo_tag tt ON tt.todo_item_id = t.id
INNER JOIN tag tg ON tt.tag_id = tg.id;
```

**查询结果：**

| title | tagName |
|-------|---------|
| 学习 MySQL 基础 | 学习 |
| 学习 MySQL 基础 | 紧急 |
| 阅读 MyBatis-Plus 文档 | 学习 |
| 阅读 MyBatis-Plus 文档 | 工作 |

**执行步骤解析：**

1. **第一步**：`FROM todo_item t`，主表是 `todo_item`，别名为 `t`
2. **第二步**：`INNER JOIN todo_tag tt`，连接 `todo_tag` 表，别名为 `tt`，`INNER JOIN` 表示按条件合并
3. **第三步**：`ON tt.todo_item_id = t.id`，连接条件
4. **第四步**：`INNER JOIN tag tg ON tt.tag_id = tg.id`，连接 `tag` 表
5. **第五步**：`SELECT t.title, tg.name AS tagName`，选择显示的列

**中间结果示例：**

```sql
-- 第一步连接结果
SELECT *
FROM todo_item t
INNER JOIN todo_tag tt ON tt.todo_item_id = t.id;
```

| id | title | priority | deadline | done | create_time | update_time | todo_item_id | tag_id |
|----|-------|----------|----------|------|-------------|-------------|--------------|--------|
| 1 | 学习 MySQL 基础 | LOW | 2026-06-10 | 1 | 2026-06-02 11:18:12 | 2026-06-04 13:48:06 | 1 | 1 |
| 1 | 学习 MySQL 基础 | LOW | 2026-06-10 | 1 | 2026-06-02 11:18:12 | 2026-06-04 13:48:06 | 1 | 3 |
| 3 | 阅读 MyBatis-Plus 文档 | MEDIUM | 2026-06-15 | 0 | 2026-06-02 11:18:12 | 2026-06-02 11:18:12 | 3 | 1 |
| 3 | 阅读 MyBatis-Plus 文档 | MEDIUM | 2026-06-15 | 0 | 2026-06-02 11:18:12 | 2026-06-02 11:18:12 | 3 | 2 |

```sql
-- 第二步连接结果
SELECT *
FROM todo_item t
INNER JOIN todo_tag tt ON tt.todo_item_id = t.id
INNER JOIN tag tg ON tt.tag_id = tg.id;
```

| id | title | priority | deadline | done | create_time | update_time | todo_item_id | tag_id | id | name |
|----|-------|----------|----------|------|-------------|-------------|--------------|--------|----|------|
| 1 | 学习 MySQL 基础 | LOW | 2026-06-10 | 1 | 2026-06-02 11:18:12 | 2026-06-04 13:48:06 | 1 | 1 | 1 | 学习 |
| 1 | 学习 MySQL 基础 | LOW | 2026-06-10 | 1 | 2026-06-02 11:18:12 | 2026-06-04 13:48:06 | 1 | 3 | 3 | 紧急 |
| 3 | 阅读 MyBatis-Plus 文档 | MEDIUM | 2026-06-15 | 0 | 2026-06-02 11:18:12 | 2026-06-02 11:18:12 | 3 | 1 | 1 | 学习 |
| 3 | 阅读 MyBatis-Plus 文档 | MEDIUM | 2026-06-15 | 0 | 2026-06-02 11:18:12 | 2026-06-02 11:18:12 | 3 | 2 | 2 | 工作 |

### 3.2 所有待办（包括没有标签的）——左连接

```sql
-- 左连接：以 todo_item 表为主
SELECT t.title, tg.name AS tag_name
FROM todo_item t
LEFT JOIN todo_tag tt ON t.id = tt.todo_item_id
LEFT JOIN tag tg ON tt.tag_id = tg.id;
```

**查询结果：**

| title | tag_name |
|-------|----------|
| 学习 MySQL 基础 | 学习 |
| 学习 MySQL 基础 | 紧急 |
| 阅读 MyBatis-Plus 文档 | 学习 |
| 阅读 MyBatis-Plus 文档 | 工作 |
| 学习数据库 | NULL |
| 学习 MySQL | NULL |
| 搭建 Spring Boot | NULL |
| 阅读 MyBatis-Plus 文档 | NULL |
| 复习 Java 8 | NULL |
| 学习 MySQL | NULL |
| 搭建 Spring Boot | NULL |
| 阅读 MyBatis-Plus 文档 | NULL |
| 复习 Java 8 | NULL |

```sql
-- 右连接：以 tag 表为主
SELECT t.title, tg.name AS tag_name
FROM todo_item t
RIGHT JOIN todo_tag tt ON t.id = tt.todo_item_id
RIGHT JOIN tag tg ON tt.tag_id = tg.id;
```

**查询结果：**

| title | tag_name |
|-------|----------|
| 学习 MySQL 基础 | 学习 |
| 阅读 MyBatis-Plus 文档 | 学习 |
| NULL | 工作 |
| 阅读 MyBatis-Plus 文档 | 工作 |
| 学习 MySQL 基础 | 紧急 |
| NULL | 长期 |

**理解**：
- **左连接**：展示数量以主表为主，没有匹配的展示 `NULL`
- **右连接**：展示数量以最后的连接表为主，没有匹配的展示 `NULL`

### 3.3 统计每个标签下有多少待办事项

```sql
-- 统计每个标签的待办数量
SELECT tg.name, COUNT(tt.tag_id) AS count
FROM tag tg
LEFT JOIN todo_tag tt ON tt.tag_id = tg.id
GROUP BY tg.id;
```

**查询结果：**

| name | count |
|------|-------|
| 学习 | 2 |
| 工作 | 2 |
| 紧急 | 1 |
| 长期 | 0 |

**重要理解点：**

1. **COUNT(tag_id) 和 COUNT(*) 的区别**
```sql
-- COUNT(tag_id)：忽略 NULL 值
SELECT tg.name, COUNT(tt.tag_id) AS count
FROM tag tg
LEFT JOIN todo_tag tt ON tt.tag_id = tg.id
GROUP BY tg.id;

-- COUNT(*)：统计所有行，包括 NULL
SELECT tg.name, COUNT(*) AS count
FROM tag tg
LEFT JOIN todo_tag tt ON tt.tag_id = tg.id
GROUP BY tg.id;
```
**COUNT(*) 结果：**

| name | count |
|------|-------|
| 学习 | 2 |
| 工作 | 2 |
| 紧急 | 1 |
| 长期 | 1 |  -- 包含 NULL 行

2. **SQL 执行顺序**：
   `FROM` → `JOIN` → `WHERE` → `GROUP BY` → `HAVING` → `SELECT` → `ORDER BY`

### 3.4 找出同时打了"学习"和"紧急"标签的待办

```sql
-- 使用多次 JOIN 查找同时有多个标签的待办
SELECT t.title
FROM todo_item t
INNER JOIN todo_tag tt1 ON t.id = tt1.todo_item_id
INNER JOIN tag tg1 ON tt1.tag_id = tg1.id AND tg1.name = '学习'
INNER JOIN todo_tag tt2 ON t.id = tt2.todo_item_id
INNER JOIN tag tg2 ON tt2.tag_id = tg2.id AND tg2.name = '紧急';
```

**查询结果：**

| title |
|-------|
| 学习 MySQL 基础 |

**执行逻辑**：
1. 先匹配出有"学习"标签的待办
2. 再匹配出有"紧急"标签的待办
3. 取两者的交集

### 3.5 子查询：找出逾期且没有标签的待办

```sql
-- 使用 NOT IN 子查询
SELECT *
FROM todo_item
WHERE deadline < CURDATE()
AND id NOT IN (SELECT todo_item_id FROM todo_tag);
```

**查询结果：**

| id | title | priority | deadline | done | create_time | update_time |
|----|-------|----------|----------|------|-------------|-------------|
| 2062411327910264835 | 学习 MySQL 进阶中 | HIGH | 2026-05-22 | 0 | 2026-06-04 17:04:46 | 2026-06-04 17:04:46 |

## 4. SQL 查询技巧总结

| 查询类型 | 关键字 | 说明 | 适用场景 |
|---------|--------|------|---------|
| **内连接** | `INNER JOIN` | 返回两个表的交集 | 需要同时满足条件的记录 |
| **左连接** | `LEFT JOIN` | 返回左表全部+右表匹配 | 以左表为主，保留所有记录 |
| **右连接** | `RIGHT JOIN` | 返回右表全部+左表匹配 | 以右表为主，保留所有记录 |
| **分组统计** | `GROUP BY` + 聚合函数 | 按字段分组统计 | 统计数据分布 |
| **子查询** | `IN`, `EXISTS`, `NOT IN` | 嵌套查询 | 复杂条件筛选 |
| **多条件筛选** | 多次 `JOIN` | 同时满足多个条件 | 查找具有多个标签的记录 |

## 5. 常用聚合函数

| 函数 | 说明 | 示例 |
|------|------|------|
| `COUNT()` | 统计行数 | `COUNT(column)` 忽略 NULL |
| `SUM()` | 求和 | `SUM(price)` |
| `AVG()` | 平均值 | `AVG(score)` |
| `MAX()` | 最大值 | `MAX(deadline)` |
| `MIN()` | 最小值 | `MIN(create_time)` |

## 6. 注意事项

1. **JOIN 性能**：多表 JOIN 时注意索引优化
2. **NULL 处理**：LEFT JOIN 可能导致 NULL，注意聚合函数选择
3. **子查询优化**：大数据量时考虑使用 EXISTS 替代 IN
4. **执行顺序**：理解 SQL 执行顺序有助于编写高效查询
