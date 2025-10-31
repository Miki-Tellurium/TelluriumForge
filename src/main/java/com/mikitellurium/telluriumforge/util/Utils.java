package com.mikitellurium.telluriumforge.util;

import java.util.function.Consumer;

public class Utils {
    public static <T> T make(T obj, Consumer<T> consumer) {
        consumer.accept(obj);
        return obj;
    }
}
