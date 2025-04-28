package com.mikitellurium.telluriumforge.capability;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;

import java.util.Optional;

public class CapabilityHelper {

    public <T, C> Optional<T> getOptional(ItemStack itemStack, ItemCapability<T, C> cap, C context) {
        return Optional.ofNullable(itemStack.getCapability(cap, context));
    }

    public <T> Optional<T> getOptional(ItemStack itemStack, ItemCapability<T, Void> cap) {
        return Optional.ofNullable(itemStack.getCapability(cap));
    }

    public <T, C> Optional<T> getOptional(Entity entity, EntityCapability<T, C> cap, C context) {
        return Optional.ofNullable(entity.getCapability(cap, context));
    }

    public <T> Optional<T> getOptional(Entity entity, EntityCapability<T, Void> cap) {
        return Optional.ofNullable(entity.getCapability(cap));
    }

}
