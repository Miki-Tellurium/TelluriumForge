package com.mikitellurium.telluriumforge.util;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Optional;

public class RegistryUtils {

    public static <T> Optional<T> getRegistryOptional(Registry<T> registry, ResourceLocation id) {
        return registry.getOptional(id);
    }

    public static <T> Optional<T> getRegistryOptional(Registry<T> registry, String id) {
        return registry.getOptional(ResourceLocation.tryParse(id));
    }

    public static <T> Optional<T> getRegistryOptional(IForgeRegistry<T> registry, ResourceLocation id) {
        return registry.containsKey(id) ? Optional.ofNullable(registry.getValue(id)) : Optional.empty();
    }

    public static <T> Optional<T> getRegistryOptional(IForgeRegistry<T> registry, String id) {
        return getRegistryOptional(registry, ResourceLocation.tryParse(id));
    }

}
