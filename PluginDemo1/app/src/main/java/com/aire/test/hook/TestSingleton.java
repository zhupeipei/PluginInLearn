package com.aire.test.hook;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TestSingleton {
    private static final Singleton<MyInterface> gDefault = new Singleton<MyInterface>() {
        @Override
        protected MyInterface create() {
            MyInterface clazz = new MyInterface() {
                @Override
                public void add() {
                    System.out.printf("abc");
                }
            };
            return clazz;
        }
    };

    public static MyInterface getDefault() {
        return gDefault.get();
    }

    public static void main(String[] args) {
        getDefault();
        try {
            Class<?> clazz = Class.forName("com.aire.test.hook.TestSingleton");
            Field field = clazz.getDeclaredField("gDefault");
            field.setAccessible(true);
            Object gDefaultObj = field.get(null);

            Class<?> singletonClazz = Class.forName("com.aire.test.hook.Singleton");
            Field instanceField = Singleton.class.getDeclaredField("mInstance");
            instanceField.setAccessible(true);
            Object obj = instanceField.get(gDefaultObj); // MyInterface

            Object proxy = Proxy.newProxyInstance(TestSingleton.class.getClassLoader(), new Class[]{MyInterface.class}, new MyClassMock(obj));
            instanceField.set(gDefaultObj, proxy);

            getDefault().add();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class MyClassMock implements InvocationHandler {
        public MyClassMock(Object obj) {

        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("abn");
            return method.invoke(proxy, args);
        }
    }

    interface MyInterface {
        void add();
    }
}
