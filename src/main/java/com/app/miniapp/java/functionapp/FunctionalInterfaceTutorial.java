package com.app.miniapp.java.functionapp;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.DayOfWeek;

/**
 * Java函数式接口深度解析：Function、Supplier、BiConsumer
 * 结合实际业务场景的完整示例
 */
public class FunctionalInterfaceTutorial {

    // ==================== 1. 基础概念理解 ====================

    /**
     * Function<T, R> 接口解析
     * - 表示接受一个参数并返回结果的函数
     * - 核心方法：R apply(T t)
     * - 用途：数据转换、属性提取、计算等
     */
    public static void demonstrateFunction() {
        System.out.println("=== Function<T, R> 基础用法 ===");

        // 1. 简单的字符串长度计算
        Function<String, Integer> getLength = String::length;
        System.out.println("字符串长度: " + getLength.apply("Hello")); // 输出: 5

        // 2. 对象属性提取（类似你代码中的getProv, getBizDate, getCnt）
        Function<Student, String> getName = Student::getName;
        Function<Student, Integer> getAge = Student::getAge;

        Student student = new Student("张三", 20, "计算机");
        System.out.println("姓名: " + getName.apply(student));
        System.out.println("年龄: " + getAge.apply(student));

        // 3. Function的链式操作
        Function<String, String> toUpperCase = String::toUpperCase;
        Function<String, String> addPrefix = s -> "Mr. " + s;
        Function<String, String> combined = toUpperCase.andThen(addPrefix);
        System.out.println("链式操作: " + combined.apply("john")); // 输出: Mr. JOHN
    }

    /**
     * Supplier<T> 接口解析
     * - 表示不接受参数但返回结果的供应商
     * - 核心方法：T get()
     * - 用途：对象创建、延迟计算、默认值提供等
     */
    public static void demonstrateSupplier() {
        System.out.println("\n=== Supplier<T> 基础用法 ===");

        // 1. 简单对象创建
        Supplier<String> stringSupplier = () -> "Hello World";
        System.out.println("字符串供应: " + stringSupplier.get());

        // 2. 对象实例化（类似你代码中的dtoSupplier）
        Supplier<Student> studentSupplier = Student::new;
        Student newStudent = studentSupplier.get();
        System.out.println("新建学生对象: " + newStudent);

        // 3. 延迟计算 - 只有在需要时才计算
        Supplier<Double> randomSupplier = Math::random;
        System.out.println("随机数1: " + randomSupplier.get());
        System.out.println("随机数2: " + randomSupplier.get()); // 每次调用都会重新计算

        // 4. 复杂对象创建工厂
        Supplier<List<String>> listFactory = ArrayList::new;
        List<String> list1 = listFactory.get();
        List<String> list2 = listFactory.get();
        System.out.println("是同一个对象吗? " + (list1 == list2)); // false，每次创建新对象
    }

    /**
     * BiConsumer<T, U> 接口解析
     * - 表示接受两个参数但不返回结果的操作
     * - 核心方法：void accept(T t, U u)
     * - 用途：属性设置、数据处理、批量操作等
     */
    public static void demonstrateBiConsumer() {
        System.out.println("\n=== BiConsumer<T, U> 基础用法 ===");

        // 1. 简单的打印操作
        BiConsumer<String, Integer> printer = (name, age) ->
                System.out.println(name + " 今年 " + age + " 岁");
        printer.accept("李四", 25);

        // 2. 对象属性设置（类似你代码中的setProv, setBizDate, setCnt）
        BiConsumer<Student, String> setName = Student::setName;
        BiConsumer<Student, Integer> setAge = Student::setAge;
        BiConsumer<Student, String> setMajor = Student::setMajor;

        Student student = new Student();
        setName.accept(student, "王五");
        setAge.accept(student, 22);
        setMajor.accept(student, "数学");
        System.out.println("设置后的学生: " + student);

        // 3. Map操作
        Map<String, Integer> map = new HashMap<>();
        BiConsumer<String, Integer> putToMap = map::put;
        putToMap.accept("apple", 10);
        putToMap.accept("banana", 20);
        System.out.println("Map内容: " + map);
    }

    // ==================== 2. 你的代码深度解析 ====================

    /**
     * 模拟你的业务场景：按周聚合数据
     * 这里用销售数据作为示例
     */
    public static class SalesData {
        private String province;    // 省份
        private String bizDate;     // 业务日期
        private Integer count;      // 销售数量

        public SalesData() {}

        public SalesData(String province, String bizDate, Integer count) {
            this.province = province;
            this.bizDate = bizDate;
            this.count = count;
        }

        // getter和setter方法
        public String getProvince() { return province; }
        public void setProvince(String province) { this.province = province; }
        public String getBizDate() { return bizDate; }
        public void setBizDate(String bizDate) { this.bizDate = bizDate; }
        public Integer getCount() { return count; }
        public void setCount(Integer count) { this.count = count; }

