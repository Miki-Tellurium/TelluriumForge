package com.mikitellurium.telluriumforge.registry;

import java.util.function.Supplier;

public interface RegistryHelper<T> {

    <S extends T> S register(String id, Supplier<S> object);

}
