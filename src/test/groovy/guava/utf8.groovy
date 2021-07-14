package guava

import com.google.common.base.Utf8

assert !Utf8.isWellFormed("你好".getBytes("gbk"))
assert Utf8.isWellFormed("你好".getBytes("utf-8"))