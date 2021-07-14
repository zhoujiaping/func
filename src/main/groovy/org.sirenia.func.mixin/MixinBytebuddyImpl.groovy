package org.sirenia.func.mixin

import net.bytebuddy.ByteBuddy
import net.bytebuddy.description.type.TypeDescription
import net.bytebuddy.dynamic.DynamicType
import net.bytebuddy.implementation.MethodDelegation
import org.sirenia.func.FuncMixin
import org.sirenia.func.anno.SourceMixin

import java.lang.reflect.Modifier
import java.lang.reflect.ParameterizedType

class MixinBytebuddyImpl {
    def generate(File classpath) {
        def sourceClass = FuncMixin
        SourceMixin mixin = sourceClass.getAnnotation(SourceMixin)
        def targetClassName = mixin.name()
        def builder
        try{
            def targetClass = Class.forName(targetClassName)
            println "class $targetClassName eixsts"
            builder = new ByteBuddy().redefine(targetClass)
        }catch(ClassNotFoundException e){
            builder = new ByteBuddy().subclass(Object).name(targetClassName)
        }
        def classes = mixin.value()
        classes.each {
            clazz ->
                clazz.declaredMethods.each {
                    method ->
                        //filter non-public
                        if (!Modifier.isPublic(method.modifiers)) {
                            return
                        }
                        //filter lambda
                        if (method.name.contains('$')) {
                            return
                        }
                        /* the first way:  this way will lost generic type!*/
                        /*builder = builder.defineMethod(method.name, method.returnType, method.modifiers)
                                .withParameters(method.parameterTypes)
                                .intercept(MethodDelegation.to(clazz))*/
                        //the second way:
                        def gt = method.genericReturnType
                        def generic
                        if(gt in ParameterizedType) {
                            def tvs = [] as Set
                            generic = TypeDescription.Generic.Builder
                            gt.actualTypeArguments.each{
                                t->
                                    generic = generic.typeVariable(t.typeName)

                                tvs << t.typeName
                            }
                            tvs.each{
                                tv->
                                    builder = builder.typeVariable(tv)
                            }

                            generic = generic.build()

                        }else{
                            generic = gt
                        }

                        builder = builder.defineMethod(method.name, generic, method.modifiers)
                        method.parameterTypes.each{
                            builder = builder.withParameter(it)
                        }
                        builder = builder.intercept(MethodDelegation.to(clazz))

                        //the third way:
                        //builder = builder.define(method).intercept(MethodDelegation.to(clazz))
                        //the forth way:
                        //builder = builder.define(method).intercept(MethodDelegation.to(DefaultInterceptor))
                }
        }
        DynamicType.Default.Unloaded dynamicType = builder.make()
        dynamicType.saveIn(classpath)
    }

    //before run this, we need comment classes.doLast at build.gradle
    static void main(String[] args) {
        def userDir = System.getProperty("user.dir")
        println userDir
        new MixinBytebuddyImpl().generate(new File(userDir))
    }
}
