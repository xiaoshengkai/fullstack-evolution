package Day3;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class QuickMethods {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("张三", "李四", "张三", "王五", "李四", "赵六");
        List<Integer> numbers = Arrays.asList(5, 2, 8, 1, 9);

        // distinct、sorted、peek 常用

        // distinct：去重
        List<String> distinctList  = names.stream().distinct().collect(Collectors.toList());
        System.out.println("去重" + distinctList);

        // 排序 sorted
        List<Integer> sortedAsc  = numbers.stream().sorted().collect(Collectors.toList());
        System.out.println("默认（升序）排序" + sortedAsc);
        List<Integer> sortedDesc  = numbers.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        System.out.println("倒序排序" + sortedDesc);

        // peek：调试神器，查看流中每个元素，常用于打印日志
        List<Integer> peekList = numbers.stream()
                .peek((n) -> System.out.println("处理前: " + n))
                .filter(n -> n % 2 == 0)
                .peek((n) -> System.out.println("过滤后: " + n))
                .collect(Collectors.toList());
    }
}
