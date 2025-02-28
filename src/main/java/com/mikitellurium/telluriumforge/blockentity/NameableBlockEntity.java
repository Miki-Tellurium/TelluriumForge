package com.mikitellurium.telluriumforge.blockentity;

import net.minecraft.core.BlockPos;
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

}
