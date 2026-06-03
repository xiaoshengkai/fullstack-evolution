package Day9;
// 核心思想：用 LambdaQueryWrapper 代替手写 SQL 条件

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class QuickSql implements CommandLineRunner {

    @Autowired
    private TodoItemMapper todoItemMapper;


    @Override
    public void run (String... arg) {
        // ======== 模糊查询 =======
        System.out.println("\n=== 模糊查询：标题含'Spring' ===");
        LambdaQueryWrapper<TodoItem> todoItemTable = new LambdaQueryWrapper<>();
        todoItemTable.like(TodoItem::getTitle, "Spring");
        List<TodoItem> likeResult = todoItemMapper.selectList(todoItemTable);
        likeResult.forEach(System.out::println);

        // ========= 多条件组合查询 =======
        System.out.println("\n=== 高优先级 + 未完成，按截止日期升序 ===");
        LambdaQueryWrapper<TodoItem> todoItemTable1 = new LambdaQueryWrapper<>();
        todoItemTable1
                .eq(TodoItem::getPriority, "HIGH")
                .eq(TodoItem::getDone, false)
                .orderByAsc(TodoItem::getDeadline);
        List<TodoItem> multiResult = todoItemMapper.selectList(todoItemTable1);
        multiResult.forEach(System.out::println);

        // ========= IN 查询 =======
        System.out.println("\n=== 优先级为 HIGH 或 MEDIUM ===");
        LambdaQueryWrapper<TodoItem> todoItemTable2 = new LambdaQueryWrapper<>();
        todoItemTable2.in(TodoItem::getPriority, "HIGH", "MEDIUM");
        List<TodoItem> inResult = todoItemMapper.selectList(todoItemTable2);
        inResult.forEach(System.out::println);

        // ======== 增加后删除 ===========
        System.out.println("\n=== 添加 ===");
        TodoItem addTodoItem = new TodoItem("test", "LOW", LocalDate.now(), false);
        todoItemMapper.insert(addTodoItem);
        List<TodoItem> addResult = todoItemMapper.selectList(null);
        addResult.forEach(System.out::println);

        System.out.println("\n=== 批量删除 ===");
        LambdaQueryWrapper<TodoItem> todoItem3 = new LambdaQueryWrapper<>();
        todoItem3.eq(TodoItem::getTitle, "test");
        todoItemMapper.delete(todoItem3);
        List<TodoItem> deleteResult = todoItemMapper.selectList(null);
        deleteResult.forEach(System.out::println);

        // ======== 批量更新（按条件修改） ===========
        // 使用 LambdaUpdateWrapper
        System.out.println("\n=== 把 HIGH 改为 LOW ===");
        LambdaUpdateWrapper<TodoItem> todoItem4 = new LambdaUpdateWrapper<>();
        todoItem4.eq(TodoItem::getId, 1).set(TodoItem::getPriority, "LOW");
        todoItemMapper.update(null, todoItem4);
        System.out.println("\n=== 更新后内容 ===");
        System.out.println(todoItemMapper.selectById(1));

    }

    public static void main (String[] args) {
        SpringApplication.run(QuickSql.class, args);
    }
}
