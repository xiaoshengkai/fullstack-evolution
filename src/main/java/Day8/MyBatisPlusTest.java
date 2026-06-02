package Day8;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SpringBootApplication
public class MyBatisPlusTest implements CommandLineRunner {

    @Autowired
    private TodoItemMapper todoItemMapper;

    @Override
    public void run(String... args) {
        // ========= 1. 插入 =========
        System.out.println("=== 插入一条新记录 ===");
        TodoItem newItem = new TodoItem("学习my batis-plus", "HIGH", LocalDate.now(), false);

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

    public static void main(String[] args) {
        SpringApplication.run(MyBatisPlusTest.class, args);
    }
}
