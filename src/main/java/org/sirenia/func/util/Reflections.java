package org.sirenia.func.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Consumer;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import javax.annotation.Nullable;

public abstract class Reflections extends ReflectionUtils {

    private static final String FIELD = "field";
    private static final String INDEX = "index";
    private static final String KEY = "key";
    private static final char ARR_BEGIN = '[';
    private static final char ARR_END = ']';
    private static final char DOT = '.';
    private static final char SINGLE_QUOTE = '\'';
    private static final char BACKSLASH = '\\';


    private static final Method[] EMPTY_METHOD_ARRAY = new Method[0];

    private static final Map<Class<?>, Method[]> declaredMethodsCache;

    static {
        Field declaredMethodsCacheField = findField(ReflectionUtils.class, "declaredMethodsCache");
        makeAccessible(declaredMethodsCacheField);
        try {
            declaredMethodsCache = (Map<Class<?>, Method[]>) declaredMethodsCacheField.get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * the orig method name is findMethod.
     * we changed it to responseTo
     * and we changed implementation of hasSameParams
     */
    @Nullable
    public static List<Method> responseTo(Class<?> clazz, String name, Object... args) {
        Assert.notNull(clazz, "Class must not be null");
        Assert.notNull(name, "Method name must not be null");
        Class<?> searchType = clazz;
        Class<?>[] paramTypes = null;
        if (args != null) {
            paramTypes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                if (args[i] == null) {
                    paramTypes[i] = null;
                }
                paramTypes[i] = args[i].getClass();
            }
        }
        List<Method> foundMethods = new LinkedList<>();
        while (searchType != null) {
            Method[] methods = (searchType.isInterface() ? searchType.getMethods() :
                    getDeclaredMethods(searchType, false));
            for (Method method : methods) {
                if (name.equals(method.getName()) && (paramTypes == null || hasSameParams(method, paramTypes))) {
                    foundMethods.add(method);
                }
            }
            searchType = searchType.getSuperclass();
        }
        return foundMethods;
    }


    private static boolean hasSameParams(Method method, Class<?>[] paramTypes) {
        return typeMatches(paramTypes, method.getParameterTypes());
    }

    //and we changed the method typeMatches
    public static boolean typeMatches(Class[] actualParamTypes, Class[] virtualParamTypes) {
        if (actualParamTypes.length != virtualParamTypes.length) {
            return false;
        }
        int len = virtualParamTypes.length;
        for (int i = 0; i < len; i++) {
            Class act = actualParamTypes[i];
            Class vir = virtualParamTypes[i];
            //if actual argument is null, we treat it as Any type
            if (act == null) {
                continue;
            }
            //if virtual argument is primitive, we use it's wrapper class
            if (vir.isPrimitive()) {
                vir = Classs.getWrapperClass(vir);
            }
            if (!vir.isAssignableFrom(act)) {
                return false;
            }
        }
        return true;
    }

    private static Method[] getDeclaredMethods(Class<?> clazz, boolean defensive) {
        Assert.notNull(clazz, "Class must not be null");
        Method[] result = declaredMethodsCache.get(clazz);
        if (result == null) {
            try {
                Method[] declaredMethods = clazz.getDeclaredMethods();
                List<Method> defaultMethods = findConcreteMethodsOnInterfaces(clazz);
                if (defaultMethods != null) {
                    result = new Method[declaredMethods.length + defaultMethods.size()];
                    System.arraycopy(declaredMethods, 0, result, 0, declaredMethods.length);
                    int index = declaredMethods.length;
                    for (Method defaultMethod : defaultMethods) {
                        result[index] = defaultMethod;
                        index++;
                    }
                } else {
                    result = declaredMethods;
                }
                declaredMethodsCache.put(clazz, (result.length == 0 ? EMPTY_METHOD_ARRAY : result));
            } catch (Throwable ex) {
                throw new IllegalStateException("Failed to introspect Class [" + clazz.getName() +
                        "] from ClassLoader [" + clazz.getClassLoader() + "]", ex);
            }
        }
        return (result.length == 0 || !defensive) ? result : result.clone();
    }

    @Nullable
    private static List<Method> findConcreteMethodsOnInterfaces(Class<?> clazz) {
        List<Method> result = null;
        for (Class<?> ifc : clazz.getInterfaces()) {
            for (Method ifcMethod : ifc.getMethods()) {
                if (!Modifier.isAbstract(ifcMethod.getModifiers())) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(ifcMethod);
                }
            }
        }
        return result;
    }

    public static FieldValue field(Object target, String path, boolean autoUnwrapJdkProxy) {
        return field0(target, path, autoUnwrapJdkProxy);
    }

    /**
     * index support array,list.
     * key support object,map.
     * field name support object.
     */
    public static FieldValue field(Object target, String path) {
        return field0(target, path, false);
    }

