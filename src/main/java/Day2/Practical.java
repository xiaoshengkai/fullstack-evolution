package Day2;

import java.util.*;

public class Practical {

    static class Order {
        String name;
        double amount;
        String category;
        boolean shipped;

        public Order (String name, double amount, String category, boolean shipped) {
            this.name = name;
            this.amount = amount;
            this.category = category;
            this.shipped = shipped;
        }

        public String getName(){ return name; }
        public double getAmount(){ return amount; }
        public String getCategory(){ return category; }
        public boolean isShipped(){ return shipped; }
    }


    public static void main(String[] args) {
        List<Order> orders = Arrays.asList(
            new Order("手机", 4999, "电子产品", false),
            new Order("Java核心编程", 89, "图书", true),
            new Order("牛奶", 5.5, "食品", false),
            new Order("耳机", 699, "电子产品", true),
            new Order("饼干", 12, "食品", true),
            new Order("Spring实战", 79, "图书", false)
        );

        // 1. 筛选已发货订单并打印
        System.out.println("筛选已发货,如下：");
        orders.stream()
                .filter(Order::isShipped)
                .forEach(item -> System.out.println(item.name));

        // 2. 获取所有订单的总金额（reduce）
        double amounts = orders.stream()
                            .map(item -> item.amount)
                            .reduce(0.0, Double::sum);
        System.out.println("所有订单的总金额：" + amounts);

        // 3. 按分类统计每个分类的订单数量（groupingBy + counting）
        Map<String, Long> countByCategory = orders.stream().collect(
                () -> new HashMap<String, Long>(),
                (map, order) -> {
                    String category = order.getCategory();
                    Long   categoryCount = map.getOrDefault(category, 0L);

                    // 更新值
                    map.put(category, categoryCount + 1);
                },
                (map1, map2) -> {
                    map2.forEach((key, value) -> {
                        map1.merge(key, value, Long::sum);
                    });
                }

                // 理解 java 并行流
                // 假设并行度=2，线程1处理前3个订单，线程2处理后3个订单。
                //        ┌─────────────────────────────────────────────┐
                //│  线程1                                        │
                //│  supplier: 创建容器 map1 = {}                 │
                //│  accumulator: 处理3个订单                      │
                //│  结果 map1: {                                 │
                //│    "电子产品": ["手机"],                        │
                //│    "食品":     ["牛奶"],                        │
                //│    "图书":     ["Java核心编程"]                 │
                //│  }                                           │
                //└──────────────┬──────────────────────────────┘
                //               │
                //               ▼
                //     ┌─────────────────────┐
                //     │  Combiner: 合并 map1 │
                //     │  与 map2             │
                //     └─────────────────────┘
                //               ▲
                //               │
                //┌──────────────┴──────────────────────────────┐
                //│  线程2                                        │
                //│  supplier: 创建容器 map2 = {}                 │
                //│  accumulator: 处理3个订单                      │
                //│  结果 map2: {                                 │
                //│    "电子产品": ["耳机"],                        │
                //│    "食品":     ["饼干"],                        │
                //│    "图书":     ["Spring实战"]                  │
                //│  }                                           │
                //└──────────────────────────────────────────────┘
        );
        System.out.println("分类统计：" + countByCategory);

        // 4. 按金额降序排序，取前 3 个
        orders.stream()
                .sorted(Comparator.comparingDouble(Order::getAmount).reversed())
                .limit(3)
                .forEach(item -> {
                    System.out.println("按金额降序排序" + item.getName() + ":" + item.getAmount());
                });
    }

}
