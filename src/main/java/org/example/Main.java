package org.example;

//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
public class Main {
    public static void main(String[] args) {
        Base base = new Base();

        // 查看 IntelliJ IDEA 建议如何修正。
        System.out.printf("Hello and welcome!\n");

        for (int i = 1; i <= 5; i++) {
//            System.out.println("i = " + i);

            base.add();
            System.out.println("base.a:"+base.a);
        }
    }
}