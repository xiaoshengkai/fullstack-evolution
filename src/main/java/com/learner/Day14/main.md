## 参数校验：用 @Valid + @NotNull 等注解防止空值、非法参数。

### 一、为什么需要参数校验？
现在你的 addTodo 直接接收前端传来的 TodoItem，如果前端漏传 title 或 priority，Service 层会抛出异常，返回错误信息不友好。参数校验就是在 Controller 层就把“不合格”的请求挡在门外，并给出清晰的错误提示。

前端类比：就像你在 React 中对表单输入做校验，不合规则直接 alert("请输入标题")，不发送请求。Java 的参数校验是同样的作用。

### 二、添加依赖
在 pom.xml 中加上 spring-boot-starter-validation（Spring Boot 2.x 默认已集成，但显式声明更清晰）：
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```