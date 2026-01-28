package com.cartera.util.cartera;

import java.lang.reflect.Field;

public class ReflectionUtil {

    public static Object get(Object obj, String fieldName) {
        if (obj == null || fieldName == null) {
            return null;
        }

        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            return null;
        }
    }
}
