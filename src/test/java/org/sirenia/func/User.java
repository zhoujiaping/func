package org.sirenia.func;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends Person implements Serializable {
    static final long serialVersionUID = 42L;
    String name;
    int age;
    String phone;
    public static final String hello(){
        return "hello";
    }
}

