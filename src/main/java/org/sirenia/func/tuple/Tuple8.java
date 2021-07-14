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
public class Tuple8<V1, V2, V3, V4, V5, V6, V7, V8> implements Serializable {

    static final long serialVersionUID = 1L;

    public V1 _1;
    public V2 _2;
    public V3 _3;
    public V4 _4;
    public V5 _5;
    public V6 _6;
    public V7 _7;
    public V8 _8;
}
