package com.learner.Day12;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learner.Day8.TodoItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    // 查一个待办的所有标签名
    @Select("select tg.name from tag tg inner join todo_tag tt on tg.id = tt.tag_id where tt.todo_item_id = #{todoItemId}")
    List<String> findTagNamesByTodoItemId(@Param("todoItemId") Long todoItemId);

    // 找出“没有任何标签的待办事项”（NOT IN 子查询）
    @Select("select t.id, t.title from todo_item t where t.id not in (select todo_item_id from todo_tag) ")
    List<TodoItem> findAllTodoItemsHasNoTag1();

    // 找出“没有任何标签的待办事项” (LEFT JOIN + IS NULL)
    @Select("select id, title from (select * from todo_item t left join todo_tag tg on tg.todo_item_id = t.id) as temp where temp.todo_item_id is null")
    List<TodoItem> findAllTodoItemsHasNoTag2();
}
