package com.mikitellurium.telluriumforge.blockentity;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Implement this on a block entity
 */
public interface TickingBlockEntity {

    /**
     * Tick the block entity based on the level side.
     * This should be called from the block entity ticker
     * created in the block class
     */
    default void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (level.isClientSide) {
            this.clientTick((ClientLevel) level, blockPos, blockState);
        } else {
            this.serverTick((ServerLevel) level, blockPos, blockState);
        }
    }

    /**
     * Tick the block entity on the server
     */
    default void serverTick(ServerLevel level, BlockPos blockPos, BlockState blockState) {
    }

    /**
     * Tick the block entity on the client
     */
    default void clientTick(ClientLevel level, BlockPos blockPos, BlockState blockState) {
    }

}
