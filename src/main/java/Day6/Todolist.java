package Day6;

//将前面的知识点总结写一个内存版todolist

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 需求如下：
 * 1、添加待办：输入：标题、优先级（高/中/低）、截止日期（yyyy-MM-dd）；自动分配唯一 ID（可用自增整数）
 * 2、显示列表：支持查看全部 / 按状态过滤（未完成/已完成）/ 按优先级过滤；支持按优先级排序（高→中→低）、按截止日期排序（最早→最晚）
 * 3、标记完成：输入 ID，将对应待办标记为已完成
 * 4、删除待办：输入 ID，删除对应待办
 * 5、统计：未完成数量；按优先级分组统计；逾期检测（截止日期早于今天且未完成）
 * 6、退出程序
 * */
enum Priority {
    HIGH("高", 3),
    MEDIUM("中", 2),
    LOW("低", 1);

    private String displayName;
    private int order;

    public String getDisplayName() {
        return displayName;
    }

    public int getOrder() {
        return order;
    }

    Priority(String displayName, int i) {
        this.displayName = displayName;
        this.order = i;

    }
}

class Stat {
    String title;
    Priority priority;
    LocalDate date;
    boolean isFinish = false;

    public Stat(String title, Priority priority, LocalDate date) {
        this.title = title;
        this.priority = priority;
        this.date = date;
    }
}

class TODO extends Stat {
    Long id;

    public TODO(String title, Priority priority, LocalDate date) {
        super(title, priority, date);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIsFinish() {
        isFinish = true;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s | 优先级: %s | 截止: %s | 状态: %s",
                id, title, priority.getDisplayName(), date, isFinish ? "已完成" : "未完成");
    }
}

public class Todolist {
    List<TODO> list = new ArrayList<>();
    Long id = 0L;

    // 查看
    public List<TODO> searchList(Map<String, Object> params) {
        List<TODO> newList = null;

        // 过滤状态
        if (params.get("isFinish") != null) {
            Boolean isFinish = (Boolean) params.get("isFinish");
            newList = list.stream().
                    filter(todo -> todo.isFinish == isFinish)
                    .collect(Collectors.toList());
        }

        // 过滤优先级
        if(params.get("priority") != null) {
            Priority priority =  (Priority) params.get("priority");

            newList = list.stream().
                    filter(todo -> todo.priority == priority)
                    .collect(Collectors.toList());
        }

        // 支持按优先级排序（高→中→低）
        if(params.get("priorityDes") != null) {
            newList = list.stream().sorted((order1, order2) -> {
                Map<String, Integer> map = new HashMap<>();
                map.put(Priority.LOW + "-" + Priority.LOW, 0);
                map.put(Priority.LOW + "-" + Priority.MEDIUM, 1);
                map.put(Priority.LOW + "-" + Priority.HIGH, 1);
                map.put(Priority.MEDIUM + "-" + Priority.LOW, -1);
                map.put(Priority.MEDIUM + "-" + Priority.MEDIUM, 0);
                map.put(Priority.MEDIUM + "-" + Priority.HIGH, 1);
                map.put(Priority.HIGH + "-" + Priority.LOW, -1);
                map.put(Priority.HIGH + "-" + Priority.MEDIUM, -1);
                map.put(Priority.HIGH + "-" + Priority.HIGH, 0);

                return map.get(order1.priority + "-" + order2.priority);

            }).collect(Collectors.toList());
        }

        // 按截止日期排序（最早→最晚）
        if(params.get("deteDes") != null) {
            newList = list.stream().sorted((order1, order2) -> {
                if (order1.date.equals(order2.date)) return 0;
                if (order1.date.isAfter(order2.date)) return 1;
                if (order1.date.isBefore(order2.date)) return -1;

                return 0;
            }).collect(Collectors.toList());
        }

        if (newList == null) return list;

        return newList;
    }

    // 添加待办
    public boolean addToDo (String title, Priority priority, LocalDate date) {
        try {
            if (title == null) throw new RuntimeException("标题必填");
            if (priority == null) throw new RuntimeException("优先级必填");
            if (date == null) throw new RuntimeException("截止时间必填");

            TODO todo = new TODO(title, priority,date);
            todo.setId(id++);
            list.add(todo);
            return true;
        } catch (Error e) {
            return false;
        }
    }

    // 根据id找到TODO
    private Optional<TODO> findTodo(Long id) {
        return list.stream().filter(t -> Objects.equals(t.id, id)).findFirst();
    }

