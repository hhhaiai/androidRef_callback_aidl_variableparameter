package cn.ref.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Utils {

    // support android and java
    public static void setFinalFieldReadable(Field field, int modify) throws NoSuchFieldException, IllegalAccessException {
        if (Modifier.isFinal(modify)) {
            Field modifiersField = getField("modifiers");
            if (modifiersField == null) {
                modifiersField = getField("accessFlags");
            }
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, modify & ~Modifier.FINAL);
        }
    }

    public static Field getField(String accessFlags) {
        try {
            return Field.class.getDeclaredField(accessFlags);
        } catch (Throwable e) {
        }
        return null;
    }
}