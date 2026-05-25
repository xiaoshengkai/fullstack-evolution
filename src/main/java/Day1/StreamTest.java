package Day1;
import java.util.*;
import java.util.stream.Collectors;

// stream
// 就是js中的高阶数组操作

public class StreamTest {
    public static void main (String[] args) {
        List<Integer> list = Arrays.asList(1,3,29,33,12, 88, 99,  321,999,9999);

        // ========== 1. 过滤：找出所有及格分数 ==========
        List<Integer> passList =  list.stream()  // 开启流
                                .filter(num -> num >= 60) // 过滤
                                .collect(Collectors.toList()); // 格式化

        System.out.println("及格人数: " + passList.size());
        System.out.println("及格分数: " + passList);

        // JS 对比：
        // const passList = scores.filter(s => s >= 60)


        // ========== 2. 统计：平均分 ==========
        double avg = list.stream()
                    .mapToInt(Integer::intValue) // 把 Integer 拆箱成 int
                    .average() // 平均分方法
                    .orElse(0.0); // 缺省

        System.out.println("平均分: " + avg);
        // JS 对比：
        // const avg = scores.reduce((a,b) => a+b, 0) / scores.length;

        // ========== 3. 统计：最高分和最低分 ==========
        int max = list.stream()
                    .mapToInt(Integer::intValue) // 等于 mapToInt(score -> score.intValue())
                    .max() // 最大值
                    .orElse(0);
        int min = list.stream()
                    .mapToInt(Integer::intValue)
                    .min() // 最大值
                    .orElse(0);

        System.out.println("最高分: " + max + ", 最低分: " + min);

        // JS 对比：
        // const max = Math.max(...scores);
        // const max = Math.min(...scores);

        // ========== 4. 分组：按成绩等级分类 ==========
        Map<String, List<Integer>> group = list.stream().collect(Collectors.groupingBy((score) -> {
            if (score < 60) {
                return "不及格";
            }

            if (score <= 200) {
                return "良好";
            }

            return "优秀";
        }));

        System.out.println("分组结果: " + group);

        // JS 对比：
        // const grouped = scores.reduce((acc, s) => {
        //     const key = s>=90 ? '优秀' : s>=80 ? '良好' : ...;
        //     (acc[key] ||= []).push(s);
        //     return acc;
        // }, {});

        // ========== 5. 归约：及格分数的总和 ==========
        int passNum = list.stream()
                        .filter(n -> n >= 60)
                        .reduce(0, (a, b) -> a + b); // 等于reduce(0, Integer::sum)

        System.out.println("及格总分: " + passNum);
        // JS 对比：
        // const passSum = scores.filter(s => s>=60).reduce((a,b) => a+b, 0);

    }
}


// 额外知识点
/*
1、int 与 Integer 的区别：
    int 是基本类型，直接存数值；Integer 是类（包装类型），存的是对象的引用。
    Java	JavaScript/TypeScript
    int	number（原始类型）
    Integer	Number（包装对象，但 JS 自动装箱几乎无感）
    List<Integer>	Array<number>
* */
