package com.learner.Day8;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

//@Component // 开启即可被Application识别执行
public class MyBatisPlusTest implements CommandLineRunner {

    @Autowired
    private TodoItemMapper todoItemMapper;

    @Override
    public void run(String... args) {
        // ========= 1. 插入 =========
        System.out.println("=== 插入一条新记录 ===");
        TodoItem newItem = new TodoItem("学习my batis-plus", "HIGH", LocalDate.now());

        // 疑问: 它怎么知道我要操作的是哪张表？
        // 解答：默认规则：实体类名 → 下划线表名，创建的实体类叫 TodoItem，MyBatis-Plus 会自动把驼峰命名转为下划线命名
        // 表名不符合这个规律，使用 @TableName("t_todo_item")， 告诉它：我的表名叫 t_todo_item
        // 表中的字段名同理

        int inserted = todoItemMapper.insert(newItem);
        System.out.println("插入结果: " + inserted + ", 生成ID: " + newItem.getId());

        // ========= 2. 查询全部 =========
        System.out.println("\n=== 查询全部 ===");
        List<TodoItem> list = todoItemMapper.selectList(null);
        list.forEach(System.out::println);

        // ========= 3. 根据ID查询 =========
        System.out.println("\n=== 根据ID查询 ===");
        TodoItem todoItem = todoItemMapper.selectById(1L);
        System.out.println(todoItem);

        // ========= 4. 更新 =========
        System.out.println("\n=== 更新 ===");
        newItem.setDone(true);
        todoItemMapper.updateById(newItem);
        list.forEach(System.out::println);

        // ========= 5. 删除 =========
        list.forEach(item -> {
            if (Objects.equals(item.getTitle(), "学习my batis-plus")) {
                todoItemMapper.deleteById(item.getId());
            }
        });
        System.out.println("删除后总数: " + todoItemMapper.selectList(null).size());
    }

//    public static void main(String[] args) {
//        SpringApplication.run(MyBatisPlusTest.class, args);
//        /**
//         * 问题：为啥不直接 this.run(args);
//         * 理由：
//         * 1、java 基础：静态上下文中没有 this
//         * 2、new MyBatisPlusTest().run(args);这样可以，但不会启动 Spring，因为这样做只是单纯执行了你写的业务代码，没有启动 Spring 容器，所有依赖注入（@Autowired）都不会生效。
//         * 3、SpringApplication.run 做了什么？
//         *   创建 Spring 应用上下文（容器）
//         *   扫描并实例化所有 Bean（包括 TodoItemMapper 的代理实现类）
//         *   处理依赖注入（给 @Autowired 字段赋值）
//         *   自动配置（数据源、MyBatis-Plus 等）
//         *   执行 CommandLineRunner（在你的 Bean 创建好之后，自动调用你的 run 方法）
//        * */
//
//    }
}
