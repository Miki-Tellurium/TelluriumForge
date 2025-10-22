package com.mikitellurium.telluriumforge.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public interface ISideTickingTile extends ITickable {
    /**
     * Call this from {@link ITickable#update()} passing the tile itself.
     */
    default void tickTile(TileEntity tile) {
        World world = tile.getWorld();
        BlockPos pos = tile.getPos();
        IBlockState state = world.getBlockState(pos);
        if (world.isRemote) {
            this.tickClient((WorldClient) world, state, pos);
        } else {
            this.tickServer((WorldServer) world, state, pos);
        }
    }

    default void tickClient(WorldClient world, IBlockState state, BlockPos pos) {}

    default void tickServer(WorldServer world, IBlockState state, BlockPos pos) {}
}
