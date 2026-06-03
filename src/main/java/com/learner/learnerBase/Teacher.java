package com.learner.learnerBase;

// 继承
public class Teacher extends  User {
    String name;

    // 构造函数
    public Teacher(String name) {
        this.name = name;
    }


    // 重写
    @Override
    public void setAge(int age) {
        super.setAge(age + 1);
    }

    public String speak (String message) {
        return "虚岁年龄为" + this.getAge() + "的老师叫" + this.name + "回了声：" + message;
    }
}
