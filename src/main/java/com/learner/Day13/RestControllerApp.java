package com.learner.Day13;

import com.learner.Day11.TodoService;
import com.learner.Day8.TodoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/todos")
public class RestControllerApp {

    @Autowired
    private TodoService todoService;

    @GetMapping("/test")
    public List<String> test () {
        return Arrays.asList("hello", "world");
    }

    // 1. 获取所有待办
    @GetMapping("/getList")
    public List<TodoItem> getListAll() {
        return todoService.listAll();
    }

    // 2. 根据 ID 获取单个待办
    @GetMapping("/{id}")
    public TodoItem getTodoById(@PathVariable Long id) {
        return todoService.findById(id).orElse(null);
    }

    // 3. 添加待办
    //    @PostMapping // 不加路径 = 使用类上的基础路径及时 "/api/todos"
    @PostMapping("/addTodo")
    public String addTodo(@RequestBody TodoItem todoItem) {
        try {
            todoService.addTodo(todoItem.getTitle(), todoItem.getPriority(), todoItem.getDeadline());
            return "添加成功";
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
            return "添加失败";
        }
    }

    // 4. 标记完成
    @PostMapping("/{id}/update")
    public String updateTodo(@PathVariable Long id) {
        todoService.markDone(id);
        return "标记完成";
    }

    // 5. 删除待办
    @DeleteMapping("/delete/{id}")
    public String deleteTodo(@PathVariable Long id) {
        todoService.delete(id);
        return "删除完成";
    }

    // 7. 按优先级过滤
    @GetMapping("/filterByPriority")
    public List<TodoItem> filterByPriority(@RequestParam String priority) {
        return todoService.filterByPriority(priority);
    }

    // 8. 排序（按截止日期升序）
    @GetMapping("/sorted")
    public List<TodoItem> sortByDeadlineAsc() {
        return todoService.sortByDeadlineAsc();
    }

    // 9. 统计信息
    @GetMapping("/stats")
    public Map<String, Object> statistics() {
        return todoService.statistics();
    }
}
