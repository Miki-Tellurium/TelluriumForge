package com.mikitellurium.telluriumforge.registry;

import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Registrator<T extends IForgeRegistryEntry<T>> implements IdentifierProvider {
    private final IForgeRegistry<T> registry;
    private final String modId;

    /**
     * Create a new registrator using the provided {@link IForgeRegistry} and mod id
     */
    public static <T extends IForgeRegistryEntry<T>> Registrator<T> makeRegistrator(IForgeRegistry<T> registry, String modId) {
        return new Registrator<>(registry, modId);
    }

    protected Registrator(IForgeRegistry<T> registry, String modId) {
        this.registry = registry;
        this.modId = modId;
    }

    public <S extends T> S register(String id, S object) {
        registry.register(object.setRegistryName(modLoc(id)));
        return object;
    }

    public IForgeRegistry<T> registry() {
        return registry;
    }

    @Override
    public String modId() {
        return modId;
    }

    /**
     * Call to init the object class
     */
    public void init() {}
}
