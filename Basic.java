package cn.shine.icumaster.util;

/**
 * author:
 * 时间:2018/3/5
 * qq:1220289215
 * 类描述：
 */

/**
 * java中==和equals和hashCode的区别
 * equals使用==实现，==比较的是对象的地址，hashcode返回一个int类型值，用来查到对象，equals用来判断相同hashcode的对象是否相等。
 *两个对象相同，他们的hashcode一定相同，两个对象不同，他们的hashcode可以相同。
 * 一个应用的一次执行，同一对象hashcode必须相同，但不同次执行，同一对象的hashcode不必相同。
 *  Protocol Buffers在不同进程返回不同hashcode。不应该在分布式应用程序中使用hashcode，替代方法：SHA1。

 int与integer的区别
 integer是对象，对int的包装，可将int->字符串，字符串->int。

 探探对java多态的理解
 服类引用持用子类对象

 String、StringBuffer、StringBuilder区别
 string是不可改变的，stringBuffer和string类似，是线程安全的，stringBuilder是线程不安全的，效率高，都能
 对字符串增删改查。

 string 转换成 integer的方式及原理

 string 设计成final，不让继承，为了安全，作为键值具有唯一性，在线程中保证线程安全。

 什么是内部类？内部类的作用
 内部类是指在一个外部类的内部再定义一个类。内部类作为外部类的一个成员，并且依附于外部类而存在的。
 内部类可为静态，可用protected和private修饰（而外部类只能使用public和缺省的包访问权限）。
 内部类主要有以下几类：成员内部类、局部内部类、静态内部类、匿名内部类
作用：每个内部类都能独立地继承自一个（接口的）实现，间接实现多继承。

 抽象类和接口区别
 抽象类：不全是抽象方法，有构造参数,对共性的抽象，适合重构，单继承
 接口：全是public抽象方法，属性都是,没有构造参数，对动作的重新，多继承

 抽象类的意义：利于代码的复用和维护，体现面向对象思想，提取事物的共性，把特性留给子类实现。比如模板方法
 接口的意义：定义了做什么，具体怎么做由子类实现。使用接口的人和实现接口的人分开，很好的解耦


 泛型中extends和super的区别
 向容器添加元素的前提?
 PECS原则：Producer extend，Consumer super
 Collection<? extends T> 把集合中每个元素都当T处理，运行时不知道子类具体类型，转化失败，不能添加元素，只能获取。
 List<Integer>ints = Arrays.asList(1,2,3);
 List<Double>doubles = Arrays.asList(2.78,3.14);
 以上两个引用都可以作为sum(Collection<? extends Number> nums) 的参数 ，如果没有extend 则不行。

 Collection<? super T> 把集合中每个元素都当T处理，运行时不知道父类具体类型。
？？？应用

 父类的静态方法能否可以被继承？是否可以被重写？以及原因？
 进程和线程的区别
 final，finally，finalize的区别
 序列化的方式
 Serializable 和Parcelable 的区别

 静态内部类的设计意图
 可以使private修饰，不持用外部类应用，如Handler的使用，防止内存泄露。

 成员内部类、静态内部类、局部内部类和匿名内部类的理解，以及项目中的应用
 谈谈对kotlin的理解

 闭包和局部内部类的区别

 */

public  class Basic {

    @Override
    public int hashCode() {
        return 100;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }


}
