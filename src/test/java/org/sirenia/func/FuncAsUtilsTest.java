package org.sirenia.func;

import org.junit.jupiter.api.Test;

/**
 * use top functions as utils
 * */
public class FuncAsUtilsTest{

    static Func $ = new Func(){};

    @Test
    public void testPrintln(){
        $.println("hello");
    }

    @Test
    public void testListOf(){
        $.println($.listOf("yes","i","can"));
    }

}
