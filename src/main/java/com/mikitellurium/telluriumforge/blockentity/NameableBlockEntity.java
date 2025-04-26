package com.mikitellurium.telluriumforge.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Automatically handles custom name for block entities
 */
public abstract class NameableBlockEntity extends BlockEntity {

    private Component name;

    public NameableBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public void setCustomName(Component name) {
        this.name = name;
    }

    public boolean hasCustomName() {
        return this.name != null && !this.name.equals(this.getDefaultName());
    }

    public Component getName() {
        return this.name != null ? this.name : this.getDefaultName();
    }

    /**
     * @return the default name
     */
    protected abstract Component getDefaultName();

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("CustomName", CompoundTag.TAG_STRING)) {
            this.name = Component.Serializer.fromJson(tag.getString("CustomName"), registries);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        if (this.name != null) {
            tag.putString("CustomName", Component.Serializer.toJson(this.name, registries));
        }
        super.saveAdditional(tag, registries);
    }

}
