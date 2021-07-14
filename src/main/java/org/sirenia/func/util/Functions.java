package org.sirenia.func.util;

public abstract class Functions {

    public interface Producer{
        void produce();
    }
    public interface Producer0{
        void produce() throws Exception;
    }
    public interface Function0<T,R>{
        R apply(T t) throws Exception;
    }
    public interface Consumer0<T>{
        void accept(T t) throws Exception;
    }
    public interface Supplier0<T>{
        T get() throws Exception;
    }
    public interface Predicate0<T>{
        boolean test(T t) throws Exception;
    }
    public interface BiFunction0<T,U,R>{
        R apply(T t, U u) throws Exception;
    }

    public interface BiConsumer0<T,U>{
        void accept(T t, U u) throws Exception;
    }
    public interface BiPredicate0<T,U>{
        boolean test(T t, U u) throws Exception;
    }
}
