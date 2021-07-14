# 使用lombok拓展java现有对象

## 实现方式
注解和注解处理器，拓展类，被拓展类，拓展函数作用类

* 定义拓展类

就是普通的类，但是要求其拓展方法是public static，
并且方法第一个参数是被拓展类的类型。

* 使用拓展类

在使用拓展类的类上面加注解 @ExtensionMethod(Extensions.class)
（其中Extensions.class可以换成其他拓展类，支持多个）

## 基本原则
- 目标是拓展jdk，简化代码
  所以更多的是关注语法和基础库层面的问题。
- 添加常用操作
  不常用操作自己写复杂代码或者用第三方库
- 保持一致性
  
- 尽量兼容java后续版本的语法和库

## 限制
- 复杂代码
lombok的注解处理器会解析java代码，得到语法树。
它内置了一个简易的编译器，功能较弱，在拓展类中有复杂语法的情况下容易出错。
建议将实际的功能在其他类中实现，拓展类中只是作为一个拓展的声明。
  
貌似在使用的方法里面，同时有多个lambda表达式也容易编译错误。
- 不支持静态方法
- 集成开发环境支持貌似还不是很好

给github上面提了一个issue，很久以前就有人提过，但是这个问题一直都还在。
失望了，这个功能还是别用了。建议退而求其次，用@Delegate吧。

@Delegate也有问题，还是用java原生的方式静态导入吧。



