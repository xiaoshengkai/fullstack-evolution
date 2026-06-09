## Java基本知识学习
## 一些问题汇总解答
### 1、int 和 Integer 的区别？泛型为什么不能用 int？
区别如下：

| 特性  | int | Integer |
|-----|-------|----------|
| 类型  | 基本数据类型 | 包装类（对象）|
| 内存  | 栈 | 堆 |
| 默认值 | 0 | null |
| 比较  | 值比较 == | 引用比较（== 比较对象地址，equal 比较值）|
| 空值  | 不能为null | 可以为null|
|性能 | 更高，直接操作 | 较低（对象开销）|
|自动装箱| Java 5+ 支持自动转换 | java 5+ 支持自动转换 |

#### 概念解析
- 自动装箱：基本类型 → 包装类（比如：int -> Integer）
- 自动拆箱：包装类 → 基本类型 （比如：Integer ->int）

> 其实就是狭义理解为前端的隐式转换

手动处理
```java 
// 手动装箱
int a = 100;
Integer aOBj = new Integer(a); // 显示创建 Integer 对象
integer aObj2 = Integer.valueOf(a); // 显示创建 Integer 对象

// 手动拆箱
Integer aOBj3 = new Integer(200);
int aa = aOBj3.intValue(); // 显式调用 intValue()

```

自动处理
```java
// 自动装箱
int a = 100;
Integer b = a;  // 编译器自动：Integer.valueOf(a)


// 自动拆箱
Integer bb = 200;
int aa = bb; // 编译器自动：bObj.intValue()
```

#### 那什么时候用int ，什么时候用 Integer？
##### 用int 场景
```java
// 场景1：性能关键的循环
int sum = 0;
for (int i = 0; i < 1000000; i++) {  // 用 int 性能更好
    sum += i;
}

// 场景2：局部变量计算
int result = calculate(a, b);  // a, b, result 都是 int

// 场景3：明确不为空的数值
int age = getUserAge();  // 确定不会返回 null
```

##### 用 Integer 场景
```java
// 场景1：集合操作
List<Integer> scores = new ArrayList<>();
Map<String, Integer> map = new HashMap<>();

// (重要！！！！)场景2：可能为 null 的返回值
Integer price = getProductPrice();  // 可能返回 null
if (price != null) {
    // 处理
}

// 场景3：数据库映射
@Entity
class Product {
    @Id
    private Long id;
    
    private Integer stock;  // 库存可能为 null
    
    // 使用包装类允许 null
}
```

#### 使用过程中有哪些坑？

##### 在做运算的时候得先拆箱再运算
```java
Integer a = 100;
int b = a;
int c = b + 200;

```

##### 比较陷阱
```java
Integer a = 127;
Integer b = 127;
// System.out.println(a == b);  // true（缓存范围 -128~127）

Integer c = 128;
Integer d = 128;
// System.out.println(c == d);  // false！超出缓存范围

System.out.println(a.equals(b));
System.out.println(c.equals(d));

```

#### 空指针问题
```java
Integer price = null;

// ❌ 危险：自动拆箱会导致 NPE（空指针异常）
int dangerous = price; // NullPointerException!

// ✅ 安全处理
if (price != null) {
    int safe = price;  // 先判空
}

```

#### 集合操作
本质上还是空指针问题
```java
List<Integer> numbers = new ArrayList<>();
numbers.add(1);     // 自动装箱：int → Integer
numbers.add(2);
numbers.add(null);  // ✅ 可以存 null

int sum = 0;
for (Integer num : numbers) {
    if (num != null) {  // 必须判空
        sum += num;     // 自动拆箱：Integer → int
    }
}

```

### 2、boolean 和 Boolean 的区别？
与上述int 和 Integer的关系一样
#### 有哪些差别
```java
// 默认值
boolean a; // false


// 构造方法的不同
Boolean b1 = new Boolean(true);      // true 基本类型
Boolean b2 = new Boolean("true");    // true 字符串（忽略大小写）
Boolean b3 = new Boolean("TRUE");    // true
Boolean b4 = new Boolean("abc");     // false（除了"true"，其他都是false）


// 推荐用 valueOf() 而不是 new
Boolean b5 = Boolean.valueOf(true);  // 利用缓存
```

### 3、数组 int[] 和 List<Integer> 的区别？
> 一句话：int[]是轻量级选手（快、省内存），List<Integer>是全能选手（功能丰富、灵活）

| 特性 | int[]      | List<integer> |
|----|------------| ----|
| 特性 | 基本数据类型的数组  | 包装类数组 |
| （最大区别）长度 | 固定（创建后不可变） | 动态（可自动扩容）|
|泛型 | 不支持 | 支持 |
|元素| 直接存储值 | 存储对象引用 |
| 内存 | 连续内存，紧凑 | 分散内存，有对象头开销|
|性能|更好|较低|
|工具方法|较少|丰富|

