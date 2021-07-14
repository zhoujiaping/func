package guava

import com.google.common.hash.Hashing

import java.nio.charset.StandardCharsets

println Hashing.goodFastHash(256).hashString("hello world", StandardCharsets.UTF_8)

println Hashing.sha256().hashString("hello world", StandardCharsets.UTF_8)
