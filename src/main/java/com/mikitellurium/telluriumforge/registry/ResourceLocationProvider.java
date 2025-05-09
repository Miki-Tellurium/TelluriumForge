package com.mikitellurium.telluriumforge.registry;

import net.minecraft.resources.ResourceLocation;

public interface ResourceLocationProvider {
    /**
     * @return The mod id
     */
    String modId();

    /**
     * @return A {@link ResourceLocation} with this provider mod id.
     */
    default ResourceLocation ofMod(String path) {
        return ResourceLocation.fromNamespaceAndPath(this.modId(), path);
    }

    /**
     * @return A {@link ResourceLocation} with the default namespace.
     */
    default ResourceLocation ofMc(String path) {
        return ResourceLocation.withDefaultNamespace(path);
    }
}
