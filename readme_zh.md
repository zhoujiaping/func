# 项目简介
java语言的表达能力，和现代语言比起来，的确有些逊色，这是不可争辩的事实。

然而，那些表达力很强的语言，短时间内还没有希望在市场份额上超越java。

也就是说，纵然你喜欢scala、kotlin、groovy等，大多数人在工作中还是不得不使用java。

既然不能选择，那就把它简化，然后快乐的工作。

本项目的目标，是简化我们写的java代码。

主要通过两种方式。

一是总结了一些实践，按照这些实践来，可以简化代码。

二是提供了一些顶层函数，通过使用顶层函数，简化代码。

# 实践
使用lombok，结合该项目中定义的顶层函数，再结合guava、spring-core、apache-commons—*，可以更快乐的coding。

- 使用lombok注解
  
自动生成pojo的getter/setter、equals/hashcode、builder、toString、allArgConstructor。

自动关闭流，比try-resource更简洁。

绝大多数情况不再需要捕获checkedException。

本地变量类型自动推导。

- 使用lombok-ext的注解，支持文本块。
  
- 使用该项目定义的顶层函数。

包括但不限于 类型、集合、字符串、反射调用、日期、函数式等相关函数。
  (如果你不喜欢顶层函数，你也可以将其按工具类的方式使用，参考 FuncAsUtilsTest)
  
- 其他功能，使用guava、spring-core、apache-commons-*等其他三方库。  

# 使用
- 添加依赖
```
<dependency>
    <groupId>org.sirenia</groupId>
    <artifactId>func</artifactId>
    <version>${func.version}</version>
</dependency>
```
- 导入函数
```
import static org.sirenia.func.Func.*;
```
- 使用函数
```
public static void main(String[] args){
    println(listOf("how","are","you"));
}
```
# 示例

https://github.com/zhoujiaping/func/blob/master/src/test/java/org/sirenia/func/FuncAsSourceMixinTest.java

# 拓展
- 1 新增顶层函数

如果你需要新增顶层函数，你应该将它们添加到org.sirenia.func.core包中。

如果函数比较简单，你可以直接加到XxxFunc类中。

否则，建议将函数添加到工具类中，然后在XxxFunc中调用它。

- 2 生成mixin的源代码

你需要将XxxFunc添加到org.sirenia.func.FuncMixin的SourceMixin注解上。

然后运行org.sirenia.func.mixin.MixinTextImpl#main。

这将会生成java源文件org.sirenia.func.Func。

它包含了我们在core包中定义的顶层函数。

然后再正常打包即可。