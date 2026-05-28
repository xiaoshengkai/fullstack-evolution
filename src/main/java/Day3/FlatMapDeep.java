package Day3;

import java.util.*;
import java.util.stream.Collectors;

public class FlatMapDeep {
    static class OrderItem {
        String shopName;
        int count;
        double price;

        public OrderItem(String shopName, int count, double price) {
            this.shopName = shopName;
            this.count = count;
            this.price = price;
        }

        public String getShopName() { return shopName; }
        public int getCount() { return count; }
        public double getPrice() { return price; }


        @Override
        public String toString() {
           return shopName + 'x' + count;
        }
    }

    static class Order {
        String orderId;
        List<OrderItem> items;

        public Order (String orderId, List<OrderItem> items) {
            this.orderId = orderId;
            this.items = items;
        }

        public String getOrderId() { return orderId; }
        public List<OrderItem> getItems() { return items; }
    }

    public static void main(String[] args) {
        // 模拟订单数据：一个订单包含多个商品
        List<Order> orders = Arrays.asList(
                new Order("A001", Arrays.asList(
                        new OrderItem("手机", 1, 4999),
                        new OrderItem("手机壳", 2, 29)
                )),
                new Order("A002", Arrays.asList(
                        new OrderItem("耳机", 1, 699),
                        new OrderItem("数据线", 3, 19)
                )),
                new Order("A003", Arrays.asList(
                        new OrderItem("手机", 1, 4999),
                        new OrderItem("耳机", 1, 699)
                ))
        );
        // 1. 获取所有订单中的所有商品明细（拍平）
        List<OrderItem> allItems = orders.stream()
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.toList());
        System.out.println("获取所有订单中的所有商品明细" + allItems);

        // 2. 统计每种商品的总销售数量
        Map<String, Long> nameForCountMap = allItems.stream().collect(
                HashMap::new,
                (map, item) -> {
                    String showName = item.getShopName();
                    Long count = map.getOrDefault(showName, 0L);
                    map.put(showName, count + 1);
                },
                (map1, map2) -> {
                    map2.forEach((key, value) -> {
                        map1.merge(key, value, Long::sum);
                    });
                }
        );
        System.out.println("统计每种商品的总销售数量" + nameForCountMap);
    }
}
