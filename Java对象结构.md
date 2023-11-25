# Java对象结构

## Java对象主要分为三部分
1. 对象头（Object Header）: 存储对象的元信息，包括哈希码、GC 分代年龄、锁状态标志等
  * Mark word（标记字段）
  * Klass Point(类型指针)
  * 数组长度
2. 实例数据（Instance Data）: 存储对象的实例变量（成员变量）数据
3. 对齐填充（Padding）: 为了保证对象在内存中的地址是8字节的整数倍，JVM 会在实例数据后添加一些额外的字节,提高内存读写的效率

## Mark Word (64bit)

## Klass Point(32bit)

## 数组长度（32bit）

![image](https://github.com/shawnHsx/demos/assets/10954975/fae59b7b-cc58-476d-8d00-9647e4dee2c4)




