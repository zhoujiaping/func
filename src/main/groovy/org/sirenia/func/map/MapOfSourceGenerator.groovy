package org.sirenia.func.map

class MapOfSourceGenerator {
    static void main(String[] args) {
        def tuples = []
        (1..10).each{
            num->
                def typeAndValues = (1..num).collect{
                    "K k$it, V v$it"
                }
                def puts = (1..num).collect{
                    "        map.put(k$it, v$it);"
                }
                tuples << """\
    @Pure
    public static @Nonnull
    <K, V> Map<K, V> mapOf(${typeAndValues.join(', ')}) {
        Map<K, V> map = new LinkedHashMap<>();
${puts.join('\n')}
        return map;
    }
"""
        }
        println tuples.join('\n')

    }
}