    // 完成 TODO
    public void finish(Long id) {
        Optional<TODO> todo = findTodo(id);
        if (todo.isPresent()) {
            todo.get().setIsFinish();
        }
    }

    // 删除 TODO
    public void delete(Long id) {
        list = list.stream().filter(todo -> !Objects.equals(todo.id, id)).collect(Collectors.toList());
    }


    // 统计
    public Map<String, Object> statistics () {
        Map<String, Object> result = new HashMap<>();

        // 未完成数量
        int unfinishedCount = (int) list.stream().filter(todo -> !todo.isFinish).count();
        result.put("unfinishedCount", unfinishedCount);

        // 按优先级分组统计
        HashMap<Priority, Integer> priorityForGroup = list.stream().collect(
                HashMap::new,
                (map, item) -> {
                    Priority priority = item.priority;
                    Integer value = map.getOrDefault(priority, 0);
                    map.put(priority, value + 1);
                },
                (map1, map2) -> {
                    map2.forEach((key, value) -> {
                         map1.put(key, value + map1.getOrDefault(key, 0));
                        // 等于下面，建议用下面
//                          map1.merge(key, value, Integer::sum);
                    });
                }
        );
        result.put("priorityForGroup", priorityForGroup);


        // 逾期检测（截止日期早于今天且未完成）
        LocalDate currentDate = LocalDate.now();
        List<TODO> unfinishedTODO = list.stream().filter(todo -> !todo.isFinish && todo.date.isBefore(currentDate)).collect(Collectors.toList());
        result.put("unfinishedTODO", unfinishedTODO);

        return result;
    }

