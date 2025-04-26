package com.mikitellurium.telluriumforge.util;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class RegistryUtils {

    public static <T> Optional<T> getRegistryOptional(Registry<T> registry, ResourceLocation id) {
        return registry.getOptional(id);
    }

    public static <T> Optional<T> getRegistryOptional(Registry<T> registry, String id) {
        return registry.getOptional(ResourceLocation.tryParse(id));
    }

}
