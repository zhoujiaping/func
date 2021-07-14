package org.sirenia.func.tuple;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@FieldDefaults(makeFinal = true)
public class Tuple10<V1, V2, V3, V4, V5, V6, V7, V8, V9, V10> implements Serializable {

    static final long serialVersionUID = 1L;

    public V1 _1;
    public V2 _2;
    public V3 _3;
    public V4 _4;
    public V5 _5;
    public V6 _6;
    public V7 _7;
    public V8 _8;
    public V9 _9;
    public V10 _10;
}
