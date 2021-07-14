package org.sirenia.func.dict;

import org.sirenia.func.tuple.Tuple2;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Dict<V> extends TreeMap<String,V> {

    public static Dict<String> of(Map<String,String> map){
        Dict<String> dict = new Dict<>();
        dict.putAll(map);
        return dict;
    }
    public static Dict<String> of(String content,char kvSeparator){
        Map<String,String> map = Arrays.stream(content.split("\n"))
                .filter(it-> StringUtils.hasText(it))
                .map(it->it.trim())
                //line starts with '#' is be treat as comment.
                .filter(it->!it.startsWith("#"))
                .map(it->{
                    int i = it.indexOf(kvSeparator);
                    if(i<0){
                        return new Tuple2<>(it.trim(),"");
                    }else{
                        return new Tuple2<>(it.substring(0,i).trim(),it.substring(i+1).trim());
                    }
                })
                .collect(Collectors.toMap(it->it._1,it->it._2));
        return of(map);
    }

}
