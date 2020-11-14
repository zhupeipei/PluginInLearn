package com.aire.plugininapp2;

import java.io.File;
import java.lang.reflect.Array;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexFile;

public class PatchClassLoaderHelper {
    public static void patchClassLoader(BaseDexClassLoader classLoader, File apkFile, File optDexFile) {
        try {
            Object pathListObj = ReflectionUtil.getFieldObject(
                    classLoader.getClass().getSuperclass(), "pathList", classLoader);
            Object[] dexElements = (Object[]) ReflectionUtil.getFieldObject(pathListObj, "dexElements");
            Class<?> elementClazz = dexElements.getClass().getComponentType();
            Object[] newDexElements = (Object[]) Array.newInstance(elementClazz, dexElements.length + 1);
            // public Element(File dir, boolean isDirectory, File zip, DexFile dexFile)
            Object willAddElement = elementClazz
                    .getConstructor(new Class[]{File.class, boolean.class, File.class, DexFile.class})
                    .newInstance(new Object[]{
                            null, false, apkFile,
                            DexFile.loadDex(apkFile.getCanonicalPath(), optDexFile.getAbsolutePath(), 0)
                    });
            System.arraycopy(dexElements, 0, newDexElements, 0, dexElements.length);
            newDexElements[newDexElements.length - 1] = willAddElement;
            ReflectionUtil.setField(pathListObj, "dexElements", newDexElements);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
