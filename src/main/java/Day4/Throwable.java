package Day4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// ts 的 try catch 升级版

public class Throwable {
    public static void main(String[] args) {
        // ========== 1. try-catch-finally ==========
        try {
            int a = 666 / 0;
        } catch (ArithmeticException e)  {
            System.out.println("捕获到异常: " + e.getMessage());
        } finally {
            System.out.println("最后的最后");
        }

        // ========== 2. 多重 catch ==========
        try {
            String str = null;
            str.length();
        } catch (NullPointerException e) {
            System.out.println("空指针异常");
        } catch (Exception e) {
            System.out.println("其他异常");
        }


        // ========== 3. try-with-resources（自动关闭资源） ==========
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader("test.txt"));
            String line = reader.readLine();
        } catch(IOException e) {
            e.printStackTrace();

        } finally {
            if (reader != null) {
                try {
                    reader.close();  // 关闭流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        // ✅ 新写法：try-with-resources
                try (BufferedReader reader1 = new BufferedReader(new FileReader("test.txt"))) {
                    String line = reader1.readLine();
                    System.out.println(line);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        // reader 会自动调用 close()，无需 finally

    }


}
