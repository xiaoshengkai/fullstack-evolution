package com.learner.Day12;

import com.learner.Day8.TodoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class App implements CommandLineRunner {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public void run (String[] args) {
        System.out.println("// ========== 查一个待办的所有标签名 ============");
        List<String> list = tagMapper.findTagNamesByTodoItemId(1L);
        System.out.println(list);

        System.out.println("// ========== 找出“没有任何标签的待办事项” ============");
//        List<TodoItem> list1 = tagMapper.findAllTodoItemsHasNoTag1();
//        list1.forEach(System.out::println);
        List<TodoItem> list2 = tagMapper.findAllTodoItemsHasNoTag2();
        list2.forEach(System.out::println);
    }
}
