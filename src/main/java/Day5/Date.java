package Day5;

// 感觉就是内置了 前端的 moment.js
// LocalDate、LocalTime、LocalDateTime

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Date {
    public static void main(String[] args) {
        // ===== 获取当前日期/时间 =====
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        LocalDateTime dateTime = LocalDateTime.now();

        System.out.println(" // ===== 获取当前日期/时间 =====");
        System.out.println("今天: " + date);
        System.out.println("现在时间: " + time);
        System.out.println("完整日期时间: " + dateTime);

        // ===== 指定具体日期/时间 =====
        LocalDate specificDate = LocalDate.of(2026, 6, 1);
        LocalTime specificTime = LocalTime.of(11, 59,59);
        LocalDateTime specificDateTime = LocalDateTime.of(2026, 3, 3, 15, 29,58);

        System.out.println(" //  ===== 指定具体日期/时间 =====");
        System.out.println("指定日期: " + specificDate);
        System.out.println("指定时间: " + specificTime);
        System.out.println("指定日期时间: " + specificDateTime);

        // ===== 解析字符串 =====
        LocalDate parsedDate = LocalDate.parse("2026-06-01");
        LocalTime parsedTime = LocalTime.parse("14:30:00");
        System.out.println(" ===== 解析字符串 =====");
        System.out.println("解析出的日期: " + parsedDate);
        System.out.println("解析出的时间: " + parsedTime);

        // =====  获取日期字段 =====
        LocalDate date1 = LocalDate.of(2026, 6, 1);
        System.out.println("年: " + date1.getYear());         // 2026
        System.out.println("月: " + date1.getMonthValue());   // 6（注意不是从0开始！）
        System.out.println("日: " + date1.getDayOfMonth());   // 1
        System.out.println("星期: " + date1.getDayOfWeek());  // MONDAY
        System.out.println("是否闰年: " + date1.isLeapYear()); // false

        // =====  日期运算 =====
        LocalDate today = LocalDate.now();
        // 加减天数、月数、年数
        LocalDate tomorrow = today.plusDays(1);
        LocalDate nextWeek = today.plusWeeks(1);
        LocalDate nextMonth = today.plusMonths(1);
        LocalDate nextYear = today.plusYears(1);

        System.out.println("明天: " + tomorrow);
        System.out.println("下个月: " + nextMonth);

        // 减法一样
        LocalDate yesterday = today.minusDays(1);
        System.out.println("昨天: " + yesterday);

        // 比较日期
        boolean isAfter = today.isAfter(yesterday);
        boolean isBefore = today.isBefore(tomorrow);
        boolean isEqual = today.isEqual(LocalDate.of(2026, 6, 1));
        System.out.println("时间是否相同: " + isEqual);

        // LocalTime 和 LocalDateTime 的运算
        // 时间运算
        LocalTime time1 = LocalTime.of(14, 30);
        LocalTime later = time1.plusHours(2).plusMinutes(15);
        System.out.println("两小时十五分钟后: " + later);

        // 日期时间运算
        LocalDateTime dt = LocalDateTime.of(2026, 6, 1, 14, 30);
        LocalDateTime future = dt.plusDays(3).minusHours(1);
        System.out.println("三天后，减一个小时: " + future);

        // =====  Instant, 时间戳（精确到纳秒） =====
        Instant now = Instant.now();
        System.out.println("当前时间戳: " + now);         // 2026-06-01T06:30:25.123Z
        System.out.println("秒数: " + now.getEpochSecond()); // Unix 秒
        System.out.println("毫秒: " + now.toEpochMilli());   // Unix 毫秒（等同于 JS 的 Date.now()）


        // =====  DateTimeFormatter 格式化与解析 =====
        LocalDateTime date2 = LocalDateTime.now();
        System.out.println("内置格式: " + date2.format(DateTimeFormatter.ISO_LOCAL_DATE));
        System.out.println("自定义格式: " + date2.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
