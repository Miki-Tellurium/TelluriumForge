package com.mikitellurium.telluriumforge.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class NameableBlockEntity extends BlockEntity {

    private Component name;

    public NameableBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public void setCustomName(Component name) {
        this.name = name;
    }

    public Component getName() {
        return this.name != null ? this.name : this.getDefaultName();
    }

    protected abstract Component getDefaultName();

    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("CustomName", CompoundTag.TAG_STRING)) {
            this.name = Component.Serializer.fromJson(tag.getString("CustomName"));
        }
    }

    protected void saveAdditional(CompoundTag tag) {
        if (this.name != null) {
            tag.putString("CustomName", Component.Serializer.toJson(this.name));
        }
        super.saveAdditional(tag);
    }

}
