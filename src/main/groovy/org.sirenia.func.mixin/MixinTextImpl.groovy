package org.sirenia.func.mixin

import org.sirenia.func.FuncMixin
import org.sirenia.func.anno.SourceMixin

/**
 * generate mixin java source file.
 * if the source file is well format.
 * it's not perfect, but it will not loss information.
 * */
class MixinTextImpl {
    def generate(File classpath) {
        def sourceClass = FuncMixin
        SourceMixin mixin = sourceClass.getAnnotation(SourceMixin)
        def targetClassName = mixin.name()
//        try {
//            Class.forName(targetClassName)
//            return
//        }catch(e){
//            //noop
//        }

        def moduleRoot = new File(System.getProperty("user.dir"))
        while('src' in moduleRoot.absolutePath){
            moduleRoot = moduleRoot.parentFile
        }

        def pkg = targetClassName.split(/\./)[0..-2].join('.')
        def simpleName = targetClassName.split(/\./)[-1]
        def imports = [] as Set
        def body = []
        def classes = mixin.value()
        classes.each {
            clazz ->
                def javaFileName = clazz.name.replaceAll(/\./,'/')+'.java'
                def javaFile = new File(moduleRoot,"/src/main/java/$javaFileName")
                javaFile.readLines().findAll{
                    if(it.matches(/^.*static\s+final.*$/)){
                        imports << "import static ${clazz.name}.*;"
                        return false
                    }
                    it.trim() && !it.matches(/^\s*package.*$/)
                            && !it.matches(/^.*public\s+abstract\s+class.*$/)
                }.each {
                    if(it.startsWith("import")){
                        imports << it
                    }else{
                        body << it
                    }
                }
                //the last }
                body[-1] = ''
        }
        def importLines = imports.join('\n')
        def bodyLines = body.join('\n')
        new File(classpath,simpleName+'.java').text = """\
package $pkg;

$importLines

public abstract class ${simpleName}{

$bodyLines

}
"""
    }

    static void main(String[] args) {
        def userDir = System.getProperty("user.dir")
        println userDir
        new MixinTextImpl().generate(new File(userDir,"/src/main/java/org/sirenia/func"))
    }
}
