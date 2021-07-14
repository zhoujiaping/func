package org.sirenia.func.mixin

import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import org.sirenia.func.FuncMixin
import org.sirenia.func.anno.SourceMixin

import java.lang.reflect.Modifier

/**
 * unfortunately, we cann't generate source class with generic type!
 */
class MixinJavassistImpl {
    def generate(File classpath) {
        def sourceClass = FuncMixin
        SourceMixin mixin = sourceClass.getAnnotation(SourceMixin)
        def targetClassName = mixin.name()
        ClassPool pool = ClassPool.getDefault()
        try {
            pool.get(targetClassName)
            return
        }catch(e){
            //noop
        }
        CtClass cc = pool.makeClass(targetClassName);

        def classes = mixin.value()
        classes.each {
            clazz ->
                def funcClass = pool.get(clazz.getName())
                funcClass.declaredMethods.each {
                    method ->
                        //filter non-public
                        if (!Modifier.isPublic(method.modifiers)) {
                            return
                        }
                        //filter lambda
                        if (method.name.contains('$')) {
                            return
                        }
                        def newMethod = new CtMethod(method,cc,null)
                        cc.addMethod(newMethod)
                }
        }
        cc.writeFile(classpath.absolutePath)
    }

    //before run this, we need comment classes.doLast at build.gradle
    static void main(String[] args) {
        def userDir = System.getProperty("user.dir")
        println userDir
        new MixinJavassistImpl().generate(new File(userDir))
    }
}
