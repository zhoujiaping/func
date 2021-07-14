# 文本块
@TextBlock

TextBlocks#lazyInit

不足：注解里面字符串必须是常量，所以文本块在注解里面用不了。

使用:
- 添加依赖
```
<dependency>
    <groupId>com.mageddo.lombok</groupId>
    <artifactId>lombok-ext</artifactId>
    <version>2.3.4</version>
</dependency>
```
- 使用文本块
```
import lombok.TextBlock;
import lombok.TextBlocks;
...
/*
select * from user t
where t.name like '#{user.name}'
and t.create_time > '2021-07-09'
*/
@TextBlock String sql = TextBlocks.lazyInit();
List<User> obj = session.query(sql);
...
```
注意：文本块的内容在注释里面。建议文本块中的内容，另起一行，最后再加一行结束。
开头和结束的行，都只用一个星号。否则多余的星号会被当做文本块内容。

如果TextBlock不生效，并且项目导入为maven项目。
（如果导入为gradle项目，自己去探索吧，我也不知道怎么配置，如果你的配置能工作，麻烦也告诉一下我怎么做）

可以按下面的方式配置idea（eclipse用户自己去探索吧）。
（报错信息: Lombok were supposed to inject this variable）

idea中配置注解处理器支持TextBlock。

file->settings->build->compiler->annotation processors

勾选 annotation processing
勾选 obtain processors from project classpath

选中 maven default annotation processors profile

Store generated sources relative to: Module content root
Production sources directory: target/generated-sources/annotations
Test sources directory:       target/generated-test-sources/test-annotations

选中 func（maven项目名）
Store generated sources relative to: Module content root
Production sources directory: target/generated-sources/annotations
Test sources directory:       target/generated-test-sources/test-annotations



