package com.mikitellurium.telluriumforge.util;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;

public class Utils {
    public static <T> T make(T obj, Consumer<T> consumer) {
        consumer.accept(obj);
        return obj;
    }

    public static <T> T getRandom(T[] objects, Random random) {
        return objects[random.nextInt(objects.length)];
    }

    public static int getRandom(int[] objects, Random random) {
        return objects[random.nextInt(objects.length)];
    }

    public static <T> T getRandom(List<T> objects, Random random) {
        return objects.get(random.nextInt(objects.size()));
    }

    public static <T> Optional<T> getRandomSafe(List<T> objects, Random random) {
        return objects.isEmpty() ? Optional.empty() : Optional.of(getRandom(objects, random));
    }
}
