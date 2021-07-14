package org.sirenia.func.util;

import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;

public abstract class Objects {

    public static final Map<Class,Object> defaultValues = new HashMap<>();

    static{
        defaultValues.put(boolean.class,Boolean.valueOf(false));
        defaultValues.put(byte.class,0);
        defaultValues.put(short.class,Short.valueOf((short)0));
        defaultValues.put(int.class,0);
        defaultValues.put(long.class,0L);
        defaultValues.put(float.class,0f);
        defaultValues.put(double.class,0d);
        defaultValues.put(char.class,'\u0000');
    }

    public static Object defaultValue(Class clazz){
        return defaultValues.get(clazz);
    }

    public static boolean asBoolean(@Nullable Object o) {
        if (o == null) {
            return false;
        }
        //boolean
        if(o instanceof Boolean){
            return (boolean)o;
        }
        //collections and array
        if (o.getClass().isArray()) {
            return Array.getLength(o) > 0;
        }
        if (o instanceof Collection) {
            return !((Collection)o).isEmpty();
        }
        //Matcher
        if(o instanceof Matcher){
            return ((Matcher)o).find(0);
        }
        //Iterators and Enumerations
        if(o instanceof Iterator){
            return ((Iterator)o).hasNext();
        }
        if(o instanceof Enumeration){
            return ((Enumeration)o).hasMoreElements();
        }
        //Maps
        if (o instanceof Map) {
            return !((Map)o).isEmpty();
        }
        //strings
        if (o instanceof CharSequence) {
            return ((CharSequence)o).length()>0;
        }
        if( o instanceof Character){
            return (char)o!=0;
        }
        //numbers
        if(o instanceof Number){
            return ((Number)o).doubleValue()!=0.0D;
        }
        return true;
    }
}