#### 如何创建

```java
// int[]
int[] arr1 = new int[5]; // 长度为5，默认值0
int[] arr2 = {1, 2, 3, 4}; // 直接初始化
int[] arr3 = new int[]{1, 2, 3, 4}; // 匿名数组


// List<Integer>
List<Integer> arr4 = new ArrayList<>;  // 空列表
List<Integer> arr5 = Arrays.asList(1,2,3); // 固定大小的列表
List<Integer> arr6 = new ArrayList<>(Arrays.asList(1,2,3)); // 可变列表

```

#### 增删改查的区别

```java
// int[]
int[] arr = {1, 2, 3, 4, 5};
// 读取
int a = arr[2];
// 修改
arr[2]=100;
// 添加（需要创建新数组）
int[] newArr = Arrays.copyOf(arr, arr.length + 1);
newArr[newArr.length - 1] = 200;
// 删除（需要创建新数组）
int[] removed = new int[arr.length - 1];
System.arraycopy(arr, 0, removed, 0, 2);
System.arraycopy(arr, 3, removed, 2, arr.length - 3);


// ========================== 分割线 ====================================================

// List<Integer> - 操作方便
List<Integer> listArr = new ArrayList<>(Arrays.asList(1,2,3,4,5,6));
// 读取
listArr.get(2); // 3
// 修改
listArr.set(2, 10); // [1,2,10,4,5,6]
// 添加
listArr.add(100); // 追加，[1,2,10,4,5,6,100]
listArr.add(0,200); // 指定位置添加，[200，1,2,10,4,5,6,100]
// 删除
listArr.remove(1); // 指定位置删除，[1,10,4,5,6,100]
listArr.remove(Integer.valueOf(5)); // 删除值为5的元素
// 查找
int index = listArr.indexof(200); // 返回位置，没有返回 -1
boolean exist = listArr.contains(500); // false
```
#### 工具方法的对比

```java
// ini[]，全部依赖Arrays的静态方法
int[] arr = {231, 321, 3, 4, 23, 1};

// 排序
Arrays.sort(arr); // {1, 3, 4, 23, 231, 321};

// 2. 二分查找（必须先排序）
int index = Arrays.binarySearch(arr, 4);  // 4

// 3. 填充
Arrays.fill(arr, 0); // {0, 0, 0, 0, 0, 0};

// 4. 复制
int[] copy = Arrays.copyOf(arr, 5);      // 复制前5个
int[] range = Arrays.copyOfRange(arr, 2, 5);  // 复制[2,5)范围

// 5.比较
Arrays.equals(arr1, arr2);

// 6. 转字符串
String str = Arrays.toString(arr);  // "[1, 2, 3, 4, 5]"

// 7. 一般都是转流来处理（Java 8+）
IntStream stream = Arrays.stream(arr);

// ========================== 分割线 ====================================================

// List<Integer>
List<Integer> list = new ArrayList<>(Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6));

// CURD
list.get(0);
list.set(0,123);
list.remove(2);
list.add(100);

// 2. 查找
int index = list.indexof(2); // 第一次出现的位置
int lastIdx = list.lastIndexOf(4);   // 最后一次出现的位置
boolean contains = list.contains(4); // 是否包含

// 3. 批量操作
list.addAll(Arrays.asList(1,2,3)); // 批量添加
list.removeAll(Arrays.asList(1,2,3)); // 批量删除
list.retainAll(Arrays.asList(3, 4, 5));  // 保留指定的

// 4. 排序
Collections.sort(list);                    // 自然排序
list.sort(Comparator.reverseOrder());      // 倒序
list.sort((a, b) -> b - a);                // 自定义排序

// 5. 子列表
List<Integer> subList = list.subList(2, 5);  // 子列表视图

// 6. 替换
list.replaceAll(x -> x * 2);  // 每个元素乘以2

// 7. 遍历
list.forEach(System.out::println);  // 消费

// 8. 转数组
Integer[] arr = list.toArray(new Integer[0]);  // 转对象数组
int[] intArr = list.stream().mapToInt(Integer::intValue).toArray();  // 转基本类型数组

```
#### Stream 操作的对比
这里就不仔细列了，区别在于以下两点

```java

// int[]需要 Arrays.stream 转
IntStream stream = Arrays.stream(new int[]{1,2,3});

// 基本运算
// int[] 直接 sum
stream.sum();

// List 需要 map转出来再做运算
Arrays.asList(1,2,3).stream().mapToInt(Integer::intValue).sum()

```