        @Override
        public String toString() {
            return String.format("SalesData{province='%s', bizDate='%s', count=%d}",
                    province, bizDate, count);
        }
    }

    /**
     * 日期工具类
     */
    public static class DateUtil {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        public static String getWeekEndDate(String dateStr) {
            LocalDate date = LocalDate.parse(dateStr, formatter);
            LocalDate weekEnd = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
            return weekEnd.format(formatter);
        }
    }

    /**
     * 你的原始方法的完整实现和详细注释
     */
    public static <T> List<T> aggregateByWeek(
            List<T> list,
            Function<T, String> getProv,        // Function：从T对象中提取省份
            Function<T, String> getBizDate,     // Function：从T对象中提取业务日期
            Function<T, Integer> getCnt,        // Function：从T对象中提取数量
            Supplier<T> dtoSupplier,            // Supplier：创建新的T对象实例
            BiConsumer<T, String> setProv,      // BiConsumer：设置T对象的省份
            BiConsumer<T, String> setBizDate,   // BiConsumer：设置T对象的业务日期
            BiConsumer<T, Integer> setCnt) {    // BiConsumer：设置T对象的数量

        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }

        // 第一步：数据分组聚合
        // 外层Map：key是省份，value是该省份的周汇总数据
        // 内层Map：key是周结束日期，value是该周的总数量
        Map<String, Map<String, Integer>> grouped = list.stream()
                .collect(Collectors.groupingBy(
                        getProv,  // 使用Function提取省份进行第一层分组
                        Collectors.toMap(
                                dto -> DateUtil.getWeekEndDate(getBizDate.apply(dto)), // 使用Function提取日期并转换为周末日期
                                getCnt,        // 使用Function提取数量作为value
                                Integer::sum   // 相同key时进行数量累加
                        )
                ));

        // 第二步：将分组结果转回DTO列表
        List<T> result = new ArrayList<>();
        grouped.forEach((prov, weekMap) -> {           // 遍历每个省份
            weekMap.forEach((weekEnd, cnt) -> {        // 遍历该省份的每周数据
                T dto = dtoSupplier.get();             // 使用Supplier创建新对象
                setProv.accept(dto, prov);             // 使用BiConsumer设置省份
                setBizDate.accept(dto, weekEnd);       // 使用BiConsumer设置日期
                setCnt.accept(dto, cnt);               // 使用BiConsumer设置数量
                result.add(dto);
            });
        });

