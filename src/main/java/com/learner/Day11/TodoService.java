package com.learner.Day11;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.learner.Day8.TodoItem;
import com.learner.Day8.TodoItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoItemMapper todoItemMapper;

    @Transactional
    public void addTodo (String title, String priority, LocalDate deadline) {
        TodoItem todoItem = new TodoItem(title, priority, deadline);
        todoItemMapper.insert(todoItem);
    }

    public List<TodoItem> listAll () {
        return todoItemMapper.selectList(null);
    }

    public List<TodoItem> filterByDone (boolean done) {
        LambdaQueryWrapper<TodoItem> listQuery = new LambdaQueryWrapper<>();
        listQuery.eq(TodoItem::getDone, done);
        return todoItemMapper.selectList(listQuery);
    }

    public List<TodoItem> filterByPriority (String priority) {
        LambdaQueryWrapper<TodoItem> listQuery = new LambdaQueryWrapper<>();
        listQuery.eq(TodoItem::getPriority, priority);
        return todoItemMapper.selectList(listQuery);
    }

    public List<TodoItem> sortByDeadlineAsc () {
        LambdaQueryWrapper<TodoItem> listQuery = new LambdaQueryWrapper<>();
        listQuery.orderByAsc(TodoItem::getDeadline);
        return todoItemMapper.selectList(listQuery);
    }


    @Transactional
    public boolean markDone(Long id) {
        LambdaUpdateWrapper<TodoItem> listOperate = new LambdaUpdateWrapper<>();
        listOperate.eq(TodoItem::getId, id).set(TodoItem::getDone, true);
        int rows = todoItemMapper.update(listOperate);
        return rows > 0;
    }

    @Transactional
    public boolean delete(Long id) {
//        LambdaQueryWrapper<TodoItem> listOperate = new LambdaQueryWrapper<>();
//        listOperate.eq(TodoItem::getId, id);
//        int rows =  todoItemMapper.delete(listOperate);
        int rows =  todoItemMapper.deleteById(id);
        return rows > 0;
    }


    public Optional<TodoItem> findById (Long id) {
//        LambdaQueryWrapper<TodoItem> listQuery = new LambdaQueryWrapper<>();
//        listQuery.eq(TodoItem::getId, id);
//        return Optional.ofNullable(todoItemMapper.selectOne(listQuery));

        // 优化：通过主键直接查询
        return Optional.ofNullable(todoItemMapper.selectById(id));
    }


    public Map<String, Object> statistics () {
        Map<String, Object> map = new HashMap<>();

        LambdaQueryWrapper<TodoItem> unfinishedCountWrapper = new LambdaQueryWrapper<>();
        unfinishedCountWrapper.eq(TodoItem::getDone, false);
//        int count = todoItemMapper.selectList(unfinishedCountWrapper).size(); 查出所有未完成记录到内存再 .size()
        Long count = todoItemMapper.selectCount(unfinishedCountWrapper); //直接让数据库 COUNT，性能比上面好
        map.put("unfinishedCount", count);

        QueryWrapper<TodoItem> priorityGroupWrapper = new QueryWrapper<>();
        priorityGroupWrapper.select("priority", "COUNT(*) AS count").groupBy("priority");
        map.put("priorityGroup",  todoItemMapper.selectMaps(priorityGroupWrapper));


        LambdaQueryWrapper<TodoItem> overdueWrapper = new LambdaQueryWrapper<>();
        overdueWrapper.eq(TodoItem::getDone, false).lt(TodoItem::getDeadline, LocalDate.of(2026, 6, 15));
        map.put("overdue",  todoItemMapper.selectList(overdueWrapper));

        return map;
    }
}
