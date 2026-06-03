package com.learner.Day4;

// 文件流操作

//4.1 字节流 vs 字符流
//类型	基类	用途	典型子类
//字节输入流	InputStream	读取原始字节	FileInputStream
//字节输出流	OutputStream	写入原始字节	FileOutputStream
//字符输入流	Reader	读取文本字符	FileReader, BufferedReader
//字符输出流	Writer	写入文本字符	FileWriter, BufferedWriter
//规则：
//
//二进制文件（图片、视频、PDF）→ 字节流
//
//文本文件（txt、csv、json）→ 字符流

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class IO {

    // ========== 综合实战(csv) =============
    static class Order {
        public void handle() {
            // 写入
            this.writeDataToFile();

            // 读取并统计
        }

        public void writeDataToFile() {
            String[] orders = {
                    "手机,4999,电子产品,false",
                    "Java核心编程,89,图书,true",
                    "牛奶,5.5,食品,false"
            };

            String filePath  = System.getProperty("user.dir") + "/src/main/java/Day4/order.csv"; // 文件路径
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));) {
                writer.write("名称,价格,分类,是否发货");
                writer.newLine();
                Arrays.stream(orders).forEach((line) -> {
                    try {
                        writer.write(line);
                        writer.newLine();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                });

                System.out.println("数据写入成功: orders.csv");
            } catch (IOException e) {
                e.printStackTrace();
            }

//            try {
//                Files.write(Paths.get(filePath), Arrays.asList(orders), StandardCharsets.UTF_8);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }

    public static void main(String[] args) {
//        try {
//            URL classLocation = IO.class.getProtectionDomain().getCodeSource().getLocation();
//            String classPath = new File(classLocation.toURI()).getPath();
//            System.out.println("类所在目录: " + classPath);
//        } catch (URISyntaxException e) {
//            System.err.println("❌ url: " + e.getMessage());
//            e.printStackTrace();
//        }

        // 调试：查看当前工作目录
        System.out.println("当前工作目录: " + System.getProperty("user.dir"));
        String filePath = System.getProperty("user.dir") + "/src/main/java/Day4/test.txt"; // 文件路径

        // ================ 读取文本文件 ============
        // 方式1：BufferedReader 逐行读取（经典方式）
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.err.println("❌ 找不到文件: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("❌ 读取文件时发生错误: " + e.getMessage());
            e.printStackTrace();
        }

        // 方式2：Java 8 Files.lines() 获取 Stream（现代方式）
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            lines.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ================ 写入文本文件 ============
        // 方式1：BufferedWriter
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Hello Java IO!");
            writer.newLine();        // 换行
            writer.write("第二行内容");
            writer.newLine();        // 换行
            writer.write("6666");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 方式2：Java 8 Files.write() 一次性写入
        try {
            Files.write(Paths.get(filePath), Arrays.asList("Hello Java IO!", "第二行内容", "6666"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ================  文件复制（字节流实战） ============
        int random = (int) (Math.random() * 1000000); // 获取随机数
        String outFilePath = System.getProperty("user.dir") + "/src/main/java/Day4/test-" + random + ".txt"; // 文件路径
        System.out.println("复制路径：" + outFilePath);

        try(FileInputStream source = new FileInputStream(filePath);
            FileOutputStream target = new FileOutputStream(outFilePath)
        ) {
            byte[] buffer = new byte[1024];  // 缓冲区（1KB）
            int byteLine;

            while ((byteLine = source.read(buffer)) != -1) {
                target.write(buffer, 0, byteLine);
            }

            System.out.println("文件复制完成: " + source + " -> " + target);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        // 实战
        new Order().handle();
    }

}
