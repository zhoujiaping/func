package guava

import com.google.common.base.CaseFormat

println CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,'helloWorld') == 'hello_world'
println CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,'hello_world') == 'hello_world'
assert CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,'hello_world') == 'helloWorld'
//一定要给定准确的信息，不然转换结果不符合预期
assert CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,'helloWorld') == 'helloworld'

println CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.LOWER_CAMEL).convert("helloWorld")