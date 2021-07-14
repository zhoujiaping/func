package org.sirenia.func.util;

import java.lang.reflect.Method;

public abstract class Methods {

    public static boolean isObjectDefaultOrOverride(Method method){
        String methodName = method.getName();
        if (methodName == "toString" && method.getParameterCount() == 0) {
            return true;
        } else if (methodName == "hashCode" && method.getParameterCount() == 0) {
            return true;
        } else if (methodName == "equals" && method.getParameterCount() == 1) {
            return true;
        } else {
            return method.getDeclaringClass().equals(Object.class);
        }
    }
}
