package com.learner.Day3;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

// 统计一批数字的计数、总和、最小值、最大值、平均值，封装成一个统计对象


class CustomCollector {
    static public class Stats {
        int count;
        int sum;
        Integer min;
        Integer max;
        double avg;

        public Stats() {
            this.count = 0;
            this.sum = 0;
            this.avg = 0;
        }

        public void accept (int value) {
            count++;
            sum += value;
            min = min != null ? Math.min(min, value) : value;
            max = max != null ? Math.max(max, value) : value;
            avg =  (double) sum / count;
        }

        public void combine (Stats stats) {
            count += stats.count;
            sum += stats.sum;
            min = Math.min(min, stats.min);
            max = Math.max(max, stats.max);
            avg =  (double) sum / count;
        };


        @Override
        public String toString() {
            return String.format("数量=%d, 总和=%d, 最小=%d, 最大=%d, 平均=%.2f",
                    count, sum, min, max, avg);
        }
    }


    static public class StatsCollector implements Collector<Integer, Stats, Stats> {
        @Override
        public Supplier<Stats> supplier() {
            return Stats::new;
        }

        @Override
        public BiConsumer<Stats, Integer> accumulator() {
            return Stats::accept;
        }


        @Override
        public BinaryOperator<Stats> combiner() {
            return (s1, s2) -> {
                s1.combine(s2);              // 合并两个统计
                return s1;
            };
        }

        @Override
        public Function<Stats, Stats> finisher() {
            return Function.identity();      // 容器就是最终结果，无需转换
        }

        @Override
        public Set<Characteristics> characteristics() {
            return EnumSet.of(Characteristics.UNORDERED, Characteristics.IDENTITY_FINISH);
        }
    }


    public static void main(String[] args) {
        List<Integer> values = Arrays.asList(85, 92, 78, 95, 88);
        Stats stats = values.stream().collect(new StatsCollector());
        System.out.println(stats);
    }
}