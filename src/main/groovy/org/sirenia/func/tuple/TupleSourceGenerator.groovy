package org.sirenia.func.tuple

class TupleSourceGenerator {
    static void main(String[] args) {
        def userDir = System.getProperty("user.dir")
        def tuples = []
        (1..10).each{
            num->
                def typeVars = (1..num).collect{
                    "V$it"
                }
                def fieldDeclares = (1..num).collect{
                    "    public V$it _$it;"
                }
                def fieldNames = (1..num).collect{
                    "_$it"
                }
                def typeAndValues = (1..num).collect{
                    "V$it _$it"
                }
                def tmpl = """\
package org.sirenia.func.tuple;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@FieldDefaults(makeFinal = true)
public class Tuple${num}<${typeVars.join(', ')}> implements Serializable {

    static final long serialVersionUID = 1L;

${fieldDeclares.join('\n')}
}
"""
                //println(tmpl)
                new File(userDir,"/src/main/java/org/sirenia/func/tuple/Tuple${num}.java").text = tmpl

                def constructorArgs = (1..num).collect{
                    "V$num _$num"
                }.join(', ')
                tuples << """\
    @Pure
    public static @Nonnull
    <${typeVars.join(', ')}> Tuple${num}<${typeVars.join(', ')}> tuple(${typeAndValues.join(', ')}) {
        return new Tuple${num}<>(${fieldNames.join(', ')});
    }
"""
        }
        println tuples.join('\n')

    }
}
