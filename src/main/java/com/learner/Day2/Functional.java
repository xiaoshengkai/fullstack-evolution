package com.learner.Day2;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Functional {
    // 条件判断（filter）
    // js (x) => boolean
    Predicate<Integer> showAddress = (num -> num >= 60);

    // 转换/映射（map）
    // js (x) => y
    Function<Boolean, String> todoFuck = r -> r ? "吃饭" : "走路";

    // 消费数据（无返回）（forEach）
    // js (x) => { /* side effect */ }
    Consumer<Object[]> todoList = (list) -> {
        Arrays.stream(list).forEach(item -> System.out.println(item)); // 等价， System.out::println
    };

    // 提供数据（生成）
    // js () => value
    Supplier<String> getName = () -> "小娃娃";


    public static void main(String[] args) {
        // flatMap 拍平
        List<List<Integer>> listNest = Arrays.asList(
                Arrays.asList(1,2),
                Arrays.asList(3,4)
        );

        List<Integer> list = listNest.stream().flatMap(item -> item.stream()).collect(Collectors.toList());


        System.out.println("flatMap：" + list);
    }
}

/**
 * 当 Lambda 表达式的内容仅仅调用了一个已有方法时，可以用更简洁的 :: 写法。
 * 个人理解，就是方法直接塞入，类似前端如下：
 * const consoleLog = (item) => console.log(item);
 * list.forEach(consoleLog)
 *
 */

