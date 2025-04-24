package com.mikitellurium.telluriumforge.blockentity;

import net.minecraft.core.Direction;
import net.minecraftforge.common.util.LazyOptional;

public interface SidedCapabilityProvider<T> {

    /**
     * Allows to return a capability based on the {@link Direction} provided
     */
    T capabilityBySide(Direction side);

    /**
     * Get a {@link LazyOptional} of the capability
     */
    default LazyOptional<T> sidedLazyOptional(Direction side) {
        return LazyOptional.of(() -> this.capabilityBySide(side));
    }

}
