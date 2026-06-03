package com.learner.Day8;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper // 告诉 Spring 这是一个 Mapper，Spring 会自动扫描并生成代理对象
public interface TodoItemMapper extends BaseMapper<TodoItem> {
    // 继承 BaseMapper 后，自动拥有以下方法：
    // insert(TodoItem)            → 插入
    // deleteById(Long id)         → 根据ID删除
    // updateById(TodoItem)        → 根据ID更新
    // selectById(Long id)         → 根据ID查询
    // selectList(Wrapper)         → 条件查询
    // selectPage(page, wrapper)   → 分页查询
    // ... 共约 20 个方法

    // 不需要写任何代码，BaseMapper 已经提供了所有基础 CRUD！
}
