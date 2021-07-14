package org.sirenia.func.util;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public abstract class IOs {
    public static BufferedReader STD_READER;
    @SneakyThrows
    public static void setupIfNeed(){
        if(STD_READER == null){
            STD_READER = new BufferedReader(new InputStreamReader(System.in,"utf-8"));
        }
    }
    public static BufferedReader stdReader(){
        setupIfNeed();
        return STD_READER;
    }

}
