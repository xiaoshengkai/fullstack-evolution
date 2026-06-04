package com.learner.Day10;

import com.learner.Day8.TodoItem;
import com.learner.Day8.TodoItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component // 开启即可被Application识别执行
public class Test implements CommandLineRunner {
    @Autowired
    private Handle handle;

    @Autowired
    private TodoItemMapper todoItemMapper;

    @Override
    public void run (String... args) {
        // 测试1：正常更新（不带异常）
        System.out.println("=== 测试正常更新 ===");
        handle.markDoneAndChangePriority(1L, false, "LOW", false);
        TodoItem item = todoItemMapper.selectById(1L);
        System.out.println("更新后: " + item);

        // 测试2：不正常更新（不带异常）
        System.out.println("=== 测试不正常更新 ===");
        try {
            handle.markDoneAndChangePriority(1L, true, "HIGH", true);
        } catch (Exception e) {
            System.out.println("捕获异常: " + e.getMessage());
        }
        TodoItem item1 = todoItemMapper.selectById(1L);
        System.out.println("更新后: " + item1);// 测试2：不正常更新（不带异常）

        // 测试3：带异常的方法（回滚演示）
        System.out.println("插入前" + todoItemMapper.selectCount(null));
        try {
            handle.insertBatchWithRollback();
        } catch (Exception e) {
            e.fillInStackTrace();
            System.out.println("捕获异常: " + e.getMessage());
        }
        System.out.println("插入后" + todoItemMapper.selectCount(null));
    }
}
