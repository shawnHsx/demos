java动态代理是利用反射机制生成一个实现代理接口类的匿名类，在调用具体方法前调用InvokeHandler来处理。
而cglib动态代理是利用asm开源包，对代理对象类的class文件加载进来，通过修改其字节码生成子类来处理。

1、如果目标对象实现了接口，默认情况下会采用JDK的动态代理实现AOP
2、如果目标对象实现了接口，可以强制使用CGLIB实现AOP
3、如果目标对象没有实现了接口，必须采用CGLIB库，spring会自动在JDK动态代理和CGLIB之间转换

如何强制使用CGLIB实现AOP？
* 添加CGLIB库，SPRING_HOME/cglib/*.jar
* 在spring配置文件中加入<aop:aspectj-autoproxy proxy-target-class="true"/>

JDK动态代理和CGLIB字节码生成的区别？
* JDK动态代理只能对实现了接口的类生成代理，而不能针对类
* CGLIB是针对类实现代理，主要是对指定的类生成一个子类，覆盖其中的方法
  因为是继承，所以该类或方法最好不要声明成final

CallbackFilter

(1)MethodInterceptor：方法拦截器。
(2)NoOp.INSTANCE：这个NoOp表示no operator，即什么操作也不做，代理类直接调用被代理的方法不进行拦截。
(3)FixedValue：表示锁定方法返回值，无论被代理类的方法返回什么值，回调方法都返回固定值。


----代理类特性：
1.继承了Proxy类，实现了代理的接口，由于java不能多继承，这里已经继承了Proxy类了
  不能再继承其他的类，所以JDK的动态代理不支持对实现类的代理，只支持接口的代理。
2.提供了一个使用InvocationHandler作为参数的构造方法。
3.生成静态代码块来初始化接口中方法的Method对象，以及Object类的equals、hashCode、toString方法。
4.重写了Object类的equals、hashCode、toString，它们都只是简单的调用了InvocationHandler的invoke方法，即可以对其进行特殊的操作，也就是说JDK的动态代理还可以代理上述三个方法。
5.代理类实现代理接口的方法，只是简单的调用了InvocationHandler的invoke方法，
  我们可以在invoke方法中进行一些特殊操作，甚至不调用实现的方法，直接返回。

代理类如下：
public final class $Proxy0 extends Proxy implements Hello ｛
  ......详见$Proxy0文件
｝