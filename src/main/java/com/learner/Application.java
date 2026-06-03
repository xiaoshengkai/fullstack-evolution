package com.learner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class Application {
    public static void main (String[] args) {
        SpringApplication.run(Application.class, args);
        /**
         * 问题：为啥不直接 this.run(args);
         * 理由：
         * 1、java 基础：静态上下文中没有 this
         * 2、new MyBatisPlusTest().run(args);这样可以，但不会启动 Spring，因为这样做只是单纯执行了你写的业务代码，没有启动 Spring 容器，所有依赖注入（@Autowired）都不会生效。
         * 3、SpringApplication.run 做了什么？
         *   创建 Spring 应用上下文（容器）
         *   扫描并实例化所有 Bean（包括 TodoItemMapper 的代理实现类）
         *   处理依赖注入（给 @Autowired 字段赋值）
         *   自动配置（数据源、MyBatis-Plus 等）
         *   执行 CommandLineRunner（在你的 Bean 创建好之后，自动调用你的 run 方法）
         * */
    }
}