    private static FieldValue field0(Object target, String path, boolean autoUnwrapJdkProxy) {
        Assert.hasText(path,"path is empty!");
        Object object = target;
        Object value = target;
        Field field = null;
        List<PathNode> nodes = parsePathNodes(path);
        PathNode lastNode = null;
        for (PathNode node : nodes) {
            lastNode = node;
            object = value;
            if (autoUnwrapJdkProxy) {
                object = unwrapJdkProxy(object);
            }
            if(node.type.equals(FIELD)){
                field = ReflectionUtils.findField(object.getClass(), node.name);
                ReflectionUtils.makeAccessible(field);
                if (Modifier.isStatic(field.getModifiers())) {
                    object = null;
                }
                value = ReflectionUtils.getField(field, object);
            }else if(node.type.equals(INDEX)){
                field = null;
                value = ((List) object).get(Integer.parseInt(node.name));
            }else if(node.type.equals(KEY)){
                if(object instanceof Map){
                    field = null;
                    value = ((Map) object).get(node.name);
                }else{
                    field = ReflectionUtils.findField(object.getClass(), node.name);
                    ReflectionUtils.makeAccessible(field);
                    if (Modifier.isStatic(field.getModifiers())) {
                        object = null;
                    }
                    value = ReflectionUtils.getField(field, object);
                }
            }else{
                throw new RuntimeException("this is impossible!");
            }
        }
        if (autoUnwrapJdkProxy) {
            value = unwrapJdkProxy(value);
        }
        Consumer<Object> setter;
        if(lastNode.type.equals(FIELD)){
            Field finalField = field;
            Object finalObject = object;
            setter = newValue->ReflectionUtils.setField(finalField, finalObject, newValue);
        }else if(lastNode.type.equals(INDEX)){
            int index = Integer.parseInt(lastNode.name);
            if(object instanceof List){
                List list = (List) object;
                setter = newValue->list.set(index,newValue);
            }else if(object.getClass().isArray()){
                Object array = object;
                setter = newValue->Array.set(array,index,newValue);
            }else{
                throw new RuntimeException("unsupported operate!");
            }
        }else if(lastNode.type.equals(KEY)){
            if(object instanceof Map){
                Map map = (Map) value;
                String finalKey = lastNode.name;
                setter = newValue->map.put(finalKey,newValue);
            }else{
                Field finalField = field;
                Object finalObject = object;
                setter = newValue->ReflectionUtils.setField(finalField, finalObject, newValue);
            }
        }else{
            throw new RuntimeException("unknown error!");
        }
        FieldValue fv = new FieldValue();
        fv.setter = setter;
        fv.value = value;
        return fv;
    }
    private static List<PathNode> parsePathNodes(String paths){
        List<PathNode> acc = new LinkedList<>();
        if(paths.charAt(0)!=ARR_BEGIN && paths.charAt(0)!=DOT){
            paths = DOT+paths;
        }
        char[] chs = paths.toCharArray();
        parsePathNodes0(paths,chs,0,acc);
        return acc;
    }

    private static void parsePathNodes0(String origPaths,char[] chs,int begin,List<PathNode> acc){
        if(begin>=chs.length){
            return;
        }
        StringBuilder sb = new StringBuilder();
        if(chs[begin]==ARR_BEGIN){
            if(chs[begin+1]==SINGLE_QUOTE){
                int i=begin+2;
                while(chs[i]!=SINGLE_QUOTE){
                    if(chs[i]==BACKSLASH) {
                        i++;
                    }
                    sb.append(chs[i]);
                    i++;
                }
                //chs[i]==']'
                i=i+2;
                acc.add(new PathNode(KEY,sb.toString()));
                parsePathNodes0(origPaths,chs,i,acc);
            }else{
                int i=begin+1;
                while(chs[i]!=ARR_END){
                    sb.append(chs[i]);
                    i++;
                }
                i=i+1;
                acc.add(new PathNode(INDEX,sb.toString()));
                parsePathNodes0(origPaths,chs,i,acc);
            }
        }else if(chs[begin]==DOT) {
            int i=begin+1;
            while(i<chs.length && chs[i]!=ARR_BEGIN && chs[i]!=DOT){
                sb.append(chs[i]);
                i++;
            }
            i=i+1;
            acc.add(new PathNode(FIELD,sb.toString()));
            parsePathNodes0(origPaths,chs,i,acc);
        }else{
            throw new RuntimeException("illegal paths(path="+origPaths+",index"+begin+")");
        }
    }
    @SneakyThrows
    private static <T> T unwrapJdkProxy(T target) {
        Object obj = target;
        while (obj.getClass().getName().startsWith("com.sun.proxy.$Proxy")) {
            Field f = obj.getClass().getDeclaredField("h");
            f.setAccessible(true);
            obj = f.get(obj);
            f = obj.getClass().getDeclaredField("target");
            f.setAccessible(true);
            obj = f.get(obj);
        }
        return (T) obj;
    }
    @AllArgsConstructor
    private static class PathNode{
        //field ,index, key
        String type;
        String name;
    }
    public static class FieldValue {
        public Object value;
        public Consumer<Object> setter;
    }
}
