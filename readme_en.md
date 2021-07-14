# introduction
It is an indisputable fact that the expression ability of java language is somewhat inferior compared with modern language.

However, those highly expressive languages have not yet been expected to surpass java in market share for a short period of time.

In other words, even if you like scala, kotlin, groovy, etc., most people still have to use java at work.

Since you can't choose, simplify it and work happily.

The goal of this project is to simplify the java code we write.

There are two main ways.

One is to summarize some of the practices that can simplify the code.

The second is to provide some top-level functions to simplify the code by using top-level functions.

# practice
You can be happier with lombok, combined with the top-level functions defined in the project, and then combined with guava, spring-core, apache-commons.

- Use lombok annotations

Auto-generate pojo's getter/setter, equals/hashcode, builder, tostring, allArgConstructor.

Auto-close streams are cleaner than terry-resource.

In the vast majority of cases, it is no longer necessary to capture checked Exception.

Local variable types are automatically derived.

- Supports block of text using lombok-ext annotations.

- Use the top-level function defined by the project.

Includes, but is not limited to, related functions such as type, collection, string, reflection call, date, function, etc.
(If you don't like top-level functions, you can also use them as tool classes, refer to FuncAsUtilsTest)

- Other features, using other tripartite libraries such as guava, spring-core, apache-commons-**.

# usage
- add dependency
```
<dependency>
    <groupId>org.sirenia</groupId>
    <artifactId>func-core</artifactId>
    <version>${func-core.version}</version>
</dependency>
```
- import functions
```
import static org.sirenia.topfunc.TopFunc.*;
```
- use functions
```
public static void main(String[] args){
    println(listOf("how","are","you"));
}
```
# example

https://github.com/zhoujiaping/func/blob/master/src/test/java/org/sirenia/func/FuncAsSourceMixinTest.java

# extension
- 1 add top functions

if you wanna add top functions, you should add it into the package org.sirenia.func.core

if the function is very simple, you can add it into XxxFunc directly.

else i suggest you add them into util class witch named Xxxs,

then XxxFunc call it.

- 2 generate mixin java source file

you need add a class name into the @SourceMixin on org.sirenia.func.FuncMixin(if need).

then run org.sirenia.func.mixin.MixinTextImpl#main.

it will generate a java source file named Func(it contains all top function we want to export).

then execute the gradle task uploadArchives.