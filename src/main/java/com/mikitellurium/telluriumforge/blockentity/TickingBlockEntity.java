package com.mikitellurium.telluriumforge.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Implement this on a block entity
 */
public interface TickingBlockEntity {

    /**
     * Tick the block entity based on the level side.
     * This should be called from the block entity ticker
     * created in the block class
     */
    default void tick(World world, BlockPos blockPos, BlockState blockState) {
        if (world.isClient) {
            this.clientTick((ClientWorld) world, blockPos, blockState);
        } else {
            this.serverTick((ServerWorld) world, blockPos, blockState);
        }
    }

    /**
     * Tick the block entity on the client
     */
    default void clientTick(ClientWorld level, BlockPos blockPos, BlockState blockState) {
    }

    /**
     * Tick the block entity on the server
     */
    default void serverTick(ServerWorld level, BlockPos blockPos, BlockState blockState) {
    }

}
