package com.mikitellurium.telluriumforge.blockentity;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public interface SidedCapabilityProvider<T> {

    /**
     * Allows to return a capability based on the {@link Direction} provided
     */
    <B extends BlockEntity> T capabilityBySide(B blockEntity, Direction side);

    static <T, B extends BlockEntity> void register(RegisterCapabilitiesEvent event, BlockCapability<T, Direction> capability, BlockEntityType<B> type, SidedCapabilityProvider<T> provider) {
        event.registerBlockEntity(capability, type, provider::capabilityBySide);
    }

}
