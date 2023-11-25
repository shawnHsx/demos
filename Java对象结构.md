# Java对象结构

## Java对象主要分为三部分
1. 对象头（Object Header）: 存储对象的元信息，包括哈希码、GC 分代年龄、锁状态标志等
  * Mark word（标记字段）
  * Klass Point(类型指针)
  * 数组长度
2. 实例数据（Instance Data）: 存储对象的实例变量（成员变量）数据
  * 一个对象的实例数据大小，等于它所有成员变量大小，以及继承类的变量的大小的和。
  * 静态变量存放在方法区，因此不占用对象内存。
3. 对齐填充（Padding）:
 * 为了保证对象在内存中的地址是8字节的整数倍，JVM 会在实例数据后添加一些额外的字节,提高内存读写的效率
 * 一个对象大小必须为8的整数倍。如果对象头+实例数据 = 57个字节。那么对齐填充就为7字节。对象头+实例数据+对齐填充 = 64字节。正好8的整数倍

![image](https://github.com/shawnHsx/demos/assets/10954975/fae59b7b-cc58-476d-8d00-9647e4dee2c4)

# 对象头（Object Header）
对象头分为如下三部分：
## Mark Word (64bit)

![image](https://github.com/shawnHsx/demos/assets/10954975/0423e53a-1ee2-4f40-ab79-c28cec2505a9)


## Klass Point(32bit)

大小为4字节，指向方法区中，该对象的类型。

## 数组长度（32bit）

只有当创建的是数组时，才有该部分，大小为4字节，代表当前数组的长度。非数组时，不存在。


## 查看对象工具 JOL

```XML []
<dependency>
    <groupId>org.openjdk.jol</groupId>
    <artifactId>jol-core</artifactId>
    <version>0.14</version>
</dependency>
```





