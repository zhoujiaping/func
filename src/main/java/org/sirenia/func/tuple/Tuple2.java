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
public class Tuple2<V1, V2> implements Serializable {

    static final long serialVersionUID = 1L;

    public V1 _1;
    public V2 _2;
}
