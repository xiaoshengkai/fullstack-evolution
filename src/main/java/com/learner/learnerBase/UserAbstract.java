package com.learner.learnerBase;

// 抽象类
// 用 abstract 修饰的类，不能直接实例化，只能被继承
public abstract class  UserAbstract {
   // 抽象方法：强制子类实现
   abstract String speak(String message);
}
