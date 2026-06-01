package learnerBase; // 包声明（类似前端的 namespace 命名空间 或目录结构）

import java.util.Arrays;

public class Main {
    // 1、数据类型
    int age = 25; // 整数
    double price = 99.99; // 浮点
    char grade = 'A'; // 单字符 （单引号）
    boolean isActive = false; // 布尔型
    String name = "小盛开"; // 字符串是引用类型（双引号）
    int[] arr = { 1,2,3,4,5 }; // 数组


    // 2、变量与常量
    int count = 10; // 局部变量
    static String appName = "APP"; // 静态变量（类级别）
    final double PI = 3.141592; // 常量（final = const）


    /*
    * 3、运算符
    * 基本与 JS 一致，少数差异
    * +, -, *, /, %, >, <, >=, <=, &&, ||, !
    * */
    double computed () {
        int a = 1;
        int b = 2;
        int c = 3;
        return (double) (a + b) / c * b - 3;
    }

    Object[] compare() {
        int a = 1;
        int b = 2;
        int c = 3;

        boolean r1 = a < b;
        boolean r2 = a >= c;
        boolean r3 = a != c;
        boolean r4 = a == c;
        int r5 = a > b ? a : c;
        boolean r6 = a < b && a < c;
        boolean r7 = a > b || a > c;

        return new Object[]{ r1, r2, r3, r4, r5, r6, r7, };
    }

    String stringConnect() {
        return "我的名字是" + this.name;
    }

    // 4、流程控制（与 JS 几乎一致）
    void controlFlow () {
        if (this.age > 24) {
            System.out.println("if: 我年纪挺大的");
        }

        switch(this.age) {
            case 25:
                System.out.println("switch: 我年纪挺大的");
                break;
            case  18:
                System.out.println("我年纪挺年轻的");
                break;
        }

        /*  循环    */
        // for 循环（与 JS 完全一样）
        for (int i = 0; i < this.count; i++) {
            System.out.print("计数 for：" + i +";");
        }
        System.out.println();

        // 增强 for 循环（类似 JS 的 for...of）
        int[] arr = new int[this.count];
        for (int i = 0; i < this.count; i++) {
            arr[i] = i;
        }
        for (int i : arr) {
            System.out.print("计数 for...of：" + i+";");
        }
        System.out.println();

        // while 循环
        int _count = 0;
        while (_count < this.count) {
            // 循环体
            System.out.print(" 计数 while：" + _count+";");
            _count++;
        }
        System.out.println();
    }


    // 5、函数
    /*
    * public  static  int   add  (int a, int b)
      ↑       ↑      ↑     ↑       ↑
    访问修饰符  静态  返回值  方法名   参数列表
    * */
    public static int add (int a, int b) {
        return a + b;
    };

    public int sub(int a, int b) {
        return a - b;
    };

    // 主方法：程序的唯一入口（固定写法，死记即可）
    public static void main (String[] arg) {
        System.out.println("java 基础");

        Main obj = new Main();

        System.out.println("局部变量：" + obj.count );
        System.out.println("静态变量（类级别）：" + Main.appName );
        System.out.println("常量（final = const）：" + obj.PI );

        System.out.println("计算（加减乘除）：" + obj.computed() );
        System.out.println("计算（比较）：" + Arrays.toString(obj.compare()));
        System.out.println("计算（模版字符串）：" + obj.stringConnect());

        obj.controlFlow();

        System.out.println("函数（加法）：" + Main.add(1, 2));
        System.out.println("函数（减法）：" + obj.sub(1, 2));

        System.out.println("java 基础 - 类/对象");

        /*   ***对象 ***    */

        // 封装
        // 类似JS 中用闭包隐藏变量
        User user = new User();
        user.setAge(18);
        System.out.println("你的年龄为：" + user.getAge());

        // 继承
        // extends 关键字，java 只能单继承（区别于c++多继承）
        Student stu = new Student("小明");
        stu.setAge(19);
        System.out.println(stu.speak("你好"));

        // 多态
        // 父类指向子类，调用子类重写父类的方法
        // js原型链查找是一个思路：不关心对象是什么类型，只关心它有没有对应的方法。
        User user1 = new User();
        Student stu1 = new Student("小红");
        stu1.setAge(6);
        Teacher tea1 = new Teacher("杨老师");
        tea1.setAge(26);
        System.out.println(user1.selfSepack(stu1, "杨老师早上好"));
        System.out.println(user1.selfSepack(tea1, "小红你好啊"));

        // 抽象 和 接口（Interface），只定义不实现
        // extends：继承一个类（单继承），获得父类的属性和方法。
        // implements：实现一个接口（多实现），必须实现接口中定义的所有方法。

    }
}
