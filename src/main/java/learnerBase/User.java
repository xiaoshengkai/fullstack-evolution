package learnerBase;

public class User extends UserAbstract {
    // 封装
    private int age;

    public String speak(String message) {
        return null;
    }


    public int getAge () {
        return age; // 等于 this.age
    }

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("'输入年龄参数不对'");
        }
        this.age = age;
    }

    public String selfSepack(User user, String message) {
        return user.speak(message);
    }
}
