package com.mikitellurium.telluriumforge.registry;

import net.minecraftforge.registries.DeferredRegister;

public interface InitializedRegistry<T> {

    void init(DeferredRegister<T> deferredRegister);

}