    public static void main(String[] args) {
        Todolist todolist = new Todolist();
        List<Stat> data = Arrays.asList(
                // 3月份任务
                new Stat("3月：系统架构设计", Priority.HIGH, LocalDate.of(2026, 3, 5)),
                new Stat("3月：数据库设计评审", Priority.MEDIUM, LocalDate.of(2026, 3, 10)),
                new Stat("3月：原型界面设计", Priority.LOW, LocalDate.of(2026, 3, 15)),
                new Stat("3月：技术选型确定", Priority.HIGH, LocalDate.of(2026, 3, 20)),
                new Stat("3月：开发环境搭建", Priority.MEDIUM, LocalDate.of(2026, 3, 25)),

                // 4月份任务
                new Stat("4月：用户模块开发", Priority.HIGH, LocalDate.of(2026, 4, 5)),
                new Stat("4月：权限模块实现", Priority.HIGH, LocalDate.of(2026, 4, 10)),
                new Stat("4月：接口联调测试", Priority.MEDIUM, LocalDate.of(2026, 4, 15)),
                new Stat("4月：单元测试覆盖", Priority.MEDIUM, LocalDate.of(2026, 4, 20)),
                new Stat("4月：文档初版完成", Priority.LOW, LocalDate.of(2026, 4, 25)),

                // 5月份任务
                new Stat("5月：性能压力测试", Priority.HIGH, LocalDate.of(2026, 5, 5)),
                new Stat("5月：安全漏洞扫描", Priority.HIGH, LocalDate.of(2026, 5, 10)),
                new Stat("5月：用户验收测试", Priority.MEDIUM, LocalDate.of(2026, 5, 15)),
                new Stat("5月：部署脚本编写", Priority.MEDIUM, LocalDate.of(2026, 5, 20)),
                new Stat("5月：培训材料准备", Priority.LOW, LocalDate.of(2026, 5, 25)),

                // 6月份任务
                new Stat("6月：生产环境部署", Priority.HIGH, LocalDate.of(2026, 6, 1)),
                new Stat("6月：监控告警设置", Priority.MEDIUM, LocalDate.of(2026, 6, 5)),
                new Stat("6月：用户培训会议", Priority.MEDIUM, LocalDate.of(2026, 6, 10)),
                new Stat("6月：项目总结报告", Priority.LOW, LocalDate.of(2026, 6, 15)),
                new Stat("6月：经验教训整理", Priority.LOW, LocalDate.of(2026, 6, 20))
        );

        data.forEach(stat -> {
            boolean result = todolist.addToDo(stat.title, stat.priority, stat.date);
            System.out.println("添加任务: " + stat.title + " - " + (result ? "成功" : "失败"));
        });

        System.out.println("\n=== 2. 显示所有待办 ===");
        List<TODO> allTodos = todolist.searchList(new HashMap<>());
        allTodos.forEach(System.out::println);
        System.out.println("总计: " + allTodos.size() + " 个待办");

        System.out.println("\n=== 3. 按状态过滤（未完成）===");
        Map<String, Object> params1 = new HashMap<>();
        params1.put("isFinish", false);
        List<TODO> unfinished = todolist.searchList(params1);
        System.out.println("未完成待办: " + unfinished.size() + " 个");
        unfinished.forEach(todo -> System.out.println("  " + todo.title));

        System.out.println("\n=== 4. 按优先级过滤（高优先级）===");
        Map<String, Object> params2 = new HashMap<>();
        params2.put("priority", Priority.HIGH);
        List<TODO> highPriority = todolist.searchList(params2);
        System.out.println("高优先级待办: " + highPriority.size() + " 个");
        highPriority.forEach(todo -> System.out.println("  " + todo.title));

        System.out.println("\n=== 5. 按优先级排序（高→中→低）===");
        Map<String, Object> params3 = new HashMap<>();
        params3.put("priorityDes", true);
        List<TODO> sortedByPriority = todolist.searchList(params3);
        sortedByPriority.forEach(todo ->
                System.out.println("  " + todo.title + " [" + todo.priority.getDisplayName() + "]")
        );

        System.out.println("\n=== 6. 按截止日期排序（最早→最晚）===");
        Map<String, Object> params4 = new HashMap<>();
        params4.put("deteDes", true);
        List<TODO> sortedByDate = todolist.searchList(params4);
        sortedByDate.forEach(todo ->
                System.out.println("  " + todo.date + " - " + todo.title)
        );

        System.out.println("\n=== 7. 标记完成（ID=1,3,5）===");
        todolist.finish(1L);
        todolist.finish(3L);
        todolist.finish(5L);
        System.out.println("标记完成后，未完成数量:");
        List<TODO> afterFinish = todolist.searchList(params1);
        System.out.println("未完成: " + afterFinish.size() + " 个");

        System.out.println("\n=== 8. 删除待办（ID=2）===");
        int beforeDelete = todolist.searchList(new HashMap<>()).size();
        todolist.delete(2L);
        int afterDelete = todolist.searchList(new HashMap<>()).size();
        System.out.println("删除前: " + beforeDelete + " 个，删除后: " + afterDelete + " 个");

        System.out.println("\n=== 9. 统计功能测试 ===");
        Map<String, Object> stats = todolist.statistics();
        System.out.println("未完成数量: " + stats.get("unfinishedCount"));

        // 9. 统计功能测试
        System.out.println("\n=== 10. 按优先级分组统计 ===");
        HashMap<Priority, Integer> priorityStats = (HashMap<Priority, Integer>) stats.get("priorityForGroup");
        System.out.println("按优先级分组统计:");
        if (priorityStats != null) {
            priorityStats.forEach((priority, count) ->
                    System.out.println("  " + priority + ": " + count + " 个")
            );
        }

        System.out.println("\n=== 10. 逾期检测（当前日期为" + LocalDate.now() + "）===");
        List<TODO> overdue = (List<TODO>) stats.get("unfinishedTODO");
        System.out.println("逾期任务数量: " + (overdue != null ? overdue.size() : 0) + " 个");
        if (overdue != null && !overdue.isEmpty()) {
            System.out.println("逾期任务列表:");
            overdue.forEach(todo ->
                    System.out.println("  " + todo.date + " - " + todo.title)
            );
        }

        System.out.println("\n=== 12. 复杂查询：高优先级+未完成 ===");
        Map<String, Object> complexParams = new HashMap<>();
        complexParams.put("isFinish", false);
        complexParams.put("priority", Priority.HIGH);
        List<TODO> complexResult = todolist.searchList(complexParams);
        System.out.println("高优先级且未完成: " + complexResult.size() + " 个");
        complexResult.forEach(todo ->
                System.out.println("  " + todo.title)
        );

        System.out.println("\n=== 13. 最终状态 ===");
        List<TODO> finalList = todolist.searchList(new HashMap<>());
        System.out.println("剩余待办总数: " + finalList.size());
        finalList.forEach(todo ->
                System.out.println(String.format("  [%d] %s - %s - %s - %s",
                        todo.id, todo.title, todo.priority.getDisplayName(), todo.date,
                        todo.isFinish ? "已完成" : "未完成"))
        );
    }
}
