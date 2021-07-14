package guava

import com.google.common.io.Resources
import com.google.common.reflect.ClassPath

import java.nio.charset.StandardCharsets

URL resource = Resources.getResource("META-INF/maven/com.google.guava/guava/pom.xml")

Resources.asCharSource(resource, StandardCharsets.UTF_8).forEachLine(){
    println it
}

println ClassPath.from(this.class.classLoader).getTopLevelClassesRecursive('com.google')