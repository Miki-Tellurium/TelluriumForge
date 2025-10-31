package com.mikitellurium.telluriumforge.util;

import java.util.function.Function;

public class Utils {
    public static <T> T make(T obj, Function<T, T> function) {
        return function.apply(obj);
    }
}
