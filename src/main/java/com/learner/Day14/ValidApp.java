package com.learner.Day14;

import com.learner.Day11.TodoService;
import com.learner.Day8.TodoItem;
import com.learner.Day8.TodoItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/todos/vaild")
public class ValidApp {

    @Autowired
    private TodoService todoService;

    @PostMapping("/addTodo")
    public Result<String> validAddTodo (@Valid @RequestBody TodoItem todoItem) {
        todoService.addTodo(todoItem.getTitle(), todoItem.getPriority(), todoItem.getDeadline());
        return Result.success("添加成功");
    };
}
