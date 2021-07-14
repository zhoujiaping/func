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
public class Tuple1<V1> implements Serializable {

    static final long serialVersionUID = 1L;

    public V1 _1;
}