        return result;
    }

    // ==================== 3. 实际使用示例 ====================

    public static void demonstrateRealWorldUsage() {
        System.out.println("\n=== 实际业务场景演示 ===");

        // 准备测试数据：不同省份不同日期的销售数据
        List<SalesData> salesDataList = Arrays.asList(
                new SalesData("北京", "2024-01-15", 100),  // 周一
                new SalesData("北京", "2024-01-16", 150),  // 周二
                new SalesData("北京", "2024-01-22", 200),  // 下周一
                new SalesData("上海", "2024-01-15", 120),  // 周一
                new SalesData("上海", "2024-01-17", 80),   // 周三
                new SalesData("广东", "2024-01-16", 300)   // 周二
        );

        System.out.println("原始数据:");
        salesDataList.forEach(System.out::println);

        // 调用聚合方法
        List<SalesData> weeklyData = aggregateByWeek(
                salesDataList,
                SalesData::getProvince,      // Function: 提取省份
                SalesData::getBizDate,       // Function: 提取业务日期
                SalesData::getCount,         // Function: 提取数量
                SalesData::new,              // Supplier: 创建新对象
                SalesData::setProvince,      // BiConsumer: 设置省份
                SalesData::setBizDate,       // BiConsumer: 设置日期
                SalesData::setCount          // BiConsumer: 设置数量
        );

        System.out.println("\n按周聚合后的数据:");
        weeklyData.forEach(System.out::println);
    }

    // ==================== 4. 进阶用法和最佳实践 ====================

    /**
     * Function的高级用法
     */
    public static void advancedFunctionUsage() {
        System.out.println("\n=== Function 进阶用法 ===");

        // 1. Function.identity() - 返回输入参数本身
        Function<String, String> identity = Function.identity();
        System.out.println("Identity: " + identity.apply("test"));

        // 2. Function组合：compose vs andThen
        Function<Integer, Integer> multiply2 = x -> x * 2;
        Function<Integer, Integer> add3 = x -> x + 3;

        Function<Integer, Integer> composeResult = multiply2.compose(add3);  // 先执行add3，再执行multiply2
        Function<Integer, Integer> andThenResult = multiply2.andThen(add3);  // 先执行multiply2，再执行add3

        int input = 5;
        System.out.println("compose(5): " + composeResult.apply(input));  // (5+3)*2 = 16
        System.out.println("andThen(5): " + andThenResult.apply(input));  // (5*2)+3 = 13

        // 3. Function在Stream中的应用
        List<String> names = Arrays.asList("alice", "bob", "charlie");
        List<Integer> lengths = names.stream()
                .map(String::length)  // Function<String, Integer>
                .collect(Collectors.toList());
        System.out.println("名字长度: " + lengths);
    }

    /**
     * Supplier的高级用法
     */
    public static void advancedSupplierUsage() {
        System.out.println("\n=== Supplier 进阶用法 ===");

        // 1. 延迟初始化
        class ExpensiveResource {
            public ExpensiveResource() {
                System.out.println("创建昂贵的资源...");
            }
            public void doWork() {
                System.out.println("执行工作...");
            }
        }

        Supplier<ExpensiveResource> lazyResource = ExpensiveResource::new;
        System.out.println("Supplier已创建，但资源还未初始化");

        ExpensiveResource resource = lazyResource.get();  // 这时才真正创建资源
        resource.doWork();

        // 2. Optional中的应用
        Optional<String> optional = Optional.empty();
        String result = optional.orElseGet(() -> "默认值");  // Supplier提供默认值
        System.out.println("Optional结果: " + result);

        // 3. 缓存模式
        Map<String, String> cache = new HashMap<>();
        Function<String, String> cachedFunction = key ->
                cache.computeIfAbsent(key, k -> {
                    System.out.println("计算 " + k + " 的值");
                    return "computed_" + k;
                });

        System.out.println("第一次获取: " + cachedFunction.apply("test"));
        System.out.println("第二次获取: " + cachedFunction.apply("test")); // 从缓存获取
    }

    /**
     * BiConsumer的高级用法
     */
    public static void advancedBiConsumerUsage() {
        System.out.println("\n=== BiConsumer 进阶用法 ===");

        // 1. BiConsumer链式操作
        BiConsumer<Student, String> setName = Student::setName;
        BiConsumer<Student, String> printName = (student, name) ->
                System.out.println("设置姓名: " + name);

        BiConsumer<Student, String> combined = setName.andThen(printName);

        Student student = new Student();
        combined.accept(student, "测试学生");

        // 2. Map的批量操作
        Map<String, Integer> scores = new HashMap<>();
        BiConsumer<String, Integer> addScore = (name, score) -> {
            scores.put(name, scores.getOrDefault(name, 0) + score);
            System.out.println(name + " 的总分现在是: " + scores.get(name));
        };

        addScore.accept("张三", 85);
        addScore.accept("张三", 92);  // 累加分数

        // 3. 错误处理
        BiConsumer<String, Exception> errorHandler = (operation, ex) ->
                System.err.println("操作 " + operation + " 失败: " + ex.getMessage());

        try {
            // 模拟可能出错的操作
            throw new RuntimeException("模拟错误");
        } catch (Exception e) {
            errorHandler.accept("数据处理", e);
        }
    }

    // ==================== 5. 性能和最佳实践 ====================

    /**
     * 性能考虑和最佳实践
     */
    public static void bestPractices() {
        System.out.println("\n=== 最佳实践 ===");

        // 1. 重用Function而不是重复创建
        Function<String, Integer> lengthFunction = String::length;
        // 好的做法：重用同一个Function
        List<String> words = Arrays.asList("hello", "world", "java");
        words.stream().map(lengthFunction).forEach(System.out::println);

        // 2. 使用方法引用而不是Lambda（当可能时）
        // 推荐：Student::getName
        // 而不是：student -> student.getName()

        // 3. 考虑异常处理
        Function<String, Integer> safeParseInt = s -> {
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                return 0;  // 默认值
            }
        };

        System.out.println("安全解析: " + safeParseInt.apply("123"));
        System.out.println("安全解析: " + safeParseInt.apply("invalid"));

        // 4. 空值检查
        Function<Student, String> safeName = student ->
                student != null ? student.getName() : "未知";

        System.out.println("安全获取姓名: " + safeName.apply(null));
    }

    // ==================== 辅助类 ====================

    static class Student {
        private String name;
        private Integer age;
        private String major;

        public Student() {}

        public Student(String name, Integer age, String major) {
            this.name = name;
            this.age = age;
            this.major = major;
        }

        // getter和setter方法
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Integer getAge() { return age; }
        public void setAge(Integer age) { this.age = age; }
        public String getMajor() { return major; }
        public void setMajor(String major) { this.major = major; }

        @Override
        public String toString() {
            return String.format("Student{name='%s', age=%d, major='%s'}",
                    name, age, major);
        }
    }

    // ==================== 主函数演示 ====================

    public static void main(String[] args) {
        demonstrateFunction();
        demonstrateSupplier();
        demonstrateBiConsumer();
        demonstrateRealWorldUsage();
        advancedFunctionUsage();
        advancedSupplierUsage();
        advancedBiConsumerUsage();
        bestPractices();
    }
}
