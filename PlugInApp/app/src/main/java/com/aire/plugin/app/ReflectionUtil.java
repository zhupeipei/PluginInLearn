package com.aire.plugin.app;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtil {

    public static Class getClass(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }

    public static Field getField(String className, String fieldName) throws Exception {
        Class<?> clazz = Class.forName(className);
        Field field = clazz.getDeclaredField(fieldName);
        return field;
    }

    public static Field getField(Class clazz, String fieldName) throws NoSuchFieldException {
        return clazz.getDeclaredField(fieldName);
    }

    public static Object getFieldObject(Class clazz, String fieldName, Object classObj) throws Exception {
        Field field = getField(clazz, fieldName);
        field.setAccessible(true);
        return field.get(classObj);
    }

    public static Object getFieldObject(String className, String fieldName, Object classObj) throws Exception {
        Field field = getField(className, fieldName);
        field.setAccessible(true);
        return field.get(classObj);
    }

    public static void setField(Field field, Object obj, Object proxy) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(obj, proxy);
    }

    public static void setField(String fieldName, Object obj, Object proxy) throws Exception {
        Field field = getField(obj.getClass(), fieldName);
        setField(field, obj, proxy);
    }

    public static Object invokeMethod(Class clazz, String methodName, Class[] paramTypes, Object[] parmams) {
        try {
            Method method = clazz.getMethod(methodName, paramTypes);
            Constructor ctor = clazz.getDeclaredConstructor(new Class[]{});
            ctor.setAccessible(true);
            return method.invoke(ctor.newInstance(new Object[]{}), parmams);
        } catch (Exception e) {
            return null;
        }
    }
}
