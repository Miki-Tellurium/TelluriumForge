package com.mikitellurium.telluriumforge.blockentity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Implement this on a block entity
 */
public interface TickingBlockEntity {

    static <T extends BlockEntity> BlockEntityTicker<T> getTicker() {
        return (world, pos, state, blockEntity) -> {
            if (blockEntity instanceof TickingBlockEntity) {
                ((TickingBlockEntity)blockEntity).tick(world, pos, state);
            } else {
                throw new RuntimeException("Block entity does not implement TickingBlockEntity interface");
            }
        };
    }

    /**
     * Tick the block entity based on the level side.
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
