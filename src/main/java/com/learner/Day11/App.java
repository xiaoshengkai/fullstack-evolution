package com.learner.Day11;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

//@Component
public class App implements CommandLineRunner {
    @Autowired
    private  TodoService service;

    @Override
    public void run (String[] args) {
        // 初始化测试数据
        service.addTodo("学习 MySQL", "HIGH", LocalDate.of(2026, 6, 10));
        service.addTodo("搭建 Spring Boot", "HIGH", LocalDate.of(2026, 6, 5));
        service.addTodo("阅读 MyBatis-Plus 文档", "MEDIUM", LocalDate.of(2026, 6, 15));
        service.addTodo("复习 Java 8", "LOW", LocalDate.of(2026, 6, 20));

        // 1. 显示全部
        System.out.println("=== 全部待办 ===");
        service.listAll().forEach(System.out::println);

        // 2. 按状态过滤（未完成）
        System.out.println("\n=== 未完成 ===");
        service.filterByDone(false).forEach(System.out::println);

        // 3. 按优先级过滤
        System.out.println("\n=== 高优先级 ===");
        service.filterByPriority("HIGH").forEach(System.out::println);

        // 4. 排序
        System.out.println("\n=== 按截止日期升序 ===");
        service.sortByDeadlineAsc().forEach(System.out::println);

        // 5. 标记完成
        System.out.println("\n=== 标记 ID=1 完成 ===");
        service.markDone(1L);
        System.out.println("完成后的数据:");
        System.out.println(service.findById(1L).orElse(null));

        // 6. 删除
        System.out.println("\n=== 删除 ID=2 ===");
        service.delete(2L);
        System.out.println("剩余数量: " + service.listAll().size());

        // 7. 统计
        System.out.println("\n=== 统计 ===");
        Map<String, Object> stats = service.statistics();
        System.out.println("未完成数量: " + stats.get("unfinishedCount"));
        System.out.println("按优先级分组: " + stats.get("priorityGroup"));
        System.out.println("逾期未完成: " + stats.get("overdue"));
    }
}
