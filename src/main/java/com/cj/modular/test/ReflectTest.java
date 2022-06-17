package com.cj.modular.test;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * <p>
 *
 * </p>
 *
 * @author Caoj
 * @date 2022-05-21 16:35
 */
public class ReflectTest {

    private String variable = "变量";
    public ReflectTest () {
        System.out.println("This is a Construct Method");
    }

    public static void main(String[] args) throws Exception {
        ArrayList<Integer> integers = new ArrayList<>(1);
        integers.add(23);
        integers.add(23);
        integers.add(23);
        Class<?> clazz = ArrayList.class;
        Field[] declaredFields = clazz.getDeclaredFields();
        for(Field field : declaredFields) {
            System.out.println(field.toString());
        }
    }
}
