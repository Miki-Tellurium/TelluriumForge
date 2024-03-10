package com.mikitellurium.telluriumforge.networking;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

/**
 * A basic class used to send packets for block entities data synchronization.
 * <p>
 * Implementations should store the packet type instance in a static final
 * field and return that in the {@link #getType()} method implemented from
 * the {@code FabricPacket} interface.
 */
public abstract class BlockEntitySyncPacket implements FabricPacket {

    /**
     * The {@code BlockPos} of the block entity
     */
    private final BlockPos blockPos;

    /**
     * Construct a new packet with the {@code BlockPos} of the
     * block entity to synchronize.
     *
     * @param blockPos the {@code BlockPos} of the block entity to sync
     */
    public BlockEntitySyncPacket(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    /**
     * Gets the {@code BlockPos} of the block entity stored
     * in this packet.
     *
     * @return the {@code BlockPos} of the block entity
     */
    public BlockPos getBlockPos() {
        return blockPos;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeBlockPos(this.blockPos);
    }

    @Override
    public abstract PacketType<?> getType();

}
