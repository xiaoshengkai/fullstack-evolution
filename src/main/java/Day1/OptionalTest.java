package Day1;
import java.util.Optional;

// 可选链

// 避免空指针，在 Java 里，如果你尝试对 null 调用方法或访问属性，程序会立刻抛出 NullPointerException（空指针异常）
// 类似前端报错：Uncaught TypeError: Cannot read properties of null (reading 'xxx')
public class OptionalTest {

    static class  Address {
        private String address;

        public Address(String city) {
            this.address = city;
        }

        public String getCity() {
            return this.address;
        }

    }

    static class User {
        String name;
        Address address;

        public User (String name, Address address) {
            this.name = name;
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public Address getAddress () {
            return this.address;
        }
    }

    static String test (User user) {
        return Optional.ofNullable(user)  // 开启
                .map(User::getAddress)  // 获取Address对象
                .map(Address::getCity)  // 获取值
                .orElse("未知城市"); // 容错处理

        // 类似前端 user?.getAddress()?.getCity() ?? "未知城市";
        // 个人感觉没有前端优雅！！！
    }


    public static void main(String[] args) {
        User user1 = new User("李四", new Address("杭州"));
        User user2 = new User("李四", null);
        User user3 = null;

        System.out.println(OptionalTest.test(user1));
        System.out.println(OptionalTest.test(user2));
        System.out.println(OptionalTest.test(user3));

    }

}
