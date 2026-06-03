package com.learner.learnerBase;

// 继承
public class Student extends  User {
    String name;

    // 构造函数
    public Student(String name) {
        this.name = name;
    }


    // 重写
    @Override
    public void setAge(int age) {
        super.setAge(age + 1);
    }

    public String speak (String message) {
        return "虚岁年龄为" + this.getAge() + "的学生叫" + this.name + "说了声：" + message;
    }
}
