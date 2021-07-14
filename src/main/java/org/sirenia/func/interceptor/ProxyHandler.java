package org.sirenia.func.interceptor;

import java.lang.reflect.Method;

public interface ProxyHandler{
    Object invoke(Object proxy, Method method,Method superMethod, Object[] args);
}
