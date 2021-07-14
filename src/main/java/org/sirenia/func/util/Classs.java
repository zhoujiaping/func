package org.sirenia.func.util;

import java.util.HashMap;
import java.util.Map;

public abstract class Classs {
    public static final Map<Class,Class> primitiveToWrapperMap = new HashMap<>();
    public static final Map<Class,Class> wrapperToPrimitiveMap = new HashMap<>();
    static {
        primitiveToWrapperMap.put(boolean.class,Boolean.class);
        primitiveToWrapperMap.put(byte.class,Byte.class);
        primitiveToWrapperMap.put(short.class,Short.class);
        primitiveToWrapperMap.put(int.class,Integer.class);
        primitiveToWrapperMap.put(long.class,Long.class);
        primitiveToWrapperMap.put(float.class,Float.class);
        primitiveToWrapperMap.put(double.class,Double.class);
        primitiveToWrapperMap.put(char.class,Character.class);

        primitiveToWrapperMap.entrySet().forEach(entry-> wrapperToPrimitiveMap.put(entry.getValue(),entry.getKey()));

    }

    public static Class getWrapperClass(Class primitiveClass){
        if(wrapperToPrimitiveMap.containsKey(primitiveClass)){
            return primitiveClass;
        }
        return primitiveToWrapperMap.get(primitiveClass);
    }

    public static Class getPrimitiveClass(Class wrapperClass){
        if(primitiveToWrapperMap.containsKey(wrapperClass)){
            return wrapperClass;
        }
        return wrapperToPrimitiveMap.get(wrapperClass);
    }

}
